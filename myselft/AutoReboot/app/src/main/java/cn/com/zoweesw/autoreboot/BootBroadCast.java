package cn.com.zoweesw.autoreboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadCast extends BroadcastReceiver {
    public static Context mContext;
    private static final String TAG="Reboot";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"===onReceive====BootBroadCast");

        mContext = context;
        Intent mIntent = new Intent(context, AutoRebootService.class);
        context.startService(mIntent);
    }
}
