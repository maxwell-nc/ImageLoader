package io.github.maxwell_nc.imageloader.engine;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import io.github.maxwell_nc.imageloader.conn.ICacheRequest;
import io.github.maxwell_nc.imageloader.entity.ImageModel;
import io.github.maxwell_nc.imageloader.utils.LogUtils;


/**
 * 加载任务
 */
public class LoadTask extends AsyncTask<Void, Void, Bitmap> {

    private ICacheRequest request;
    private OnResultListener listener;

    /**
     * 标记是否取消任务
     */
    private boolean isCancelled = false;

    public LoadTask(ICacheRequest request) {
        this.request = request;
    }

    public void setListener(OnResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return new Engine().getImage(request);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (!isCancelled && listener != null) {
            listener.onGetBitmap(bitmap);
        }
    }

    /**
     * 取消任务
     * TODO:绑定context周期
     */
    public void cancel() {
        isCancelled = true;
    }


    public interface OnResultListener {

        /**
         * 成功读取图片后的操作
         *
         * @param bitmap 图片
         */
        public void onGetBitmap(Bitmap bitmap);

    }

}
