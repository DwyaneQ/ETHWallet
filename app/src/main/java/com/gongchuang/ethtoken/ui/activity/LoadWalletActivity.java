package com.gongchuang.ethtoken.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.base.BaseFragment;
import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.ui.adapter.LoadWalletPageFragmentAdapter;
import com.gongchuang.ethtoken.ui.fragment.LoadWalletByMnemonicFragment;
import com.gongchuang.ethtoken.ui.fragment.LoadWalletByObserverFragment;
import com.gongchuang.ethtoken.ui.fragment.LoadWalletByOfficialWalletFragment;
import com.gongchuang.ethtoken.ui.fragment.LoadWalletByPrivateKeyFragment;
import com.gongchuang.ethtoken.utils.UUi;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dwq on 2018/3/22/022.
 * e-mail:lomapa@163.com
 */

public class LoadWalletActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    ImageView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_load_wallet)
    ViewPager vpLoadWallet;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private LoadWalletPageFragmentAdapter loadWalletPageFragmentAdapter;
    private IndicatorViewPager indicatorViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_load_wallet;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.load_wallet_title);
        ivBtn.setImageResource(R.mipmap.ic_transfer_scanner);
        rlBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {
        fragmentList.add(new LoadWalletByMnemonicFragment());
        fragmentList.add(new LoadWalletByOfficialWalletFragment());
        fragmentList.add(new LoadWalletByPrivateKeyFragment());
        fragmentList.add(new LoadWalletByObserverFragment());
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
        loadWalletPageFragmentAdapter = new LoadWalletPageFragmentAdapter(this, getSupportFragmentManager(), fragmentList);
        indicatorViewPager.setAdapter(loadWalletPageFragmentAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpLoadWallet.setOffscreenPageLimit(4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ETHWallet wallet) {
        finish();
    }
}
