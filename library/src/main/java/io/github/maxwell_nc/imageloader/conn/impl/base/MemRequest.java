package io.github.maxwell_nc.imageloader.conn.impl.base;

/**
 * 内存请求
 */
public abstract class MemRequest extends FullCacheRequest<Void, Void> {

    @Override
    public Void requestWeb() {
        return null;
    }

    @Override
    public Void requestDisk() {
        return null;
    }

    @Override
    public void cacheInDisk(Void webCache) {

    }

    @Override
    public void cacheInMem(Void diskCache) {

    }
}
