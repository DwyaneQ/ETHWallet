package com.gongchuang.ethtoken.ui.adapter;

import android.content.Context;

import com.gongchuang.ethtoken.base.CommonAdapter;
import com.gongchuang.ethtoken.base.ViewHolder;

import java.util.List;

/**
 * Created by dwq on 2018/3/23/023.
 * e-mail:lomapa@163.com
 */

public class MessageCenterAdapter extends CommonAdapter<String> {
    public MessageCenterAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, String s) {

    }
}
