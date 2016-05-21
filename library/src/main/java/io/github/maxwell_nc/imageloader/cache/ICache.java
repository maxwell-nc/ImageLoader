package io.github.maxwell_nc.imageloader.cache;

/**
 * 缓存接口
 */
public interface ICache<I,O> {

    /**
     * 设置缓存
     */
    void setCache(I content);

    /**
     * 获取缓存
     */
    O getCache();

}
