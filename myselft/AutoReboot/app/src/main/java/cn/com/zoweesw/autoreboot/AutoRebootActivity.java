package cn.com.zoweesw.autoreboot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AutoRebootActivity extends AppCompatActivity {
    private static final String TAG = "Reboot";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_reboot);
        Log.d(TAG, "===onCreate()====AutoRebootActivity");
    }
}
