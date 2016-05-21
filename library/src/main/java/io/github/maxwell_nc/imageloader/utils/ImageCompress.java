package io.github.maxwell_nc.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 压缩bitmap工具类
 */
public class ImageCompress {

    /**
     * 根据View宽高自动压缩图片
     *
     * @param viewHeight View的高度
     * @param viewWidth  View的宽度
     * @return Bitmap 压缩后的图片对象
     */
    public static Bitmap getImage(File imageFile, int viewHeight, int viewWidth) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        // 计算图片长宽
        options.inJustDecodeBounds = true;

        if (imageFile != null) {
            BitmapFactory.decodeFile(imageFile.getPath(), options);
        }

        int height = options.outHeight;
        int width = options.outWidth;

        calculateSampleSize(height, width, viewHeight, viewWidth, options);

        if (imageFile != null) {
            return BitmapFactory.decodeFile(imageFile.getPath(), options);
        }
        return null;

    }

    public static Bitmap getImage(InputStream imageStream, int viewHeight, int viewWidth) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        // 计算图片长宽
        options.inJustDecodeBounds = true;

        if (imageStream != null) {
            BitmapFactory.decodeStream(imageStream, null, options);
        }

        int height = options.outHeight;
        int width = options.outWidth;

        calculateSampleSize(height, width, viewHeight, viewWidth, options);

        if (imageStream != null) {
            return BitmapFactory.decodeStream(imageStream, null, options);
        } else {
            return null;
        }

    }

    private static void calculateSampleSize(int height, int width, int viewHeight, int viewWidth, BitmapFactory.Options options) {
        // 默认不缩放
        int compressSampleSize = 1;

        if (height > 0 && width > 0 && viewHeight > 0 && viewWidth > 0) {//防止为0x0
            // 根据长宽计算最佳采样大小
            if (height > viewHeight || width > viewWidth) {
                int heightRadio = height / viewHeight;
                int widthRadio = width / viewWidth;

                compressSampleSize = heightRadio > widthRadio ? heightRadio : widthRadio;
            }
        }

        options.inSampleSize = compressSampleSize;
        // options.inPreferredConfig = Bitmap.Config.RGB_565;

        // 真正解析Bitmap
        options.inJustDecodeBounds = false;
    }

}
