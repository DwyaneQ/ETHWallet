package com.gongchuang.ethtoken.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by dwq on 2018/3/19/019.
 * e-mail:lomapa@163.com
 */

public class ContactsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    ImageView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.mine_contracts);
        ivBtn.setImageResource(R.mipmap.ic_add);
        rlBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

}
