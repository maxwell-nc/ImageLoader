package io.github.maxwell_nc.imageloader.conn.impl.disk;

import android.graphics.Bitmap;

import java.io.File;

import io.github.maxwell_nc.imageloader.conn.impl.base.DiskRequest;
import io.github.maxwell_nc.imageloader.utils.ImageCompress;
import io.github.maxwell_nc.imageloader.utils.Uri2Path;

/**
 * Content://的图片加载
 */
public class MediaRequest extends DiskRequest<File> {

    @Override
    public Bitmap requestMem() {
        return memoryCache.getCache();
    }

    @Override
    public File requestDisk() {
        String path = Uri2Path.getImageAbsolutePath(model.getContext(), model.getUri());
        File file = new File(path);
        return file.exists() ? file : null;
    }

    @Override
    public void cacheInMem(File diskCache) {
        Bitmap bitmap = ImageCompress.getImage(diskCache, model.getViewHeight(), model.getViewWidth());
        memoryCache.setCache(bitmap);
    }
}
