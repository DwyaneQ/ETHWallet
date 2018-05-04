package com.gongchuang.ethtoken.ui.contract;

import com.gongchuang.ethtoken.base.BaseContract;
import com.gongchuang.ethtoken.domain.ETHWallet;

/**
 * Created by dwq on 2018/3/14/014.
 * e-mail:lomapa@163.com
 */

public interface CreateWalletContract extends BaseContract {

    interface View extends BaseContract.BaseView {
        void jumpToWalletBackUp(ETHWallet wallet);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void createWallet(String name, String pwd, String confirmPwd, String pwdReminder);

        boolean walletNameRepeatChecking(String name);
    }
}
