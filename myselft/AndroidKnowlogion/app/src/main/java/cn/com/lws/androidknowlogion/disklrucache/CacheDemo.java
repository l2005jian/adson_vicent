package cn.com.lws.androidknowlogion.disklrucache;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.com.lws.androidknowlogion.R;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/19 . 13:39
 * 描述
 * size():返回当前缓存路径下的数据的大小，byte
 * flush():同步内存的操作记录到日志文件；在onPause调用一次
 * close():在onDestroy中调用一次
 * delete():清除全部缓存数据
 * 
 * 
 */

public class CacheDemo extends Activity {

    public static DiskLruCache diskLruCache;
    private  Context context;
    private DiskLruCache.Editor editor;
    private   File cacheDir;
    private ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView=findViewById(R.id.lrucache);
         cacheDir=getCachePath(context,"bitmap");
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        //写入缓冲数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {//创建cache目录
                    diskLruCache= DiskLruCache.open(cacheDir,getAppVersion(context),1,10*10244*1024);
                    String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
                    String key=hashKeyForDisk(imageUrl);
                    editor= diskLruCache.edit(key);
                    if (editor != null){
                        OutputStream outputStream=  editor.newOutputStream(0);
                        if (downLoadUrlToStream(imageUrl,outputStream)){
                            editor.commit();
                        }else {
                            editor.abort();
                        }
                    }
                    diskLruCache.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
       //读取缓存数据
        try {
            String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
            String key=hashKeyForDisk(imageUrl);
            DiskLruCache.Snapshot snapShot=diskLruCache.get(key);
            if (snapShot != null){
                InputStream is=snapShot.getInputStream(0);
                Bitmap bitmap= BitmapFactory.decodeStream(is);
                mImageView.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //移除缓存数据
        String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        String key=hashKeyForDisk(imageUrl);
        try {
            diskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //获取cache路径
    public File getCachePath(Context context,String uniqueName){
        String cachePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()){
            cachePath=context.getExternalCacheDir().getPath();
        }else {
            cachePath=context.getCacheDir().getPath();
        }
        return new File(cachePath+File.separator+uniqueName);
    }
    //获取当前版本
    public int getAppVersion(Context context){
        try {
            PackageInfo info=context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    //写入缓存
    private boolean downLoadUrlToStream(String urlString, OutputStream outputStream){
        HttpURLConnection urlConnection=null;
        BufferedOutputStream out=null;
        BufferedInputStream in=null;

        try {
            final URL url=new URL(urlString);
            urlConnection= (HttpURLConnection) url.openConnection();
            in=new BufferedInputStream(urlConnection.getInputStream(),8*1024);
            out=new BufferedOutputStream(outputStream,8*1024);
            int b;
            while ((b=in.read()) != -1){
                out.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (out != null){
                try {
                    out.close();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    //转换图片链接为md5的key
    public String hashKeyForDisk(String key){
        String cacheKey;
        try {
            final MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            cacheKey=byteToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey=String.valueOf(key.hashCode());
            e.printStackTrace();
        }
        return cacheKey;
    }

    private String byteToHexString(byte[] digest) {
        StringBuilder sb=new StringBuilder();
        for (int i=0; i < digest.length;i++){
            String hex=Integer.toHexString(0xFF & digest[i]);
            if (hex.length() ==1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    

}
