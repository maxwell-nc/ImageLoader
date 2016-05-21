package io.github.maxwell_nc.imageloader.engine;

import android.content.ContentResolver;

import io.github.maxwell_nc.imageloader.conn.ICacheRequest;
import io.github.maxwell_nc.imageloader.conn.impl.disk.AssetRequest;
import io.github.maxwell_nc.imageloader.conn.impl.disk.LocalRequest;
import io.github.maxwell_nc.imageloader.conn.impl.disk.MediaRequest;
import io.github.maxwell_nc.imageloader.conn.impl.disk.RawRequest;
import io.github.maxwell_nc.imageloader.conn.impl.mem.ResourceRequest;
import io.github.maxwell_nc.imageloader.conn.impl.web.WebImageRequest;
import io.github.maxwell_nc.imageloader.entity.ImageModel;

/**
 * 路径解析器
 */
public class PathParser {

    public static final String PREFIX_ASSERT = "file:///android_asset/";
    public static final String PREFIX_RESOURCE = ContentResolver.SCHEME_ANDROID_RESOURCE + "://";

    public enum Type {
        ASSERT,//xxx.jpg
        RAW,//R.raw.xxx
        RESOURCE,//R.drawable.xxx R.mipmap.xxx R.raw.xxx
        MEDIA,//content://
        LOCAL,//file://
        WEB//http:// https:// www.
    }

    public static ICacheRequest getRequest(Type type) {
        switch (type) {
            case ASSERT:
                return new AssetRequest();
            case RAW:
                return new RawRequest();
            case MEDIA:
                return new MediaRequest();
            case RESOURCE:
                return new ResourceRequest();
            case LOCAL:
                return new LocalRequest();
            case WEB:
                return new WebImageRequest();
        }
        return null;
    }

}
