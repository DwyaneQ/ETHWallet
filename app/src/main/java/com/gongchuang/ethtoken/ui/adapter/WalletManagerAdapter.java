package com.gongchuang.ethtoken.ui.adapter;

import android.content.Context;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.CommonAdapter;
import com.gongchuang.ethtoken.base.ViewHolder;
import com.gongchuang.ethtoken.domain.ETHWallet;

import java.util.List;

/**
 * Created by dwq on 2018/3/19/019.
 * e-mail:lomapa@163.com
 */

public class WalletManagerAdapter extends CommonAdapter<ETHWallet> {
    public WalletManagerAdapter(Context context, List<ETHWallet> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ETHWallet wallet) {
        holder.setText(R.id.tv_wallet_name,wallet.getName());
        holder.setText(R.id.tv_wallet_address,wallet.getAddress());
    }
}
