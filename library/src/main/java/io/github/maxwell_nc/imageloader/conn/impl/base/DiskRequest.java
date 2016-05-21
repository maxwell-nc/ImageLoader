package io.github.maxwell_nc.imageloader.conn.impl.base;

/**
 * 本地请求
 */
public abstract class DiskRequest<D> extends FullCacheRequest<D, Void> {

    @Override
    public Void requestWeb() {
        return null;
    }

    @Override
    public void cacheInDisk(Void webCache) {

    }

}
