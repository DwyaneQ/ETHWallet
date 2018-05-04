package com.gongchuang.ethtoken.ui.contract;

import com.gongchuang.ethtoken.base.BaseContract;
import com.gongchuang.ethtoken.domain.ETHWallet;

/**
 * Created by dwq on 2018/3/23/023.
 * e-mail:lomapa@163.com
 */

public interface WalletDetailContract extends BaseContract {
    interface View extends BaseView {
        void modifySuccess();

        void modifyPwdSuccess(ETHWallet wallet);

        void showDerivePrivateKeyDialog(String privateKey);

        void showDeriveKeystore(String keystore);

        void deleteSuccess(boolean isDelete);
    }

    interface Presenter extends BasePresenter {
        void modifyWalletName(long walletId, String name);

        void modifyWalletPwd(long walletId, String walletName, String oldPassword, String newPassword);

        void deriveWalletPrivateKey(long walletId, String password);

        void deriveWalletKeystore(long walletId, String password);

        void deleteWallet(long walletId);
    }
}
