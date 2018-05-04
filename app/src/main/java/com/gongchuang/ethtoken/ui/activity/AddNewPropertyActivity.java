package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.ui.adapter.AddNewICOAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/16/016.
 * e-mail:lomapa@163.com
 */

public class AddNewPropertyActivity extends BaseActivity {
    private static final int SEARCH_ICO_TOKEN_REQUEST = 1000;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_ico)
    ListView lvIco;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    private List<String> strings;
    private AddNewICOAdapter addNewICOAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_new_property;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.add_new_property_title);
        rlBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {
        strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add(String.valueOf(i));
        }
        addNewICOAdapter = new AddNewICOAdapter(this, strings, R.layout.list_item_add_ico_property);
        lvIco.setAdapter(addNewICOAdapter);
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.rl_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_btn:
                Intent intent = new Intent(this, SearchIcoTokenActivity.class);
                startActivityForResult(intent, SEARCH_ICO_TOKEN_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
