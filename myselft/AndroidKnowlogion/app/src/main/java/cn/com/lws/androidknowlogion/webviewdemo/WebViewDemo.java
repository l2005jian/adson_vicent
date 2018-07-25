package cn.com.lws.androidknowlogion.webviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/13 . 10:09
 * 描述
 */

public class WebViewDemo extends Activity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
 /*       Uri uri=Uri.parse(url);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);*/
      // webView=findViewById(R.id.webview);
        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("local source");
        webView.requestFocus();
    
 
 
 
    }

  
    
}
