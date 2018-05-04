package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.event.DeleteWalletSuccessEvent;
import com.gongchuang.ethtoken.event.LoadWalletSuccessEvent;
import com.gongchuang.ethtoken.ui.contract.WalletDetailContract;
import com.gongchuang.ethtoken.ui.presenter.WalletDetailPresenter;
import com.gongchuang.ethtoken.utils.Md5Utils;
import com.gongchuang.ethtoken.utils.TKeybord;
import com.gongchuang.ethtoken.utils.ToastUtils;
import com.gongchuang.ethtoken.view.InputPwdDialog;
import com.gongchuang.ethtoken.view.PrivateKeyDerivetDialog;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dwq on 2018/3/26/026.
 * e-mail:lomapa@163.com
 */

public class WalletDetailActivity extends BaseActivity implements WalletDetailContract.View {

    private static final int WALLET_DETAIL_RESULT = 2201;
    private static final int MODIFY_PASSWORD_REQUEST = 1102;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lly_back)
    LinearLayout llyBack;
    @BindView(R.id.iv_btn)
    TextView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.civ_wallet)
    CircleImageView civWallet;
    @BindView(R.id.tv_eth_balance)
    TextView tvEthBalance;
    @BindView(R.id.lly_wallet_property)
    LinearLayout llyWalletProperty;
    @BindView(R.id.tv_wallet_address)
    TextView tvWalletAddress;
    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.rl_modify_pwd)
    RelativeLayout rlModifyPwd;
    @BindView(R.id.rl_derive_private_key)
    RelativeLayout rlDerivePrivateKey;
    @BindView(R.id.rl_derive_keystore)
    RelativeLayout rlDeriveKeystore;
    @BindView(R.id.btn_delete_wallet)
    TextView btnDeleteWallet;
    @BindView(R.id.btn_mnemonic_backup)
    TextView btnMnemonicBackup;
    private long walletId;
    private String walletPwd;
    private String walletAddress;
    private String walletName;
    private boolean walletIsBackup;
    private WalletDetailContract.Presenter mPresenter;
    private InputPwdDialog inputPwdDialog;
    private String walletMnemonic;
    private PrivateKeyDerivetDialog privateKeyDerivetDialog;
    private boolean fromList;


    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        mPresenter = new WalletDetailPresenter(this);
        inputPwdDialog = new InputPwdDialog(this);
        Intent intent = getIntent();
        walletId = intent.getLongExtra("walletId", -1);
        walletPwd = intent.getStringExtra("walletPwd");
        walletAddress = intent.getStringExtra("walletAddress");
        walletName = intent.getStringExtra("walletName");
        walletIsBackup = intent.getBooleanExtra("walletIsBackup", false);
        walletMnemonic = intent.getStringExtra("walletMnemonic");
        fromList = intent.getBooleanExtra("fromList", false);
    }

    @Override
    public void configViews() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 1f)
                .init();

        tvTitle.setText(walletName);
        etWalletName.setText(walletName);
        tvWalletAddress.setText(walletAddress);
        if (walletIsBackup) {
            btnMnemonicBackup.setVisibility(View.GONE);
        } else {
            btnMnemonicBackup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void modifySuccess() {

    }

    @Override
    public void modifyPwdSuccess(ETHWallet wallet) {

    }

    @Override
    public void showDerivePrivateKeyDialog(String privateKey) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                dismissDialog();
            }
        }, 1000);
        privateKeyDerivetDialog = new PrivateKeyDerivetDialog(WalletDetailActivity.this);
        privateKeyDerivetDialog.show();
        privateKeyDerivetDialog.setPrivateKey(privateKey);
    }

    @Override
    public void showDeriveKeystore(String keystore) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                dismissDialog();
            }
        }, 1000);
        Intent intent = new Intent(this, DeriveKeystoreActivity.class);
        intent.putExtra("walletKeystore", keystore);
        startActivity(intent);
    }

    @Override
    public void deleteSuccess(boolean isDelete) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                dismissDialog();
            }
        }, 2000);
        if (fromList) {
            setResult(WALLET_DETAIL_RESULT, new Intent());
            TKeybord.closeKeybord(etWalletName);
            EventBus.getDefault().post(new DeleteWalletSuccessEvent());
        } else {
            EventBus.getDefault().post(new DeleteWalletSuccessEvent());
            EventBus.getDefault().post(new LoadWalletSuccessEvent());
        }
        finish();
    }

    @Override
    public void showError(String errorInfo) {

    }

    @Override
    public void complete() {

    }

    @OnClick({R.id.rl_btn, R.id.rl_modify_pwd, R.id.btn_mnemonic_backup
            , R.id.btn_delete_wallet, R.id.rl_derive_private_key
            , R.id.rl_derive_keystore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_btn:
                String name = etWalletName.getText().toString().trim();
                if (!TextUtils.equals(this.walletName, name)) {
                    mPresenter.modifyWalletName(walletId, name);
                }
                showDialog(getString(R.string.saving_wallet_tip));
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        dismissDialog();
                        ToastUtils.showToast(R.string.wallet_detail_save_success);
                        setResult(WALLET_DETAIL_RESULT, new Intent());
                        TKeybord.closeKeybord(etWalletName);
                        finish();
                    }
                }, 2000);

                break;
            case R.id.rl_modify_pwd:// 修改密码
                Intent intent = new Intent(mContext, ModifyPasswordActivity.class);
                intent.putExtra("walletId", walletId);
                intent.putExtra("walletPwd", walletPwd);
                intent.putExtra("walletAddress", walletAddress);
                intent.putExtra("walletName", walletName);
                intent.putExtra("walletMnemonic", walletMnemonic);
                intent.putExtra("walletIsBackup", walletIsBackup);
                startActivityForResult(intent, MODIFY_PASSWORD_REQUEST);
                break;
            case R.id.rl_derive_private_key:// 导出明文私钥
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(false);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        inputPwdDialog.dismiss();
                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
                            showDialog(getString(R.string.deriving_wallet_tip));
                            mPresenter.deriveWalletPrivateKey(walletId, pwd);
                        } else {
                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
                        }
                    }
                });
                break;
            case R.id.rl_derive_keystore:// 导出明文keystore
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(false);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        inputPwdDialog.dismiss();
                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
                            showDialog(getString(R.string.deriving_wallet_tip));
                            mPresenter.deriveWalletKeystore(walletId, pwd);
                        } else {
                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
                        }
                    }
                });
                break;
            case R.id.btn_mnemonic_backup:
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(false);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
                            Intent intent = new Intent(WalletDetailActivity.this, MnemonicBackupActivity.class);
                            intent.putExtra("walletId", walletId);
                            intent.putExtra("walletMnemonic", walletMnemonic);
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
                            inputPwdDialog.dismiss();
                        }
                    }
                });
                break;

            case R.id.btn_delete_wallet:
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(true);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
                            showDialog(getString(R.string.deleting_wallet_tip));
                            mPresenter.deleteWallet(walletId);
                        } else {
                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
                            inputPwdDialog.dismiss();
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_PASSWORD_REQUEST) {
            if (data != null) {
                walletPwd = data.getStringExtra("newPwd");
            }
        }
    }
}
