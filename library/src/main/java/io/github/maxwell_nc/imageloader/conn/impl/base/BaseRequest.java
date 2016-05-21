package io.github.maxwell_nc.imageloader.conn.impl.base;

import io.github.maxwell_nc.imageloader.conn.ICacheRequest;
import io.github.maxwell_nc.imageloader.entity.ImageModel;

/**
 * 默认实现
 */
public abstract class BaseRequest<D, W> implements ICacheRequest<D, W> {

    protected ImageModel model;

    @Override
    public void setModel(ImageModel model) {
        this.model = model;
    }

    @Override
    public String getRequestPath() {
        return model.getPath();
    }
}
