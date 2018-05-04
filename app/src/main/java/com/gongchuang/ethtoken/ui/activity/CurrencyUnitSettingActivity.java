package com.gongchuang.ethtoken.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/20/020.
 * e-mail:lomapa@163.com
 */

public class CurrencyUnitSettingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    TextView tvBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.iv_cny)
    ImageView ivCNY;
    @BindView(R.id.iv_usd)
    ImageView ivUSD;
    // 0为CNY 1 USD
    private int currencyUnit = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_currency_unit_setting;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.system_setting_language);
        rlBtn.setVisibility(View.VISIBLE);
        tvBtn.setText(R.string.language_setting_save);
    }

    @Override
    public void initDatas() {
        currencyUnit = SharedPreferencesUtil.getInstance().getInt("currencyUnit", 0);
        if (currencyUnit == 0) {
            ivCNY.setVisibility(View.VISIBLE);
            ivUSD.setVisibility(View.GONE);
        } else if (currencyUnit == 1) {
            ivCNY.setVisibility(View.GONE);
            ivUSD.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.rl_cny, R.id.rl_usd, R.id.rl_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_cny:
                currencyUnit = 0;
                ivCNY.setVisibility(View.VISIBLE);
                ivUSD.setVisibility(View.GONE);
                break;
            case R.id.rl_usd:
                currencyUnit = 1;
                ivCNY.setVisibility(View.GONE);
                ivUSD.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_btn:// 设置语言并保存
                SharedPreferencesUtil.getInstance().putInt("currencyUnit", currencyUnit);
                finish();
                break;
        }
    }
}
