package com.gongchuang.ethtoken.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.ui.adapter.SwitchWalletAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dwq on 2018/3/20/020.
 * e-mail:lomapa@163.com
 */

public class SwitchWalletActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_wallet)
    ListView lvWallet;
    private SwitchWalletAdapter switchWalletAdapter;
    private List<String> strings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_switch_activity;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.switch_wallet_title);
    }

    @Override
    public void initDatas() {
        strings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            strings.add(String.valueOf(i));
        }
        switchWalletAdapter = new SwitchWalletAdapter(this, strings, R.layout.list_item_switch_wallet);
        lvWallet.setAdapter(switchWalletAdapter);

    }

    @Override
    public void configViews() {
        lvWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchWalletAdapter.setSelection(position);
            }
        });
    }

}
