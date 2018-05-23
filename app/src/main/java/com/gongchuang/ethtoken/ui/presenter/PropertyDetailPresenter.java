package com.gongchuang.ethtoken.ui.presenter;

import com.gongchuang.ethtoken.ui.contract.PropertyDetailContract;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.AdminFactory;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dwq on 2018/3/28/028.
 * e-mail:lomapa@163.com
 */

public class PropertyDetailPresenter implements PropertyDetailContract.Presenter {

    @Override
    public void etcTransfer(final String fromAddress, String toAddress, String transferAmount, String miningCost) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                Admin web3j = AdminFactory.build(new HttpService());
//                PersonalUnlockAccount personalUnlockAccount = web3j.personalUnlockAccount("0x000...", "a password").send();
//                if (personalUnlockAccount.accountUnlocked()) {
//                    // send a transaction
                Transaction transaction = Transaction.createContractTransaction(fromAddress, null, BigInteger.ZERO, null);
                // 创建自定义gasPrice、gasLimit的Transaction
                //    Transaction etherTransaction = Transaction.createEtherTransaction(fromAddress, null, gasPrice, gasLimit, toAddress, ethValue);
                EthSendTransaction
                        transactionResponse = web3j.ethSendTransaction(transaction)
                        .send();
                // 交易Hash值
                String transactionHash = transactionResponse.getTransactionHash();
//                }
//                e.onNext();
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean wallet) {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void etcTransferCustomMiningCost(String fromAddress, String toAddress, String transferAmount
            , String gasPrice, String gas) {

    }
}
