package io.github.maxwell_nc.imageloader.conn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

import io.github.maxwell_nc.imageloader.entity.ImageModel;

/**
 * 缓存请求接口
 *
 * @param <D> 本地缓存类型
 * @param <W> 网络数据类型
 */
public interface ICacheRequest<D, W> {

    /**
     * 设置数据模型
     */
    void setModel(ImageModel model);

    /**
     * 获取请求地址，用于打印日志
     */
    String getRequestPath();

    /**
     * 请求内存
     */
    Bitmap requestMem();

    /**
     * 请求本地
     */
    D requestDisk();

    /**
     * 请求网络
     */
    W requestWeb();

    /**
     * 缓存到内存
     */
    void cacheInMem(D diskCache);

    /**
     * 缓存到本地
     */
    void cacheInDisk(W webCache);

}
