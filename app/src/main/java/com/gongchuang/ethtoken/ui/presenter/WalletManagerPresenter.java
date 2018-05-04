package com.gongchuang.ethtoken.ui.presenter;

import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.ui.contract.WalletManagerContract;
import com.gongchuang.ethtoken.utils.WalletDaoUtils;

import java.util.List;

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

public class WalletManagerPresenter implements WalletManagerContract.Presenter {
    public WalletManagerPresenter(WalletManagerContract.View mView) {
        this.mView = mView;
    }

    private WalletManagerContract.View mView;

    @Override
    public void loadAllWallets() {
        Observable.create(new ObservableOnSubscribe<List<ETHWallet>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ETHWallet>> e) throws Exception {
                // 将钱包信息保存到数据库
                e.onNext(WalletDaoUtils.loadAll());
//                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ETHWallet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ETHWallet> ethWallets) {
                        mView.showWalletList(ethWallets);
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
