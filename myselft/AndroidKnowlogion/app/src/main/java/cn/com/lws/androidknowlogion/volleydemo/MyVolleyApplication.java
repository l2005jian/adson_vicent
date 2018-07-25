package cn.com.lws.androidknowlogion.volleydemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyVolleyApplication extends Application {
    public static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        queue= Volley.newRequestQueue(getApplicationContext());
        Fresco.initialize(getApplicationContext());
    }

    public static RequestQueue getHttpQueue(){
        return queue;
    }
}
