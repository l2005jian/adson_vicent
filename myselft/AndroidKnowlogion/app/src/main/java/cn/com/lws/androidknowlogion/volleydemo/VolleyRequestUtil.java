package cn.com.lws.androidknowlogion.volleydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;


import org.json.JSONObject;

import cn.com.lws.androidknowlogion.R;

public class VolleyRequestUtil extends Activity{


    private RequestQueue requestQueue;
    private TextView volley_result;
    private ImageView imageVolley;
    private SimpleDraweeView image_net;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_layout);
        init();
       // get();
//        post();
//        imageLoader();
//        json();
        netWorkImageView();
    }

    private void init() {
        volley_result=findViewById(R.id.volley_result);
        imageVolley=findViewById(R.id.volley_image);
        image_net=findViewById(R.id.volley_net_image);
    }

    public void get(){
        requestQueue=Volley.newRequestQueue(VolleyRequestUtil.this);
        String url="http://api.m.mtime.cn/PageSubArea/TrailerList.api";

        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JSONObject jsonObject = new JSONObject();
                volley_result.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volley_result.setText("加载失败"+error);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void post(){
        requestQueue=Volley.newRequestQueue(VolleyRequestUtil.this);
        String url="http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                volley_result.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volley_result.setText("加载失败"+error);
            }
        });

        requestQueue.add(stringRequest);
    }

    private void json(){
        requestQueue=Volley.newRequestQueue(VolleyRequestUtil.this);
        String url="http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volley_result.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volley_result.setText("加载失败"+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void imageLoader(){
        requestQueue=Volley.newRequestQueue(VolleyRequestUtil.this);
        ImageLoader imageLoader=new ImageLoader(requestQueue,new BitmapCache());
        String url = "http://img5.mtime.cn/mg/2016/12/26/164311.99230575.jpg";
        ImageLoader.ImageListener imageListener= ImageLoader.getImageListener(imageVolley,R.drawable.mezi,R.drawable.selectdots);
        imageLoader.get(url,imageListener);
    }
    private void netWorkImageView(){

        requestQueue=Volley.newRequestQueue(VolleyRequestUtil.this);
        ImageLoader imageLoader=new ImageLoader(requestQueue,new BitmapCache());
        image_net.setImageResource(R.drawable.mezi);
        String url = "http://img5.mtime.cn/mg/2016/12/26/164311.99230575.jpg";
        image_net.setImageURI(url,imageLoader);
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestQueue.cancelAll(VolleyRequestUtil.this);
    }
}
