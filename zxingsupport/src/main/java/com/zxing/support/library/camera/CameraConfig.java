package com.zxing.support.library.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.client.android.camera.CameraConfigurationUtils;

import java.util.regex.Pattern;

/**
 * 照相机的一些基本配置
 * @author bingbing
 * @date 15/9/22
 */
public class CameraConfig {

    private final String TAG = CameraConfig.class.getSimpleName();


    private static final int TEN_DESIRED_ZOOM = 27;
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    private boolean initialized;
    private Context mContext;


    private int previewFormat;
    private String previewFormatString;
    private Point screenResolution;
    private Point cameraResolution;


    public CameraConfig(Context context) {
        this.mContext = context;
    }

    public void configCamera(Camera camera) {
        // 注意：此设置不会影响到PreviewCallback回调、及其生成的Bitmap图片的数据方向，
        camera.setDisplayOrientation(90);
        initFromCameraParameters(camera);
        setDesiredCameraParameters(camera);
    }


    /**
     * 初始化参数从照相机 只执行一次
     * @param camera
     */
    private void initFromCameraParameters(Camera camera) {
        if (!initialized){
            initialized = true;
            Camera.Parameters parameters = camera.getParameters();
            previewFormat = parameters.getPreviewFormat();
            previewFormatString = parameters.get("preview-format");
            Log.d(TAG, "Default preview format: " + previewFormat + '/' + previewFormatString);
            WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            screenResolution = new Point(display.getWidth(), display.getHeight());
            Log.d(TAG, "Screen resolution: " + screenResolution);

            /** 因为换成了竖屏显示，所以不替换屏幕宽高得出的预览图是变形的 */
            Point screenResolutionForCamera = new Point();
            screenResolutionForCamera.x = screenResolution.x;
            screenResolutionForCamera.y = screenResolution.y;

            if (screenResolution.x < screenResolution.y) {
                screenResolutionForCamera.x = screenResolution.y;
                screenResolutionForCamera.y = screenResolution.x;
            }

            cameraResolution = CameraConfigurationUtils.findBestPreviewSizeValue(parameters, screenResolutionForCamera);
            Log.d(TAG, "Camera resolution: " + cameraResolution);
        }
    }

    /**
     * 设置照相机
     * @param camera
     */
    private void setDesiredCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        Log.d(TAG, "Setting preview size: " + cameraResolution);
        parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
        setFlash(parameters);
        setZoom(parameters);
        //setSharpness(parameters);
        //modify here
        camera.setDisplayOrientation(90);
        camera.setParameters(parameters);
    }


    private void setFlash(Camera.Parameters parameters) {
        // FIXME: This is a hack to turn the flash off on the Samsung Galaxy.
        // And this is a hack-hack to work around a different value on the Behold II
        // Restrict Behold II check to Cupcake, per Samsung's advice
        //if (Build.MODEL.contains("Behold II") &&
        //    CameraManager.SDK_INT == Build.VERSION_CODES.CUPCAKE) {
        if (Build.MODEL.contains("Behold II") && CameraManager.SDK_INT == 3) { // 3 = Cupcake
            parameters.set("flash-value", 1);
        } else {
            parameters.set("flash-value", 2);
        }
        // This is the standard setting to turn the flash off that all devices should honor.
        parameters.set("flash-mode", "off");
    }


    private void setZoom(Camera.Parameters parameters){

        String zoomSupportedString = parameters.get("zoom-supported");
        if (zoomSupportedString != null && !Boolean.parseBoolean(zoomSupportedString)) {
            return;
        }

        int tenDesiredZoom = TEN_DESIRED_ZOOM;

        String maxZoomString = parameters.get("max-zoom");
        if (maxZoomString != null) {
            try {
                int tenMaxZoom = (int) (10.0 * Double.parseDouble(maxZoomString));
                if (tenDesiredZoom > tenMaxZoom) {
                    tenDesiredZoom = tenMaxZoom;
                }
            } catch (NumberFormatException nfe) {
                Log.w(TAG, "Bad max-zoom: " + maxZoomString);
            }
        }

        String takingPictureZoomMaxString = parameters.get("taking-picture-zoom-max");
        if (takingPictureZoomMaxString != null) {
            try {
                int tenMaxZoom = Integer.parseInt(takingPictureZoomMaxString);
                if (tenDesiredZoom > tenMaxZoom) {
                    tenDesiredZoom = tenMaxZoom;
                }
            } catch (NumberFormatException nfe) {
                Log.w(TAG, "Bad taking-picture-zoom-max: " + takingPictureZoomMaxString);
            }
        }

        String motZoomValuesString = parameters.get("mot-zoom-values");
        if (motZoomValuesString != null) {
            tenDesiredZoom = findBestMotZoomValue(motZoomValuesString, tenDesiredZoom);
        }

        String motZoomStepString = parameters.get("mot-zoom-step");
        if (motZoomStepString != null) {
            try {
                double motZoomStep = Double.parseDouble(motZoomStepString.trim());
                int tenZoomStep = (int) (10.0 * motZoomStep);
                if (tenZoomStep > 1) {
                    tenDesiredZoom -= tenDesiredZoom % tenZoomStep;
                }
            } catch (NumberFormatException nfe) {
                // continue
            }
        }

        // Set zoom. This helps encourage the user to pull back.
        // Some devices like the Behold have a zoom parameter
        if (maxZoomString != null || motZoomValuesString != null) {
            parameters.set("zoom", String.valueOf(tenDesiredZoom / 10.0));
        }

        // Most devices, like the Hero, appear to expose this zoom parameter.
        // It takes on values like "27" which appears to mean 2.7x zoom
        if (takingPictureZoomMaxString != null) {
            parameters.set("taking-picture-zoom", tenDesiredZoom);
        }
    }



    private static int findBestMotZoomValue(CharSequence stringValues, int tenDesiredZoom) {
        int tenBestValue = 0;
        for (String stringValue : COMMA_PATTERN.split(stringValues)) {
            stringValue = stringValue.trim();
            double value;
            try {
                value = Double.parseDouble(stringValue);
            } catch (NumberFormatException nfe) {
                return tenDesiredZoom;
            }
            int tenValue = (int) (10.0 * value);
            if (Math.abs(tenDesiredZoom - value) < Math.abs(tenDesiredZoom - tenBestValue)) {
                tenBestValue = tenValue;
            }
        }
        return tenBestValue;
    }

    /**
     * 获取照相机的分辨率
     * @return
     */
    public Point getCameraResolution() {
        return cameraResolution;
    }

    public Point getScreenResolution() {
        return screenResolution;
    }

    public int getPreviewFormat() {
        return previewFormat;
    }

    public String getPreviewFormatString() {
        return previewFormatString;
    }
}
