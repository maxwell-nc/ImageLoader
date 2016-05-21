package io.github.maxwell_nc.imageloader.conn.impl.base;

import io.github.maxwell_nc.imageloader.cache.impl.DiskCache;
import io.github.maxwell_nc.imageloader.cache.impl.MemoryCache;
import io.github.maxwell_nc.imageloader.entity.ImageModel;

/**
 * 默认实现
 */
public abstract class FullCacheRequest<D, W> extends BaseRequest<D, W> {

    protected DiskCache diskCache;
    protected MemoryCache memoryCache;

    @Override
    public void setModel(ImageModel model) {
        super.setModel(model);
        diskCache = new DiskCache(model);
        memoryCache = new MemoryCache(model);
    }
}
