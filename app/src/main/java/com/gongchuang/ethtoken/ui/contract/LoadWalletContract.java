package com.gongchuang.ethtoken.ui.contract;

import com.gongchuang.ethtoken.base.BaseContract;
import com.gongchuang.ethtoken.domain.ETHWallet;

/**
 * Created by dwq on 2018/3/23/023.
 * e-mail:lomapa@163.com
 */

public interface LoadWalletContract extends BaseContract {
    interface View extends BaseContract.BaseView {
        void loadSuccess(ETHWallet wallet);

//        void walletAlreadyExist(String tip);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void loadWalletByMnemonic(String bipPath, String mnemonic, String pwd);

        void loadWalletByKeystore(String keystore, String pwd);

        void loadWalletByPrivateKey(String privateKey, String pwd);
    }
}
