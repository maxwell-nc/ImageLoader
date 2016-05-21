package io.github.maxwell_nc.imageloader.cache.impl;

import android.graphics.Bitmap;

import io.github.maxwell_nc.imageloader.cache.LruCacheDispatcher;
import io.github.maxwell_nc.imageloader.entity.ImageModel;
import io.github.maxwell_nc.imageloader.utils.MD5Utils;

/**
 * 内存缓存
 */
public class MemoryCache extends AbsModelCache<Bitmap, Bitmap> {

    public MemoryCache(ImageModel model) {
        super(model);
    }

    @Override
    public void setCache(Bitmap content) {
        if (content != null) {
            // 加入内存缓存
            LruCacheDispatcher.getInstance().getMemoryCache().put(model.getMd5Code(), content);
        }
    }

    @Override
    public Bitmap getCache() {
        return LruCacheDispatcher.getInstance().getMemoryCache().get(model.getMd5Code());
    }

    /**
     * 清理指定内存缓存
     */
    public static boolean clearCache(String key) {
        LruCacheDispatcher.getInstance().getMemoryCache().remove(MD5Utils.getMD5String(key));
        return true;
    }

    /**
     * 回收所有内存
     */
    public static boolean clearAllCache() {
        LruCacheDispatcher.getInstance().clearCache();
        return true;
    }

    /**
     * 回收部分内存
     */
    public static boolean trimCache(int level) {
        LruCacheDispatcher.getInstance().trimCache(level);
        return true;
    }

}
