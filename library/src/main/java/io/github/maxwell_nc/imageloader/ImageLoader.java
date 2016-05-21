package io.github.maxwell_nc.imageloader;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import io.github.maxwell_nc.imageloader.cache.impl.DiskCache;
import io.github.maxwell_nc.imageloader.cache.impl.MemoryCache;
import io.github.maxwell_nc.imageloader.conn.ICacheRequest;
import io.github.maxwell_nc.imageloader.engine.LoadTask;
import io.github.maxwell_nc.imageloader.engine.PathParser;
import io.github.maxwell_nc.imageloader.entity.ImageModel;
import io.github.maxwell_nc.imageloader.thread.ThreadPoolController;
import io.github.maxwell_nc.imageloader.utils.LogUtils;

/**
 * 图片加载器
 */
public class ImageLoader {

    private final Builder mBuilder;

    public ImageLoader(Builder builder) {
        this.mBuilder = builder;
    }

    /**
     * 创建任务
     */
    public static Builder createTask() {
        return new Builder();
    }

    /**
     * 创建任务
     */
    public static void log() {
        LogUtils.switchLog(true);
    }

    /**
     * 清空内存缓存
     */
    public static boolean clearMemCache() {
        boolean ret = MemoryCache.clearAllCache();
        LogUtils.log().addMsg("清空内存缓存" + (ret ? "成功" : "失败")).build().execute();
        return ret;
    }

    /**
     * 减少内存缓存
     */
    public static boolean clearMemCache(int level) {
        boolean ret = MemoryCache.trimCache(level);
        LogUtils.log().addMsg("减少内存缓存,Level:" + level + (ret ? ",成功" : ",失败")).build().execute();
        return ret;
    }

    /**
     * 清空本地缓存
     */
    public static boolean clearDiskCache(Context context) {
        boolean ret = DiskCache.clearAllCache(context);
        LogUtils.log().addMsg("清空本地缓存" + (ret ? "成功" : "失败")).build().execute();
        return ret;
    }

    private void start() {

        final ImageModel imageModel = new ImageModel(mBuilder.imageView.getContext());

        imageModel.setViewWidth(mBuilder.imageView.getMeasuredWidth());
        imageModel.setViewHeight(mBuilder.imageView.getMeasuredHeight());

        if (imageModel.getViewWidth() <= 0 || imageModel.getViewHeight() <= 0) {

            mBuilder.imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    imageModel.setViewWidth(mBuilder.imageView.getMeasuredWidth());
                    imageModel.setViewHeight(mBuilder.imageView.getMeasuredHeight());

                    startDownloadTask(imageModel);

                    mBuilder.imageView.removeOnLayoutChangeListener(this);
                }

            });

        } else {
            startDownloadTask(imageModel);
        }

    }

    /**
     * 启动下载图片任务并显示结果
     */
    private <D, W> void startDownloadTask(ImageModel model) {

        //清空显示
        mBuilder.imageView.setImageBitmap(null);

        //显示加载中图片
        if (mBuilder.loadingResId > 0) {
            mBuilder.imageView.setImageResource(mBuilder.loadingResId);
        }

        model.setUri(mBuilder.uri);

        ICacheRequest requestHandler = mBuilder.request == null ?
                PathParser.getRequest(mBuilder.type) ://使用默认的加载框架
                mBuilder.request;//使用自定义的加载框架

        if (requestHandler != null) {
            requestHandler.setModel(model);
        } else {
            return;
        }

        LoadTask loadTask = new LoadTask(requestHandler);
        loadTask.setListener(new LoadTask.OnResultListener() {
            @Override
            public void onGetBitmap(Bitmap bitmap) {
                if (bitmap != null) {
                    mBuilder.imageView.setImageBitmap(bitmap);
                    if (mBuilder.listener!=null){
                        mBuilder.listener.onSuccess(bitmap);
                    }
                } else {// 图片获取失败
                    if (mBuilder.failedLoadResId > 0) {
                        mBuilder.imageView.setImageResource(mBuilder.failedLoadResId);
                    }
                    if (mBuilder.listener!=null){
                        mBuilder.listener.onFailed();
                    }
                }
            }
        });

        loadTask.executeOnExecutor(ThreadPoolController.getThreadPool());
    }

    public static class Builder {

        ImageView imageView;
        Uri uri;
        ICacheRequest request;
        PathParser.Type type;

        int loadingResId = -1;
        int failedLoadResId = -1;
        OnResultListener listener;

        public Builder web(String url) {
            type = PathParser.Type.WEB;

            // HTTP类型，补全http://
            if (url.startsWith("www.")) {
                url = "http://" + url;
            }

            // HTTP类型，补全http://
            if (!url.startsWith("https://") && !url.startsWith("http://")) {
                url = "http://" + url;
            }

            uri = Uri.parse(url);
            return this;
        }

        public Builder asset(String filename) {
            type = PathParser.Type.ASSERT;
            uri = Uri.parse(PathParser.PREFIX_ASSERT + filename);
            return this;
        }

        public Builder raw(Context context, int resId) {
            type = PathParser.Type.RAW;
            Resources resources = context.getResources();
            uri = Uri.parse(PathParser.PREFIX_RESOURCE
                    + resources.getResourcePackageName(resId) + "/"
                    + resources.getResourceTypeName(resId) + "/"
                    + resources.getResourceEntryName(resId));
            return this;
        }

        public Builder media(Uri uri) {
            type = PathParser.Type.MEDIA;
            this.uri = uri;
            return this;
        }

        public Builder media(String uriStr) {
            type = PathParser.Type.MEDIA;
            this.uri = Uri.parse(uriStr);
            return this;
        }

        public Builder res(int resId) {
            type = PathParser.Type.RESOURCE;
            uri = Uri.parse(String.valueOf(resId));//no cache，不关心Uri是否重复
            return this;
        }

        public Builder local(String filepath) {
            type = PathParser.Type.LOCAL;
            if (!filepath.startsWith("file://")) {//补全路径
                filepath = "file://" + filepath;
            }
            uri = Uri.parse(filepath);
            return this;
        }

        public Builder into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public <D, W> Builder using(ICacheRequest<D, W> request) {
            this.request = request;
            return this;
        }

        public Builder load(String customPath) {
            uri = Uri.parse(customPath);
            return this;
        }

        public Builder loadingRes(int resId) {
            loadingResId = resId;
            return this;
        }

        public Builder failedRes(int resId) {
            failedLoadResId = resId;
            return this;
        }

        public Builder callback(OnResultListener listener) {
            this.listener = listener;
            return this;
        }

        public void start() {

            //自动回收内存
            imageView.getContext().registerComponentCallbacks(new ComponentCallbacks2() {
                @Override
                public void onTrimMemory(int level) {
                    LogUtils.log().addMsg("内存较低，自动回收内存缓存,Level:" + level).build().execute();
                    clearMemCache(level);
                }

                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                }

                @Override
                public void onLowMemory() {
                    LogUtils.log().addMsg("内存不足，自动清空内存缓存").build().execute();
                    clearMemCache();
                }
            });

            new ImageLoader(this).start();
        }

        public boolean cleanMemCache() {
            boolean ret = MemoryCache.clearCache(uri.toString());
            LogUtils.log().addMsg("清空" + uri.toString() + "内存缓存" + (ret ? "成功" : "失败")).build().execute();
            return ret;
        }

        public boolean cleanDiskCache(Context context) {
            boolean ret = DiskCache.clearCache(context, uri.toString());
            LogUtils.log().addMsg("清空" + uri.toString() + "本地缓存" + (ret ? "成功" : "失败")).build().execute();
            return ret;
        }

        public boolean cleanCache(Context context) {
            return cleanMemCache() && cleanDiskCache(context);
        }

    }

    public interface OnResultListener{

        /**
         * 失败的回调
         */
        void onFailed();

        /**
         * 成功的回调
         */
        void onSuccess(Bitmap bitmap);
    }

}
