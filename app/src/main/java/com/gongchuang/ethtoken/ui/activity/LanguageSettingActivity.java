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

public class LanguageSettingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    TextView tvBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.iv_chinese)
    ImageView ivChinese;
    @BindView(R.id.iv_English)
    ImageView ivEnglish;
    // 0为简体中文 1 English
    private int language = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_language_setting;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.system_setting_language);
        rlBtn.setVisibility(View.VISIBLE);
        tvBtn.setText(R.string.language_setting_save);
    }

    @Override
    public void initDatas() {
        language = SharedPreferencesUtil.getInstance().getInt("language", 0);
        if (language == 0) {
            ivChinese.setVisibility(View.VISIBLE);
            ivEnglish.setVisibility(View.GONE);
        } else if (language == 1) {
            ivChinese.setVisibility(View.GONE);
            ivEnglish.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.rl_chinese, R.id.rl_english, R.id.rl_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_chinese:
                language = 0;
                ivChinese.setVisibility(View.VISIBLE);
                ivEnglish.setVisibility(View.GONE);
                break;
            case R.id.rl_english:
                ivChinese.setVisibility(View.GONE);
                ivEnglish.setVisibility(View.VISIBLE);
                language = 1;
                break;
            case R.id.rl_btn:// 设置语言并保存
                SharedPreferencesUtil.getInstance().putInt("language", language);
                finish();
                break;
        }
    }
}
