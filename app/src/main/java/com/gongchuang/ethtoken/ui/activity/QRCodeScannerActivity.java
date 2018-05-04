package com.gongchuang.ethtoken.ui.activity;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gongchuang.ethtoken.R;
import com.gongchuang.ethtoken.base.BaseActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.zxing.support.library.QRCodeSupport;
import com.zxing.support.library.view.FinderViewStyle2;

import butterknife.BindView;

/**
 * Created by dwq on 2018/3/14/014.
 * e-mail:lomapa@163.com
 */

public class QRCodeScannerActivity extends BaseActivity implements QRCodeSupport.OnScanResultListener, View.OnClickListener {
    private static final String TAG = QRCodeScannerActivity.class.getSimpleName();
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 123;
    private static final int QRCODE_RESULT = 124;

    private static final int SCAN_W = 720;
    private static final int SCAN_H = 720;
    @BindView(R.id.scanner_toolsbar)
    Toolbar scannerToolbar;

    private RelativeLayout rlGallery;
    private RelativeLayout rlFlashLight;
    private LinearLayout llBack;
    private String path;
    private SurfaceView mSurfaceView;
    private QRCodeSupport mQRCodeSupport;
    private FinderViewStyle2 mFinderView;

    private boolean isStop = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_scanner;
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
                .titleBar(scannerToolbar, false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
        initScanner();
    }

    private void initScanner() {
        rlGallery = findViewById(R.id.rl_gallery);
        rlFlashLight = (RelativeLayout) findViewById(R.id.rl_flash_light);
        llBack = (LinearLayout) findViewById(R.id.lly_back);
        rlGallery.setOnClickListener(this);
        rlFlashLight.setOnClickListener(this);
        llBack.setOnClickListener(this);

        mFinderView = (FinderViewStyle2) findViewById(R.id.capture_viewfinder_view);
        mSurfaceView = (SurfaceView) findViewById(R.id.sufaceview);
        mQRCodeSupport = new QRCodeSupport(mSurfaceView, mFinderView);
        mQRCodeSupport.setScanResultListener(this);
    }

    @Override
    public void onScanResult(String notNullResult, byte[] resultByte) {
        Intent data = new Intent();
        data.putExtra("scan_result", notNullResult);
        setResult(QRCODE_RESULT, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lly_back:
                finish();
                break;
//            case R.id.rl_gallery:
//                PictureSelector.create(QRCodeScannerActivity.this)
//                        .openGallery(PictureMimeType.ofImage())
//                        .imageSpanCount(4)
//                        .selectionMode(PictureConfig.SINGLE)
//                        .isCamera(true)
//                        .compress(true)
//                        .previewImage(true)
//                        .forResult(PictureConfig.CHOOSE_REQUEST);
//                break;
            case R.id.rl_flash_light:
                mQRCodeSupport.toggleFlashLight();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStop) {
            setContentView(R.layout.activity_qrcode_scanner);
            initScanner();
            isStop = false;
        }
        mQRCodeSupport.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeSupport.onPause();
        isStop = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        isStop = true;
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
//            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
//            path = localMedias.get(0).getPath();
//            // 解析获取的图片中的二维码
//            new DecodeAsyncTask().execute(path);
//        }
//    }


}
