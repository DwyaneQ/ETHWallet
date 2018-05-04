package com.gongchuang.ethtoken.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseFragment;
import com.gongchuang.ethtoken.ui.activity.ContactsActivity;
import com.gongchuang.ethtoken.ui.activity.MessageCenterActivity;
import com.gongchuang.ethtoken.ui.activity.SystemSettingActivity;
import com.gongchuang.ethtoken.ui.activity.TradingRecordActivity;
import com.gongchuang.ethtoken.ui.activity.WalletMangerActivity;

import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/13/013.
 * e-mail:lomapa@163.com
 */

public class MineFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
    }

    @OnClick({R.id.lly_wallet_manage, R.id.lly_contacts, R.id.lly_msg_center
            , R.id.lly_system_setting, R.id.lly_trade_recode})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lly_contacts:
                intent = new Intent(getActivity(), ContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_wallet_manage:
                intent = new Intent(getActivity(), WalletMangerActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_msg_center:
                intent = new Intent(getActivity(), MessageCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_system_setting:
                intent = new Intent(getActivity(), SystemSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_trade_recode:
                intent = new Intent(getActivity(), TradingRecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
