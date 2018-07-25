package cn.com.lws.androidknowlogion.auildemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

import cn.com.lws.androidknowlogion.R;
import cn.com.lws.androidknowlogion.disklrucache.Image;

public class AuilActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auilloader);
        imageView=findViewById(R.id.loader_image);
        //ImageLoader.getInstance().init();
        //ImageLoader.getInstance().displayImage("http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg", imageView);


    }
}
