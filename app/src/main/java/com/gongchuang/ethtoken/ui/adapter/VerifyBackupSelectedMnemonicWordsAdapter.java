package com.gongchuang.ethtoken.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gongchuang.ethtoken.R;

import java.util.List;

/**
 * Created by dwq on 2018/3/13/013.
 * e-mail:lomapa@163.com
 */

public class VerifyBackupSelectedMnemonicWordsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public VerifyBackupSelectedMnemonicWordsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String string) {
        helper.setText(R.id.tv_mnemonic_selected_word, string);
    }

}
