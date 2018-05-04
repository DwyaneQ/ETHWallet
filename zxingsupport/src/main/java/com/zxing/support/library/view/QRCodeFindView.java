package com.zxing.support.library.view;

import com.google.zxing.ResultPointCallback;
import com.zxing.support.library.camera.CameraManager;

/**
 * 此接口主要是给findView 设置CameraManager ，来获取识别二维码的位置
 * Created by bingbing on 15/9/22.
 */
public interface QRCodeFindView extends ResultPointCallback {

    void setCamanerManager(CameraManager manager);
}
