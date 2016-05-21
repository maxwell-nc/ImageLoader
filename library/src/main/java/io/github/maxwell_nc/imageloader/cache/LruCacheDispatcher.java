package io.github.maxwell_nc.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 分配内存缓存，单例
 */
public class LruCacheDispatcher {

    long maxCacheMemory;

    /**
     * 单例
     */
    private final static LruCacheDispatcher dispatcher = new LruCacheDispatcher();


    /**
     * LruCache,存放Bitmap的集合
     */
    private LruCache<String, Bitmap> mMemoryCache;

    /**
     * @return LruCache
     */
    public LruCache<String, Bitmap> getMemoryCache() {
        return mMemoryCache;
    }

    /**
     * 禁止创建实例对象，请不要使用反射创建实例
     */
    private LruCacheDispatcher() {

        maxCacheMemory = Runtime.getRuntime().maxMemory() / 8;// 设置最大Cache占用应用总内存1/8
        mMemoryCache = new LruCache<String, Bitmap>((int) maxCacheMemory) {

            /**
             * 计算返回每一个Bitmap的占用的内存大小
             */
            @Override
            protected int sizeOf(String key, Bitmap value) {

                //计算图片占用空间
                return value.getRowBytes() * value.getHeight();

            }

        };

    }

    /**
     * 获得实例对象
     *
     * @return 单例
     */
    public static LruCacheDispatcher getInstance() {
        return dispatcher;
    }

    /**
     * 清理内存
     */
    public boolean clearCache() {
        mMemoryCache.evictAll();
        return true;
    }

    /**
     * 清空部分内存
     */
    public boolean trimCache(int level) {
        long size = maxCacheMemory * (level / 100);
        mMemoryCache.trimToSize((int) size);
        return true;
    }

}
