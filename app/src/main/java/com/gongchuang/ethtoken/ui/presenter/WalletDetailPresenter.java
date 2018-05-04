package com.gongchuang.ethtoken.ui.presenter;

import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.ui.contract.WalletDetailContract;
import com.gongchuang.ethtoken.utils.ETHWalletUtils;
import com.gongchuang.ethtoken.utils.WalletDaoUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dwq on 2018/3/26/026.
 * e-mail:lomapa@163.com
 */

public class WalletDetailPresenter implements WalletDetailContract.Presenter {

    private WalletDetailContract.View mView;

    public WalletDetailPresenter(WalletDetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void modifyWalletName(long walletId, String name) {
        WalletDaoUtils.updateWalletName(walletId, name);
        mView.modifySuccess();
    }

    @Override
    public void modifyWalletPwd(final long walletId, final String walletName, final String oldPassword, final String newPassword) {
        Observable.create(new ObservableOnSubscribe<ETHWallet>() {
            @Override
            public void subscribe(ObservableEmitter<ETHWallet> e) throws Exception {
                e.onNext(ETHWalletUtils.modifyPassword(walletId, walletName, oldPassword, newPassword));
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ETHWallet>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ETHWallet ethWallet) {
                        mView.modifyPwdSuccess(ethWallet);
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
    public void deriveWalletPrivateKey(final long walletId, final String password) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(ETHWalletUtils.derivePrivateKey(walletId, password));
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String privateKey) {
                        mView.showDerivePrivateKeyDialog(privateKey);
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
    public void deriveWalletKeystore(final long walletId, final String password) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(ETHWalletUtils.deriveKeystore(walletId, password));
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String keystore) {
                        mView.showDeriveKeystore(keystore);
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
    public void deleteWallet(final long walletId) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(ETHWalletUtils.deleteWallet(walletId));
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean isDelete) {
                        mView.deleteSuccess(isDelete);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
