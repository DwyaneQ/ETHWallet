package com.gongchuang.ethtoken.ui.fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongchuang.ethtoken.event.LoadWalletSuccessEvent;
import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseFragment;
import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.ui.contract.LoadWalletContract;
import com.gongchuang.ethtoken.ui.presenter.LoadWalletPresenter;
import com.gongchuang.ethtoken.utils.ETHWalletUtils;
import com.gongchuang.ethtoken.utils.LogUtils;
import com.gongchuang.ethtoken.utils.ToastUtils;
import com.gongchuang.ethtoken.utils.UUi;
import com.gongchuang.ethtoken.utils.WalletDaoUtils;
import com.gongchuang.ethtoken.view.LoadWalletSelectStandardPopupWindow;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/22/022.
 * e-mail:lomapa@163.com
 */

public class LoadWalletByMnemonicFragment extends BaseFragment implements LoadWalletContract.View {

    @BindView(R.id.et_mnemonic)
    EditText etMnemonic;
    @BindView(R.id.et_standard)
    EditText etStandard;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etWalletPwdAgain;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etWalletPwdReminderInfo;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.lly_wallet_agreement)
    LinearLayout llyWalletAgreement;
    @BindView(R.id.btn_load_wallet)
    TextView btnLoadWallet;
    private LoadWalletContract.Presenter mPresenter;
    private LoadWalletSelectStandardPopupWindow popupWindow;

    private String ethType = ETHWalletUtils.ETH_JAXX_TYPE;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_load_wallet_by_mnemonic;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        mPresenter = new LoadWalletPresenter(this);
    }

    @Override
    public void configViews() {
        popupWindow = new LoadWalletSelectStandardPopupWindow(getContext());
        popupWindow.setOnPopupItemSelectedListener(new LoadWalletSelectStandardPopupWindow.OnPopupItemSelectedListener() {
            @Override
            public void onSelected(int selection) {
                switch (selection) {
                    case 0:
                        etStandard.setText(R.string.load_wallet_by_mnemonic_standard);
                        ethType = ETHWalletUtils.ETH_JAXX_TYPE;
                        etStandard.setEnabled(false);
                        break;
                    case 1:
                        etStandard.setText(R.string.load_wallet_by_mnemonic_standard_ledger);
                        ethType = ETHWalletUtils.ETH_LEDGER_TYPE;
                        etStandard.setEnabled(false);
                        break;
                    case 2:
                        etStandard.setText(R.string.load_wallet_by_mnemonic_standard_custom);
                        ethType = ETHWalletUtils.ETH_CUSTOM_TYPE;
                        etStandard.setEnabled(true);
                        etStandard.setFocusable(true);
                        etStandard.setFocusableInTouchMode(true);
                        etStandard.requestFocus();
                        break;

                }
            }
        });
    }

    @Override
    public void loadSuccess(ETHWallet wallet) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                dismissDialog();
            }
        }, 1000);
        ToastUtils.showToast("导入钱包成功");
        LoadWalletSuccessEvent loadWalletSuccessEvent = new LoadWalletSuccessEvent();
        loadWalletSuccessEvent.setEthWallet(wallet);
        EventBus.getDefault().post(loadWalletSuccessEvent);

    }

    @Override
    public void showError(String errorInfo) {

    }

    @Override
    public void complete() {

    }

    @OnClick({R.id.btn_load_wallet, R.id.lly_standard_menu, R.id.lly_wallet_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load_wallet:
                String mnemonic = etMnemonic.getText().toString().trim();
                String walletPwd = etWalletPwd.getText().toString().trim();
                String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                String pwdReminder = etWalletPwdReminderInfo.getText().toString().trim();
                boolean verifyWalletInfo = verifyInfo(mnemonic, walletPwd, confirmPwd, pwdReminder);
                if (verifyWalletInfo) {
                    showDialog(getString(R.string.loading_wallet_tip));
                    mPresenter.loadWalletByMnemonic(ethType, mnemonic, walletPwd);
                }
                break;
            case R.id.lly_standard_menu:
                LogUtils.i("LoadWallet", "lly_standard_menu");
                popupWindow.showAsDropDown(etStandard, 0, UUi.dip2px(10));
                break;
            case R.id.lly_wallet_agreement:
                if (cbAgreement.isChecked()) {
                    cbAgreement.setChecked(false);
                    btnLoadWallet.setEnabled(false);
                } else {
                    cbAgreement.setChecked(true);
                    btnLoadWallet.setEnabled(true);
                }
                break;
        }
    }

    private boolean verifyInfo(String mnemonic, String walletPwd, String confirmPwd, String pwdReminder) {
        if (TextUtils.isEmpty(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_by_mnemonic_input_tip);
            return false;
        } else if (!WalletDaoUtils.checkRepeatByMenmonic(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_already_exist);
            return false;
        } else if (TextUtils.isEmpty(walletPwd)) {
            ToastUtils.showToast(R.string.create_wallet_pwd_input_tips);
            // 同时判断强弱
            return false;
        } else if (TextUtils.isEmpty(confirmPwd) || !TextUtils.equals(confirmPwd, walletPwd)) {
            ToastUtils.showToast(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }

}
