package com.gongchuang.ethtoken.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseFragment;
import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.ui.contract.LoadWalletContract;
import com.gongchuang.ethtoken.ui.presenter.LoadWalletPresenter;
import com.gongchuang.ethtoken.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/22/022.
 * e-mail:lomapa@163.com
 */

public class LoadWalletByPrivateKeyFragment extends BaseFragment implements LoadWalletContract.View {
    @BindView(R.id.et_private_key)
    EditText etPrivateKey;
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
    @BindView(R.id.btn_help)
    TextView btnHelp;

    private LoadWalletContract.Presenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_load_wallet_by_private_key;
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

    }

    @OnClick({R.id.btn_load_wallet, R.id.lly_wallet_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load_wallet:
                String privateKey = etPrivateKey.getText().toString().trim();
                String walletPwd = etWalletPwd.getText().toString().trim();
                String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                String pwdReminder = etWalletPwdReminderInfo.getText().toString().trim();
                boolean verifyWalletInfo = verifyInfo(privateKey, walletPwd, confirmPwd, pwdReminder);
                if (verifyWalletInfo) {
                    showDialog(getString(R.string.loading_wallet_tip));
                    mPresenter.loadWalletByPrivateKey(privateKey, walletPwd);
                }
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

    private boolean verifyInfo(String privateKey, String walletPwd, String confirmPwd, String pwdReminder) {
        if (TextUtils.isEmpty(privateKey)) {
            ToastUtils.showToast(R.string.load_wallet_by_private_key_input_tip);
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

    @Override
    public void loadSuccess(ETHWallet wallet) {
        ToastUtils.showToast("导入钱包成功");
        dismissDialog();
        EventBus.getDefault().post(wallet);

    }

    @Override
    public void showError(String errorInfo) {

    }

    @Override
    public void complete() {

    }
}
