package com.gongchuang.ethtoken.ui.contract;

import com.gongchuang.ethtoken.base.BaseContract;
import com.gongchuang.ethtoken.domain.ETHWallet;

import java.util.List;

/**
 * Created by dwq on 2018/3/21/021.
 * e-mail:lomapa@163.com
 */

public interface PropertyContract extends BaseContract {
    interface View extends BaseContract.BaseView {
        void showWallet(ETHWallet wallet);

        void switchWallet(int currentPosition, ETHWallet wallet);

        void showDrawerWallets(List<ETHWallet> ethWallets);

        void showCurrentWalletProrperty(String balance);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void refreshWallet();

        void switchCurrentWallet(int position, long walletId);

        void loadAllWallets();

        void selectBalance(String walletAddress);
    }
}
