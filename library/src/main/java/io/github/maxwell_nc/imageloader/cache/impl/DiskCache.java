package io.github.maxwell_nc.imageloader.cache.impl;

import android.content.Context;

import java.io.File;
import java.io.InputStream;

import io.github.maxwell_nc.imageloader.cache.LruCacheDispatcher;
import io.github.maxwell_nc.imageloader.entity.ImageModel;
import io.github.maxwell_nc.imageloader.utils.IOUtils;
import io.github.maxwell_nc.imageloader.utils.MD5Utils;

/**
 * 本地缓存
 */
public class DiskCache extends AbsModelCache<InputStream, File> {

    public DiskCache(ImageModel model) {
        super(model);
    }

    @Override
    public void setCache(InputStream content) {
        IOUtils.writeStreamToFile(content, IOUtils.getDiskCacheFile(model.getMd5Code(), model.getContext()));
    }

    @Override
    public File getCache() {
        File cacheFile = IOUtils.getDiskCacheFile(model.getMd5Code(), model.getContext());

        if (cacheFile.exists()) {// 本地缓存存在
            return cacheFile;
        } else {
            return null;
        }

    }

    /**
     * 清空指定本地缓存
     */
    public static boolean clearCache(Context context,String key) {
        File cacheFile = IOUtils.getDiskCacheFile(MD5Utils.getMD5String(key), context);
        return cacheFile.exists() && cacheFile.delete();
    }

    /**
     * 回收所有内存
     */
    public static boolean clearAllCache(Context context) {
        return IOUtils.removeDir(new File(IOUtils.getLocalCachePath(context)));
    }

}
