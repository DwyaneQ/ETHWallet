package com.gongchuang.ethtoken.ui.adapter;

import android.content.Context;
import android.graphics.Color;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.CommonAdapter;
import com.gongchuang.ethtoken.base.ViewHolder;
import com.gongchuang.ethtoken.domain.ETHWallet;

import java.util.List;

/**
 * Created by dwq on 2018/3/16/016.
 * e-mail:lomapa@163.com
 */

public class DrawerWalletAdapter extends CommonAdapter<ETHWallet> {

    private int currentWalletPosition = 0;

    public DrawerWalletAdapter(Context context, List<ETHWallet> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public void setCurrentWalletPosition(int currentWalletPosition) {
        this.currentWalletPosition = currentWalletPosition;
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder holder, ETHWallet wallet) {
        boolean isCurrent = wallet.getIsCurrent();
        int position = holder.getPosition();
        if (isCurrent) {
            currentWalletPosition = position;
            holder.getView(R.id.lly_wallet).setBackgroundColor(mContext.getResources().getColor(R.color.item_divider_bg_color));
        } else {
            holder.getView(R.id.lly_wallet).setBackgroundColor(Color.WHITE);
        }
        holder.setText(R.id.tv_wallet_name, wallet.getName());
    }
}
