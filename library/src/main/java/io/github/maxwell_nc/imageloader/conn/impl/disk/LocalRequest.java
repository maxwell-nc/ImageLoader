package io.github.maxwell_nc.imageloader.conn.impl.disk;

import android.graphics.Bitmap;

import java.io.File;

import io.github.maxwell_nc.imageloader.conn.impl.base.DiskRequest;
import io.github.maxwell_nc.imageloader.utils.ImageCompress;

/**
 * 本地的图片加载
 */
public class LocalRequest extends DiskRequest<File> {

    @Override
    public Bitmap requestMem() {
        return memoryCache.getCache();
    }

    @Override
    public File requestDisk() {
        File file = new File(model.getUri().getPath());
        return file.exists() ? file : null;
    }

    @Override
    public void cacheInMem(File diskCache) {
        Bitmap bitmap = ImageCompress.getImage(diskCache, model.getViewHeight(), model.getViewWidth());
        memoryCache.setCache(bitmap);
    }
}
