package com.zxing.support.library.camera;

import android.hardware.Camera;

/**
 *
 * 自动对焦监听
 * @author bingbing
 * @date 15/9/22
 */
public interface AutoFucesListener {

    public void onAutoFocus(boolean success, Camera camera);
}
