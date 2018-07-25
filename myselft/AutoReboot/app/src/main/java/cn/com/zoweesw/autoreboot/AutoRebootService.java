package cn.com.zoweesw.autoreboot;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

public class AutoRebootService extends Service {
    /**
     *<data
     android:host=".autorebootactaivity"
     android:scheme="cn.com.zoweesw.autoreboot" />
     *
     *
     */
    private Context mContext = BootBroadCast.mContext;
    private static final String TAG = "Reboot";
    private Thread ledThread;
    private boolean blLedControl;
    private final int delay_time=10*1000;
    @Override
    public void onCreate() {
        Log.d(TAG, "===onCreate()====AutoRebootService");
        startAgingTest();
        reboot();
    }

    private void reboot() {
        //Log.d(TAG, "===reboot()====");
        try{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "===reboot handler====AutoRebootService");
                    PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
                    powerManager.reboot(null);
                }
            }, delay_time);

        }catch (Exception e){
            Log.d(TAG, "===Handler()====AutoRebootService"+e.toString());
        }
    }
    private void startAgingTest() {
        Log.i(TAG, "startLedTest:ledThread ");
        blLedControl = true;
        ledThread = new Thread() {
            @Override
            public void run() {
                while (blLedControl) {
                    try {
                        Thread.sleep(500);
                        BoardTestUtils.setLedStatus(true, false, false);//red led on
                        Thread.sleep(500);
                        BoardTestUtils.setLedStatus(false, true, false);//green led on
                        Thread.sleep(500);
                        BoardTestUtils.setLedStatus(false, true, true);//青色
                        Thread.sleep(500);
                        BoardTestUtils.setLedStatus(true, true, false);// 黄色
                        Thread.sleep(500);
                        BoardTestUtils.setLedStatus(false, false, false);//all led off
                        Thread.sleep(500);
                    } catch (Exception e) {
                        Log.e(TAG, "Thread ledThread:" + e.toString()+"AutoRebootService");
                    }
                }
            }
        };
        ledThread.start();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: AutoRebootService");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "===onDestroy()====AutoRebootService");
        //BoardTestUtils.setLedStatus(false, false, false);//all led off
        super.onDestroy();
        if (ledThread != null) {
            blLedControl = false;
        }
    }
}
