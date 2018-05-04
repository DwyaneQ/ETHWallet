package com.zxing.support.library.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.zxing.support.library.R;
import com.zxing.support.library.camera.CameraManager;


/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * <p>
 * - 屏幕中间模拟激光扫描线效果的View。它是个纯粹的动画，与二维码识别过程没有任何关系。
 */
public final class FinderViewStyle2 extends View implements QRCodeFindView {

    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;

    private static final int MAX_RESULT_POINTS = 20;

    private static int MIDDLE_LINE_WIDTH;
    private static int MIDDLE_LINE_PADDING;
    private static final int SPEED_DISTANCE = 8;

    private Paint paint;
    private int CORNER_PADDING;
    private int slideTop;
    private int slideBottom;
    private boolean isFirst = true;

    private final int maskColor;

    private CameraManager cameraManager;
    private Bitmap mScanLexer;

    // This constructor is used when the class is built from an XML resource.
    public FinderViewStyle2(Context context, AttributeSet attrs) {
        super(context, attrs);
        CORNER_PADDING = dip2px(context, 0.0F);
        MIDDLE_LINE_PADDING = dip2px(context, 0.0F);
        MIDDLE_LINE_WIDTH = dip2px(context, 3.0F);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 开启反锯齿
        final Resources resources = getResources();
        maskColor = 0x88000000; // 遮掩层颜色
        /**
         * 缓存图片资源。
         */
        mScanLexer = ((BitmapDrawable) getResources().getDrawable(R.mipmap.scan_icon_scanline)).getBitmap();
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        if (frame == null) {
            return;
        }
        drawCover(canvas, frame);
        drawFrameBounds(canvas, frame);
        drawScanLaxer(canvas, frame);
        postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
    }

    /**
     * 绘制扫描线
     */
    private void drawScanLaxer(Canvas canvas, Rect frame) {
        // 初始化中间线滑动的最上边和最下边
        if (isFirst) {
            isFirst = false;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }
        // 绘制中间的线,每次刷新界面，中间的线往下移动SPEED_DISTANCE
        slideTop += SPEED_DISTANCE;
        if (slideTop >= slideBottom) {
            slideTop = frame.top;
        }
        // 从图片资源画扫描线
        Rect lineRect = new Rect();
        lineRect.left = frame.left + MIDDLE_LINE_PADDING;
        lineRect.right = frame.right - MIDDLE_LINE_PADDING;
        lineRect.top = slideTop;
        lineRect.bottom = (slideTop + MIDDLE_LINE_WIDTH);
        canvas.drawBitmap(mScanLexer, null, lineRect, paint);

    }

    // 扫描框边角颜色
    private int innercornercolor = Color.parseColor("#ffffff");
    // 扫描框边角长度
    private int innercornerlength = 60;
    // 扫描框边角宽度
    private int innercornerwidth = 9;


    /**
     * 绘制取景框边框
     *
     * @param canvas
     * @param frame
     */
    private void drawFrameBounds(Canvas canvas, Rect frame) {

        /*paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(frame, paint);*/

        paint.setColor(innercornercolor);
        paint.setStyle(Paint.Style.FILL);

        int corWidth = innercornerwidth;
        int corLength = innercornerlength;

        // 左上角
        canvas.drawRect(frame.left, frame.top, frame.left + corWidth, frame.top
                + corLength, paint);
        canvas.drawRect(frame.left, frame.top, frame.left
                + corLength, frame.top + corWidth, paint);
        // 右上角
        canvas.drawRect(frame.right - corWidth, frame.top, frame.right,
                frame.top + corLength, paint);
        canvas.drawRect(frame.right - corLength, frame.top,
                frame.right, frame.top + corWidth, paint);
        // 左下角
        canvas.drawRect(frame.left, frame.bottom - corLength,
                frame.left + corWidth, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - corWidth, frame.left
                + corLength, frame.bottom, paint);
        // 右下角
        canvas.drawRect(frame.right - corWidth, frame.bottom - corLength,
                frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - corLength, frame.bottom - corWidth,
                frame.right, frame.bottom, paint);
    }

    private void drawCover(Canvas canvas, Rect frame) {
        // 获取屏幕的宽和高
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);
    }


    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void setCamanerManager(CameraManager manager) {
        this.cameraManager = manager;
    }

    @Override
    public void foundPossibleResultPoint(ResultPoint point) {

    }
}
