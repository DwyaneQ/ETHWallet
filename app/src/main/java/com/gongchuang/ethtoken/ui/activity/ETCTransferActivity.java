package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gongchuang.ethtoken.utils.ToastUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dwq on 2018/3/19/019.
 * e-mail:lomapa@163.com
 */

public class ETCTransferActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    ImageView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.et_transfer_address)
    EditText etTransferAddress;
    @BindView(R.id.lly_contacts)
    LinearLayout llyContacts;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.tv_gas_cost)
    TextView tvGasCost;
    @BindView(R.id.lly_gas)
    LinearLayout llyGas;
    @BindView(R.id.et_hex_data)
    EditText etHexData;
    @BindView(R.id.lly_advance_param)
    LinearLayout llyAdvanceParam;
    @BindView(R.id.advanced_switch)
    Switch advancedSwitch;

    private static final int QRCODE_SCANNER_REQUEST = 1100;

    private static final double miner_min = 0.00002520;
    private static final double miner_max = 0.00252000;

    @Override
    public int getLayoutId() {
        return R.layout.activity_etc_transfer;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.etc_transfer_title);
        ivBtn.setImageResource(R.mipmap.ic_transfer_scanner);
        rlBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        advancedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llyAdvanceParam.setVisibility(View.VISIBLE);
                    llyGas.setVisibility(View.GONE);
                } else {
                    llyAdvanceParam.setVisibility(View.GONE);
                    llyGas.setVisibility(View.VISIBLE);

                }
            }
        });
        final DecimalFormat formater = new DecimalFormat();
        //保留几位小数
        formater.setMaximumFractionDigits(8);
        //模式  四舍五入
        formater.setRoundingMode(RoundingMode.UP);
        final String etherUnit = getString(R.string.etc_transfer_ether_unit);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String cost = null;
                if (progress == 0) {
                    cost = formater.format(miner_min) + "0";
                } else if (progress == 100) {
                    cost = formater.format(miner_max) + "000";
                } else {
                    double p = progress / 100f;
                    double d = (miner_max - miner_min) * p + miner_min;
                    cost = formater.format(d);
                    for (int i = 0; i < 10 - cost.length(); i++) {
                        cost = String.valueOf(cost + "0");
                    }
                }
                tvGasCost.setText(String.valueOf(cost + etherUnit));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick({R.id.rl_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_btn:
                Intent intent = new Intent(ETCTransferActivity.this, QRCodeScannerActivity.class);
                startActivityForResult(intent, QRCODE_SCANNER_REQUEST);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) {
            if (data != null) {
                String scanResult = data.getStringExtra("scan_result");
                // 对扫描结果进行处理
                ToastUtils.showLongToast(scanResult);
            }
        }
    }

}
