package io.github.maxwell_nc.imageloader.cache.impl;

import io.github.maxwell_nc.imageloader.cache.ICache;
import io.github.maxwell_nc.imageloader.entity.ImageModel;

/**
 * 抽象的图像缓存
 */
public abstract class AbsModelCache<I, O> implements ICache<I, O> {

    protected ImageModel model;

    public AbsModelCache(ImageModel model) {
        this.model = model;
    }

}
