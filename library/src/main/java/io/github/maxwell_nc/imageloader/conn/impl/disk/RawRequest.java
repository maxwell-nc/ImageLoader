package io.github.maxwell_nc.imageloader.conn.impl.disk;

import android.content.ContentResolver;
import android.graphics.Bitmap;

import java.io.FileNotFoundException;
import java.io.InputStream;

import io.github.maxwell_nc.imageloader.conn.impl.base.DiskRequest;
import io.github.maxwell_nc.imageloader.utils.ImageCompress;

/**
 * raw目录的图片加载
 */
public class RawRequest extends DiskRequest<InputStream> {

    @Override
    public Bitmap requestMem() {
        return memoryCache.getCache();
    }

    @Override
    public InputStream requestDisk() {
        InputStream inputStream = null;
        try {
            ContentResolver contentResolver = model.getContext().getContentResolver();
            inputStream = contentResolver.openInputStream(model.getUri());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public void cacheInMem(InputStream diskCache) {
        Bitmap bitmap = ImageCompress.getImage(diskCache, model.getViewHeight(), model.getViewWidth());
        memoryCache.setCache(bitmap);
    }
}
