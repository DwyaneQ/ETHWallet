package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.base.BaseFragment;
import com.gongchuang.ethtoken.ui.adapter.DeriveKeystorePageFragmentAdapter;
import com.gongchuang.ethtoken.ui.fragment.DeriveKeystoreQRCodeFragment;
import com.gongchuang.ethtoken.ui.fragment.DeriveKeystoreStringFragment;
import com.gongchuang.ethtoken.utils.UUi;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dwq on 2018/3/27/027.
 * e-mail:lomapa@163.com
 */

public class DeriveKeystoreActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_load_wallet)
    ViewPager vpLoadWallet;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private IndicatorViewPager indicatorViewPager;
    private DeriveKeystorePageFragmentAdapter deriveKeystorePageFragmentAdapter;
    private DeriveKeystoreStringFragment deriveKeystoreStringFragment;
    private DeriveKeystoreQRCodeFragment deriveKeystoreQRCodeFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_load_wallet;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.derive_keystore_title);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        String walletKeystore = intent.getStringExtra("walletKeystore");
        deriveKeystoreStringFragment = new DeriveKeystoreStringFragment();
        deriveKeystoreQRCodeFragment = new DeriveKeystoreQRCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("walletKeystore",walletKeystore);//这里的values就是我们要传的值
        deriveKeystoreStringFragment.setArguments(bundle);
        deriveKeystoreQRCodeFragment.setArguments(bundle);
        fragmentList.add(deriveKeystoreStringFragment);
        fragmentList.add(deriveKeystoreQRCodeFragment);
    }

    @Override
    public void configViews() {
        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.etc_transfer_advanced_setting_help_text_color), getResources().getColor(R.color.discovery_application_item_name_color))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(this, indicatorView, getResources().getColor(R.color.etc_transfer_advanced_setting_help_text_color), UUi.dip2px(2)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpLoadWallet);
        deriveKeystorePageFragmentAdapter = new DeriveKeystorePageFragmentAdapter(this, getSupportFragmentManager(), fragmentList);
        indicatorViewPager.setAdapter(deriveKeystorePageFragmentAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpLoadWallet.setOffscreenPageLimit(4);
    }

}
