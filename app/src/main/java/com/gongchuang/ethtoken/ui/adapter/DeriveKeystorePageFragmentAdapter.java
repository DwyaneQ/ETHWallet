package com.gongchuang.ethtoken.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.List;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

/**
 * Created by dwq on 2017/7/27/027.
 * e-mail:lomapa@163.com
 */

public class DeriveKeystorePageFragmentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private final int[] TITLES = {R.string.load_wallet_indicator_mnemonic
            , R.string.load_wallet_indicator_official};


    private List<BaseFragment> fragmentList;
    private FragmentManager fm;
    private Context mContext;

    private LayoutInflater inflater;

    public DeriveKeystorePageFragmentAdapter(Context context, FragmentManager fragmentManager, List<BaseFragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.fm = fm;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Override
    public Fragment getFragmentForPage(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.load_wallet_indicator_tab, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(TITLES[position]);
        return textView;
    }

    public void setFragments(List<BaseFragment> fragments) {
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }
}
