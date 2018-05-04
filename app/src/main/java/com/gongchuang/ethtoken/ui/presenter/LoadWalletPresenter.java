package com.gongchuang.ethtoken.ui.presenter;

import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.ui.contract.LoadWalletContract;
import com.gongchuang.ethtoken.utils.ETHWalletUtils;
import com.gongchuang.ethtoken.utils.WalletDaoUtils;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dwq on 2018/3/23/023.
 * e-mail:lomapa@163.com
 */

public class LoadWalletPresenter implements LoadWalletContract.Presenter {
    public LoadWalletPresenter(LoadWalletContract.View mView) {
        this.mView = mView;
    }

    private LoadWalletContract.View mView;


    @Override
    public void loadWalletByMnemonic(final String bipPath, final String mnemonic, final String pwd) {
        Observable.create(new ObservableOnSubscribe<ETHWallet>() {
            @Override
            public void subscribe(ObservableEmitter<ETHWallet> e) throws Exception {
                ETHWallet ethWallet = ETHWalletUtils.importMnemonic(bipPath
                        , Arrays.asList(mnemonic.split(" ")), pwd);
                if (ethWallet != null) {
                    WalletDaoUtils.insertNewWallet(ethWallet);
                }
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
                        mView.loadSuccess(wallet);
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
    public void loadWalletByKeystore(final String keystore, final String pwd) {
        Observable.create(new ObservableOnSubscribe<ETHWallet>() {
            @Override
            public void subscribe(ObservableEmitter<ETHWallet> e) throws Exception {
                ETHWallet ethWallet = ETHWalletUtils.loadWalletByKeystore(keystore, pwd);
                if (ethWallet != null) {
                    WalletDaoUtils.insertNewWallet(ethWallet);
                }
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
                        mView.loadSuccess(wallet);
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
    public void loadWalletByPrivateKey(final String privateKey, final String pwd) {
        Observable.create(new ObservableOnSubscribe<ETHWallet>() {
            @Override
            public void subscribe(ObservableEmitter<ETHWallet> e) throws Exception {
                ETHWallet ethWallet = ETHWalletUtils.loadWalletByPrivateKey(privateKey, pwd);
                if (ethWallet != null) {
                    WalletDaoUtils.insertNewWallet(ethWallet);
                }
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
                        mView.loadSuccess(wallet);
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
