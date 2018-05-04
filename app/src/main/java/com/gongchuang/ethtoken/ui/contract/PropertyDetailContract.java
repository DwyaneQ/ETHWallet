package com.gongchuang.ethtoken.ui.contract;

import com.gongchuang.ethtoken.base.BaseContract;

/**
 * Created by dwq on 2018/3/28/028.
 * e-mail:lomapa@163.com
 */

public interface PropertyDetailContract extends BaseContract {
    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {
        void etcTransfer(String fromAddress,String toAddress, String transferAmount, String miningCost);

        void etcTransferCustomMiningCost(String fromAddress,String toAddress, String transferAmount
                , String gasPrice, String gas);
    }
}
