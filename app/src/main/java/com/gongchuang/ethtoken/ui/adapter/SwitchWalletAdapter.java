package com.gongchuang.ethtoken.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.CommonAdapter;
import com.gongchuang.ethtoken.base.ViewHolder;

import java.util.List;

/**
 * Created by dwq on 2018/3/20/020.
 * e-mail:lomapa@163.com
 */

public class SwitchWalletAdapter extends CommonAdapter<String> {


    private int selection = 0;

    public SwitchWalletAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, String s) {
        ImageView ivCheck = holder.getView(R.id.iv_check);
        if (holder.getPosition() == selection) {
            ivCheck.setVisibility(View.VISIBLE);
        } else {
            ivCheck.setVisibility(View.GONE);
        }
    }

    public void setSelection(int selection) {
        this.selection = selection;
        notifyDataSetChanged();
    }
}
