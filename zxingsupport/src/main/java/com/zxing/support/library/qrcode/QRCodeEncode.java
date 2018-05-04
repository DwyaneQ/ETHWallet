package com.zxing.support.library.qrcode;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * 将文本信息生成QRCode图片
 */
public final class QRCodeEncode {

    public static final String TAG = QRCodeDecode.class.getSimpleName();

    private final Builder mConfigBuilder;
    private final MultiFormatWriter mMultiFormatWriter;

    private QRCodeEncode(Builder configBuilder) {
        mConfigBuilder = configBuilder;
        mMultiFormatWriter = new MultiFormatWriter();
    }

    /**
     * 将文本信息生成二维码图片
     * @param content 文本内容
     * @return Bitmap对象，如果生成失败，返回null。
     */
    public Bitmap encode(final String content){
        return encode(content, mConfigBuilder.mOutputBitmapWidth, mConfigBuilder.mOutputBitmapHeight);
    }

    /**
     * 将文本信息生成二维码图片，指定输出图片宽度和高度
     * @param content 文本内容
     * @param width 输出图片宽度
     * @param height 输出图片高度
     * @return Bitmap对象，如果生成失败，返回null。
     */
    public Bitmap encode(final String content, int width, int height){
        if (TextUtils.isEmpty(content)){
            throw new IllegalArgumentException("QRCode encode content CANNOT be empty");
        }
        final long start = System.currentTimeMillis();
        final Map<EncodeHintType,Object> hints = new EnumMap<>(EncodeHintType.class);
        // 字符编码
        hints.put(EncodeHintType.CHARACTER_SET, mConfigBuilder.mCharset);
        if (mConfigBuilder.mHintMargin >= 0){
            // 输出图片外边距
            hints.put(EncodeHintType.MARGIN, mConfigBuilder.mHintMargin);
        }
        BitMatrix result;
        try {
            result = mMultiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (Exception e) {
            Log.w(TAG, e);
            return null;
        }
        int finalBitmapWidth = result.getWidth();
        int finalBitmapHeight = result.getHeight();
        final int[] pixels = new int[finalBitmapWidth * finalBitmapHeight];
        for (int y = 0; y < finalBitmapHeight; y++) {
            int offset = y * finalBitmapWidth;
            for (int x = 0; x < finalBitmapWidth; x++) {
                pixels[offset + x] = result.get(x, y) ? mConfigBuilder.mCodeColor : mConfigBuilder.mBackgroundColor;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(finalBitmapWidth, finalBitmapHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, finalBitmapWidth, 0, 0, finalBitmapWidth, finalBitmapHeight);

        final long end = System.currentTimeMillis();
        Log.d(TAG, "QRCode encode in " + (end - start) + "ms");
        return bitmap;
    }



    public static class Builder{

        private int mBackgroundColor = 0xFFFFFFFF;
        private int mCodeColor = 0xFF000000;
        private String mCharset = "UTF-8";
        private int mOutputBitmapWidth;
        private int mOutputBitmapHeight;
        private int mHintMargin = -1;

        /**
         * 设置生成二维码图片的背景色
         * @param backgroundColor 背景色，如 0xFFFFFFFF
         * @return Builder，用于链式调用
         */
        public Builder setBackgroundColor(int backgroundColor) {
            mBackgroundColor = backgroundColor;
            return this;
        }

        /**
         * 设置二维码的编码块颜色
         * @param codeColor 编码块颜色，如 0xFF000000
         * @return Builder，用于链式调用
         */
        public Builder setCodeColor(int codeColor) {
            mCodeColor = codeColor;
            return this;
        }

        /**
         * 设置文本编码格式
         * @param charset 字符编码格式
         * @return Builder，用于链式调用
         */
        public Builder setCharset(String charset) {
            if (TextUtils.isEmpty(charset)){
                throw new IllegalArgumentException("Illegal charset: " + charset);
            }
            mCharset = charset;
            return this;
        }

        /**
         * 设置输出图片的宽度
         * @param outputBitmapWidth 宽度，单位：px
         * @return Builder，用于链式调用
         */
        public Builder setOutputBitmapWidth(int outputBitmapWidth) {
            mOutputBitmapWidth = outputBitmapWidth;
            return this;
        }

        /**
         * 设置输出图片的高度
         * @param outputBitmapHeight 高度，单位：px
         * @return Builder，用于链式调用
         */
        public Builder setOutputBitmapHeight(int outputBitmapHeight) {
            mOutputBitmapHeight = outputBitmapHeight;
            return this;
        }

        /**
         * 设置输出二维码与图片边缘的距离
         * @param hintMargin 距离值，正整数。
         */
        public Builder setOutputBitmapPadding(int hintMargin) {
            mHintMargin = hintMargin;
            return this;
        }


        /**
         * @return QRCode生成器对象
         */
        public QRCodeEncode build(){
            return new QRCodeEncode(this);
        }
    }
}
