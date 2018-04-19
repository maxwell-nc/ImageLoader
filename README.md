# Introduction
ImageLoader is a async load image framework with auto cache and compress.

## Feature
- Multi Image Type Support: Raw,Assert,Content Provider,Local File,WebImage,etc.
- Auto compress Image.
- Auto cache in memory and disk.
- Auto recycle memory.
- Async load with callback with thread pool control.
- Custom Load framework,use your custom library to load img,such as okHttp.
- Builder Design Mode.

## Dependency

Add it in your root build.gradle at the end of repositories:
```groovy
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Add the dependency：
```groovy
	dependencies {
	        compile 'com.github.maxwell-nc:ImageLoader:v1.0'
	}
```
## Show Log：

```java
	ImageLoader.log();
```

## Sample：

```java
	ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").into(imageView).start();
```

Other Type:

```java
   //no prefix
   ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").into(imageView).start();

   //www.
   ImageLoader.createTask().web("www.easyicon.net/api/resizeApi.php?id=1200398&size=128").into(imageView).start();

   //http://
   ImageLoader.createTask().web("http://cdn-img.easyicon.net/image/mix.png").into(imageView).start();

   //https://
   ImageLoader.createTask().web("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png").into(imageView).start();
```


## Cache control：

```java
   ImageLoader.clearMemCache(80);//trim to 80%
   ImageLoader.clearMemCache();//clear all
   ImageLoader.clearDiskCache(this);//clear all
   ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").cleanCache(this);//clear custom memory and disk cache
   ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").cleanDiskCache(this);//clear custom disk cache
   ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png").cleanMemCache();//clear custom memory cache
```


## Callback：

```java
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
```


## Set Loading and Failed Resource：

```java
   ImageLoader.createTask().web("cdn-img.easyicon.net/image/mix.png")
           .loadingRes(R.mipmap.ic_launcher)
           .failedRes(R.drawable.ic_launcher)
           .into(imageView).start();
```

## Custom Load framework：

a simple sample for custom network load framework:
```java
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
```

## TODO：

- bind context lifecycle
- cancel load
- not need imageview
- onCancel in callback
- add code comment
- may with sync return

## See more on app demo
