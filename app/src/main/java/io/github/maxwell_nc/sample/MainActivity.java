package io.github.maxwell_nc.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import io.github.maxwell_nc.imageloader.ImageLoader;
import io.github.maxwell_nc.imageloader.conn.impl.web.WebImageRequest;
import io.github.maxwell_nc.imageloader.entity.ImageModel;

public class MainActivity extends Activity {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.iv_test);

        ImageLoader.log();//打印日志
    }

    public void raw(View view) {

        //cache and compress
        ImageLoader.createTask().raw(this, R.raw.test).into(imageView).start();

        //use res to load,no cache and compress
        ImageLoader.createTask().res(R.raw.test).into(imageView).start();
    }

    public void assertType(View view) {
        ImageLoader.createTask().asset("test_stream.jpg").into(imageView).start();
    }

    public void resource(View view) {

        //drawable
        ImageLoader.createTask().res(R.drawable.ic_launcher).into(imageView).start();

        //mipmap
        ImageLoader.createTask().res(R.mipmap.ic_launcher).into(imageView).start();
    }

    public void media(View view) {

        //uri string
        ImageLoader.createTask().media("content://media/external/images/media/58212").into(imageView).start();

        //uri
        ImageLoader.createTask().media(Uri.parse("content://media/external/images/media/58212")).into(imageView).start();
    }

    public void web(View view) {

        //no prefix
        ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").into(imageView).start();

        //www.
        ImageLoader.createTask().web("www.easyicon.net/api/resizeApi.php?id=1200398&size=128").into(imageView).start();

        //http://
        ImageLoader.createTask().web("http://cdn-img.easyicon.net/image/mix.png").into(imageView).start();

        //https://
        ImageLoader.createTask().web("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png").into(imageView).start();
    }

    public void local(View view) {
        ImageLoader.createTask().local(Environment.getExternalStorageDirectory().getPath() + "/test.jpg").into(imageView).start();
    }

    public void using(View view) {
        //a simple sample for custom network load framework
        ImageLoader.createTask().load("bd_logo1_31bdc765.png")
                .using(new WebImageRequest() {
                    @Override
                    public void setModel(ImageModel model) {
                        super.setModel(model);
                        model.setPath("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/" + model.getPath());
                    }

                    @Override
                    public InputStream requestWeb() {
                        return super.requestWeb();//you can use your custom http client in method,such as okHttp
                    }
                })
                .into(imageView).start();
    }

    public void cache(View view) {
        ImageLoader.clearMemCache(80);//trim to 80%
        ImageLoader.clearMemCache();//clear all
        ImageLoader.clearDiskCache(this);//clear all
        ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").cleanCache(this);//clear custom memory and disk cache
        ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").cleanDiskCache(this);//clear custom disk cache
        ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").cleanMemCache();//clear custom memory cache
    }

    public void res(View view) {
        ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png")
                .loadingRes(R.mipmap.ic_launcher)
                .failedRes(R.drawable.ic_launcher)
                .into(imageView).start();
    }

    public void callback(View view) {
        ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png")
                .callback(new ImageLoader.OnResultListener() {
                    @Override
                    public void onFailed() {
                        //your job
                        Toast.makeText(MainActivity.this,"onFailed",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        //your job
                        Toast.makeText(MainActivity.this,"onSuccess",Toast.LENGTH_SHORT).show();
                    }
                })
                .into(imageView).start();
    }
}
