package cn.com.lws.androidknowlogion.sdstorage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/15 . 14:17
 * 描述
 */

public class SDIOFile extends Activity {
    private final String TAG="SDIOFile";
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                Log.d(TAG,"sd card mount ....");
                File sdCarddir=Environment.getExternalStorageDirectory();
                Log.d(TAG,"sd card mount ...."+sdCarddir);
                File saveFile=new File(sdCarddir,"test.txt");
                Log.d(TAG,"sd card mount ...."+saveFile.toString());
                try {
                    FileOutputStream fileOutputStream=new FileOutputStream(saveFile);
                    fileOutputStream.write("abcedfdsfa".getBytes());
                    fileOutputStream.write(32);
                    fileOutputStream.close();
                    Log.d(TAG,"write sucess ...."+saveFile.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    


  
}
