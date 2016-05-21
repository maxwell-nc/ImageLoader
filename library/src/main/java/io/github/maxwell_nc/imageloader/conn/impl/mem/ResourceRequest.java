package io.github.maxwell_nc.imageloader.conn.impl.mem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.github.maxwell_nc.imageloader.conn.impl.base.MemRequest;

/**
 * 资源请求mipmap/drawable
 */
public class ResourceRequest extends MemRequest {

    @Override
    public Bitmap requestMem() {
        return BitmapFactory.decodeResource(model.getContext().getResources(), Integer.valueOf(model.getPath()));
    }

}
