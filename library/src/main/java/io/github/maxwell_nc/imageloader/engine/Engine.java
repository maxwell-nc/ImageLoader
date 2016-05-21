package io.github.maxwell_nc.imageloader.engine;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.InputStream;

import io.github.maxwell_nc.imageloader.cache.impl.DiskCache;
import io.github.maxwell_nc.imageloader.cache.impl.MemoryCache;
import io.github.maxwell_nc.imageloader.conn.ICacheRequest;
import io.github.maxwell_nc.imageloader.entity.ImageModel;
import io.github.maxwell_nc.imageloader.utils.LogUtils;

/**
 * 图片处理
 */
public class Engine {

    LogUtils.LogInfo log;

    /**
     * 从缓存里获取图片
     */
    public Bitmap getImage(ICacheRequest request) {
        log = LogUtils.log();
        log.addMsg("加载图片：" + request.getRequestPath());
        Bitmap bitmap = absGetImage(request, 1);
        log.addMsg("加载图片：" + (bitmap != null ? "成功" : "失败"));
        log.build().execute();
        return bitmap;
    }

    private <D, W> Bitmap absGetImage(ICacheRequest<D, W> request, int time) {


        if (time > 3) {//递归最大次数
            return null;
        }

        log.addMsg("读取内存缓存");
        Bitmap bitmap = request.requestMem();//请求内存


        if (bitmap == null) {

            log.addMsg("没有内存缓存，读取本地缓存");
            D diskCache = request.requestDisk();//请求本地

            if (diskCache == null) {

                log.addMsg("没有本地缓存，请求网络数据");
                W webCache = request.requestWeb();//请求网络

                if (webCache == null) {
                    return null;
                }

                log.addMsg("请求网络数据成功，设置本地缓存");
                request.cacheInDisk(webCache);

            } else {
                log.addMsg("读取本地缓存成功，设置内存缓存");
                request.cacheInMem(diskCache);
            }

            return absGetImage(request, time + 1);

        }

        return bitmap;

    }

}
