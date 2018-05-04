package com.gongchuang.ethtoken.ui.presenter;

import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.ui.contract.CreateWalletContract;
import com.gongchuang.ethtoken.utils.ETHWalletUtils;
import com.gongchuang.ethtoken.utils.LogUtils;
import com.gongchuang.ethtoken.utils.WalletDaoUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dwq on 2018/3/14/014.
 * e-mail:lomapa@163.com
 */

public class CreateWalletPresenter implements CreateWalletContract.Presenter {

    public CreateWalletPresenter(CreateWalletContract.View mView) {
        this.mView = mView;
    }

    private CreateWalletContract.View mView;

    @Override
    public void createWallet(final String name, final String pwd, String confirmPwd, String pwdReminder) {
        Observable.create(new ObservableOnSubscribe<ETHWallet>() {
            @Override
            public void subscribe(ObservableEmitter<ETHWallet> e) throws Exception {
//                if (WalletDaoUtils.walletNameChecking(name)) {
                ETHWallet ethWallet = ETHWalletUtils.generateMnemonic(name, pwd);
                // 将新创建的钱包添加到数据库中，并选中新钱包
                WalletDaoUtils.insertNewWallet(ethWallet);
                // 将钱包信息保存到数据库
                e.onNext(ethWallet);
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ETHWallet>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ETHWallet wallet) {
                        mView.jumpToWalletBackUp(wallet);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("钱包创建失败");
                        LogUtils.e("CreateWalletPresenter", e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public boolean walletNameRepeatChecking(final String name) {
//        final boolean isRepeat = false;
//        Observable.create(new ObservableOnSubscribe<Boolean>() {
//            @Override
//            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
//                // 将钱包信息保存到数据库
//
//                e.onNext(WalletDaoUtils.walletNameChecking(name));
////                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Boolean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Boolean repeat) {
//                        isRepeat = repeat;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.showError(e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
        return false;
    }

}
