package com.gongchuang.ethtoken.ui.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.utils.TKeybord;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/16/016.
 * e-mail:lomapa@163.com
 */

public class SearchIcoTokenActivity extends BaseActivity {
    @BindView(R.id.search_bar)
    Toolbar searchBar;
    @BindView(R.id.et_search)
    EditText etSearch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_ico_token;
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
                .titleBar(searchBar, false)
                .transparentStatusBar()
                .statusBarDarkFont(true, 1f)
                .navigationBarColor(R.color.white)
                .init();

        initSoftKeyboard();
    }

    private void initSoftKeyboard() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String searchWords = etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchWords)) {

                    }
//                    Toast.makeText(AddMemberActivity.this,"呵呵",Toast.LENGTH_SHORT).show();
                    // search pressed and perform your functionality.
                }
                return false;
            }

        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                TKeybord.openKeybord(etSearch);
            }

        }, 500);
    }

    @OnClick({R.id.rl_btn, R.id.tv_upload_token})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_btn:
                TKeybord.closeKeybord(etSearch);
                finish();
                break;
            case R.id.tv_upload_token:
                break;
        }
    }

}
