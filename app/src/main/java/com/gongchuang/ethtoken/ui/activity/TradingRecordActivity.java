package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.ui.adapter.MessageCenterAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/20/020.
 * e-mail:lomapa@163.com
 */

public class TradingRecordActivity extends BaseActivity {
    private static final int SWITCH_WALLET_REQUEST = 1101;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    ImageView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.lv_trading_record)
    ListView lvTradingRecord;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private List<String> strings;
    private MessageCenterAdapter drawerWalletAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trading_record;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.trading_record_title);
        ivBtn.setImageResource(R.mipmap.ic_acount_switch);
        rlBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {
        strings = new ArrayList<>();
        drawerWalletAdapter = new MessageCenterAdapter(this, strings, R.layout.list_item_news_center);
        lvTradingRecord.setAdapter(drawerWalletAdapter);
    }

    @Override
    public void configViews() {
//        swipeRefresh.setRefreshing(true);
    }

    @OnClick(R.id.rl_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_btn:
                Intent intent = new Intent(this,SwitchWalletActivity.class);
                startActivityForResult(intent,SWITCH_WALLET_REQUEST);
                break;
        }
    }
}
