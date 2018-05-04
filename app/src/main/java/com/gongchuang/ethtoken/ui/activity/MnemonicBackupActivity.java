package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.utils.AppManager;
import com.gongchuang.ethtoken.view.MnemonicBackupAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/21/021.
 * e-mail:lomapa@163.com
 */

public class MnemonicBackupActivity extends BaseActivity {
    private static final int VERIFY_MNEMONIC_BACKUP_REQUEST = 1101;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_mnemonic)
    TextView tvMnemonic;
    private String walletMnemonic;
    private long walletId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mnemonic_backup;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.mnemonic_backup_title);
    }

    @Override
    public void initDatas() {
        AppManager.getAppManager().addActivity(this);
        MnemonicBackupAlertDialog mnemonicBackupAlertDialog = new MnemonicBackupAlertDialog(this);
        mnemonicBackupAlertDialog.show();
        Intent intent = getIntent();
        walletId = intent.getLongExtra("walletId", -1);
        walletMnemonic = intent.getStringExtra("walletMnemonic");
        tvMnemonic.setText(walletMnemonic);

    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        Intent intent = new Intent(this, VerifyMnemonicBackupActivity.class);
        intent.putExtra("walletId", walletId);
        intent.putExtra("walletMnemonic", walletMnemonic);
        startActivityForResult(intent, VERIFY_MNEMONIC_BACKUP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VERIFY_MNEMONIC_BACKUP_REQUEST) {
            if (data != null) {
                finish();
            }
        }
    }
}

