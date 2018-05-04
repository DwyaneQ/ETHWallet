package com.gongchuang.ethtoken.ui.contract;

import com.gongchuang.ethtoken.base.BaseContract;
import com.gongchuang.ethtoken.domain.ETHWallet;

import java.util.List;

/**
 * Created by dwq on 2018/3/23/023.
 * e-mail:lomapa@163.com
 */

public interface WalletManagerContract extends BaseContract {

    interface View extends BaseContract.BaseView {
        void showWalletList(List<ETHWallet> ethWallets);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void loadAllWallets();
    }
}
