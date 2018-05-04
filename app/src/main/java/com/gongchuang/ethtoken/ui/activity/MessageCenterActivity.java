package com.gongchuang.ethtoken.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.ui.adapter.MessageCenterAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dwq on 2018/3/20/020.
 * e-mail:lomapa@163.com
 */

public class MessageCenterActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.lv_message_center)
    ListView lvMessageCenter;
    private List<String> strings;
    private MessageCenterAdapter drawerWalletAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initToolBar() {
        rlBtn.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.news_center_title);
    }

    @Override
    public void initDatas() {
        strings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            strings.add(String.valueOf(i));
        }
        drawerWalletAdapter = new MessageCenterAdapter(this, strings, R.layout.list_item_news_center);
        lvMessageCenter.setAdapter(drawerWalletAdapter);
    }

    @Override
    public void configViews() {

    }

}
