package io.github.maxwell_nc.imageloader.entity;

import android.content.Context;
import android.net.Uri;

import java.io.Serializable;

import io.github.maxwell_nc.imageloader.utils.MD5Utils;

/**
 * 图片数据模型
 */
public class ImageModel implements Serializable {

    /**
     * 上下文
     */
    private Context context;

    /**
     * 图片地址
     */
    private Uri uri;

    /**
     * 图片地址的MD5码，自动计算
     */
    private String md5Code;

    /**
     * 控件高度
     */
    private int viewHeight;

    /**
     * 控件宽度
     */
    private int viewWidth;

    public ImageModel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        this.md5Code = MD5Utils.getMD5String(getPath());
    }

    public void setPath(String path){
        this.uri = Uri.parse(path);
    }

    public String getPath(){
        return uri.toString();
    }

    public String getMd5Code() {
        return md5Code;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }
}
