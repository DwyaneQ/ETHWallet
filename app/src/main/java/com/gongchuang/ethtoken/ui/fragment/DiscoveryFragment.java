package com.gongchuang.ethtoken.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseFragment;
import com.gongchuang.ethtoken.ui.adapter.DiscoveryApplicationAdapter;
import com.gongchuang.ethtoken.ui.adapter.DiscoveryNewsAdapter;
import com.gongchuang.ethtoken.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dwq on 2018/3/13/013.
 * e-mail:lomapa@163.com
 */

public class DiscoveryFragment extends BaseFragment {
    @BindView(R.id.lv_discovery)
    ListView lvDiscovery;
    private List<Integer> images;
    private Banner banner;
    private RecyclerView rvApplication;
    private LinearLayoutManager linearLayoutManager;

    private List<String> strings;
    private DiscoveryApplicationAdapter discoveryApplicationAdapter;
    private DiscoveryNewsAdapter discoveryNewsAdapter;
    private View headerView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        images = new ArrayList<>();
        images.add(R.mipmap.banner1);
        images.add(R.mipmap.banner2);
        images.add(R.mipmap.banner3);
        initHeaderView();
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader(R.drawable.place_img_shape));
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //设置布局管理器
        rvApplication.setLayoutManager(linearLayoutManager);
        strings = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            strings.add(String.valueOf(i));
        }
        discoveryApplicationAdapter = new DiscoveryApplicationAdapter(R.layout.list_item_application, strings);
        rvApplication.setAdapter(discoveryApplicationAdapter);

        discoveryNewsAdapter = new DiscoveryNewsAdapter(getContext(), R.layout.list_item_discovery, strings);
        lvDiscovery.setAdapter(discoveryNewsAdapter);
        lvDiscovery.addHeaderView(headerView);
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.discovery_list_header, null);
        banner = headerView.findViewById(R.id.banner);
        rvApplication = headerView.findViewById(R.id.rv_application);

    }

    @Override
    public void configViews() {

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

}
