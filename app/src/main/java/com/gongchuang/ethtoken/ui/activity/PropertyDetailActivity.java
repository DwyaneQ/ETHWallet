package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.view.View;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/19/019.
 * e-mail:lomapa@163.com
 */

public class PropertyDetailActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_property_detail;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 1f)
                .init();
    }

    @OnClick({R.id.lly_back, R.id.lly_transfer, R.id.lly_gathering})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lly_back:
                finish();
                break;
            case R.id.lly_transfer:
                intent = new Intent(mContext, ETCTransferActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_gathering:
                intent = new Intent(mContext, GatheringQRCodeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
