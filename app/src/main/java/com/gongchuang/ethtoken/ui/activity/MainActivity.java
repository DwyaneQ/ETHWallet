package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.domain.ETHWallet;
import com.gongchuang.ethtoken.event.LoadWalletSuccessEvent;
import com.gongchuang.ethtoken.ui.adapter.HomePagerAdapter;
import com.gongchuang.ethtoken.ui.fragment.DiscoveryFragment;
import com.gongchuang.ethtoken.ui.fragment.MineFragment;
import com.gongchuang.ethtoken.ui.fragment.PropertyFragment;
import com.gongchuang.ethtoken.utils.ToastUtils;
import com.gongchuang.ethtoken.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.vp_home)
    NoScrollViewPager vpHome;
    @BindView(R.id.iv_mall)
    ImageView ivMall;
    @BindView(R.id.tv_mall)
    TextView tvMall;
    @BindView(R.id.lly_mall)
    LinearLayout llyMall;
    @BindView(R.id.iv_news)
    ImageView ivNews;
    @BindView(R.id.tv_news)
    TextView tvNews;
    @BindView(R.id.lly_news)
    LinearLayout llyNews;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.lly_mine)
    LinearLayout llyMine;

    private HomePagerAdapter homePagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        ivMall.setSelected(true);
        tvMall.setSelected(true);

        llyNews.setOnClickListener(this);
        llyMall.setOnClickListener(this);
        llyMine.setOnClickListener(this);

        vpHome.setOffscreenPageLimit(10);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PropertyFragment());
        fragmentList.add(new DiscoveryFragment());
        fragmentList.add(new MineFragment());
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), fragmentList);
        vpHome.setAdapter(homePagerAdapter);
        vpHome.setCurrentItem(0, false);
    }

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtils.showToast(getString(R.string.exit_tips));
                return true;
            } else {
                finish(); // 退出
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onClick(View v) {
        setAllUnselected();
        switch (v.getId()) {
            case R.id.lly_mall:// 商场
                ivMall.setSelected(true);
                tvMall.setSelected(true);
                vpHome.setCurrentItem(0, false);
                break;
            case R.id.lly_news:// 资讯
                ivNews.setSelected(true);
                tvNews.setSelected(true);
                vpHome.setCurrentItem(1, false);
                break;
            case R.id.lly_mine:// 我的
                ivMine.setSelected(true);
                tvMine.setSelected(true);
                vpHome.setCurrentItem(2, false);
                break;
        }
    }

    private void setAllUnselected() {
        ivNews.setSelected(false);
        tvNews.setSelected(false);
        ivMall.setSelected(false);
        tvMall.setSelected(false);
        ivMine.setSelected(false);
        tvMine.setSelected(false);
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
        vpHome.setCurrentItem(0, false);
        setAllUnselected();
        ivMall.setSelected(true);
        tvMall.setSelected(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(LoadWalletSuccessEvent loadWalletSuccessEvent) {
        startActivity(new Intent(this, WalletMangerActivity.class));
        vpHome.setCurrentItem(0, false);
        setAllUnselected();
        ivMall.setSelected(true);
        tvMall.setSelected(true);
    }

}
