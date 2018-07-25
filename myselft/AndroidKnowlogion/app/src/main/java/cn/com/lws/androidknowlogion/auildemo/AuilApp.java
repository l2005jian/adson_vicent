package cn.com.lws.androidknowlogion.auildemo;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

import cn.com.lws.androidknowlogion.R;

public class AuilApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);

        //第一种创建默认的加载
        //创建ImageLoaderConfiguration
        ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
        //初始化ImageLoader
        ImageLoader.getInstance().init(configuration);
        //第二种 自定义加载
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.selectdots)//设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.selectdots)//设置图片uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.selectdots)//图片加载或者解码过程中发生错误显示的图片
//                .resetViewBeforeLoading(true)//图片在加载前是否重置／复位
//                .delayBeforeLoading(200)//下载前的延迟时间
                .cacheInMemory(true)//图片是否缓存在内存中
                .cacheOnDisk(true)//图片是否保存在SD卡中
                .considerExifParams(false)//default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //图片的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565) //图片解码类型
                .displayer(new SimpleBitmapDisplayer()) //default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .displayer(new FadeInBitmapDisplayer(200))//图片加载好后渐入的动画时间
//                .displayer(new RoundedBitmapDisplayer(20))//设置成圆角图片
                .handler(new Handler())
                .build();

            //设置图片缓存路径
        File cacheDir= StorageUtils.getCacheDirectory(this);
        //自定义ImageLoaderConfiguration配置
        ImageLoaderConfiguration configuration1=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,800) //内存缓存文件的最大宽高
//                .diskCacheExtraOptions(480,800,null) //本地缓存的宽高
                .threadPoolSize(4) //线程池加载的数量
//                .threadPriority(Thread.NORM_PRIORITY-2) //当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2*1024*1024))
                .memoryCacheSize(2*1024*1024)
                .memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(100*1024*1024)
                .diskCacheFileCount(100) //可缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(this,5*1000,30*1000))
                .defaultDisplayImageOptions(options)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(configuration);
    }


}






