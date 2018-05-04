package com.gongchuang.ethtoken.event;

import com.gongchuang.ethtoken.domain.ETHWallet;

/**
 * Created by dwq on 2018/3/26/026.
 * e-mail:lomapa@163.com
 */

public class LoadWalletSuccessEvent {

    public ETHWallet getEthWallet() {
        return ethWallet;
    }

    public void setEthWallet(ETHWallet ethWallet) {
        this.ethWallet = ethWallet;
    }

    private ETHWallet ethWallet;
}
