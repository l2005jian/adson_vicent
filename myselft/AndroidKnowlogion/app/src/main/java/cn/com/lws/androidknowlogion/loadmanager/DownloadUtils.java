package cn.com.lws.androidknowlogion.loadmanager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;



/**
 * 创建者 Ferry
 * 创建时间  2018/6/14 . 17:25
 * 描述
 */

public class DownloadUtils {
    
    private DownloadManager downloadManager;
    private Context mContext;
    
    private long downloadId;
    public DownloadUtils(Context context){
        this.mContext=context;
    }
    public void downloadAPK (String url,String name){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverRoaming(false);
        
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("new version apk");
        request.setDescription("apk Dowmloading");
        request.setVisibleInDownloadsUi(true);
        
        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath(),name);
        
        downloadManager= (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);
        
        mContext.registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        );
        
    }
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };
    private void checkStatus() {
        DownloadManager.Query query=new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor c=downloadManager.query(query);
        if (c.moveToFirst()){
            int status=c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status){
                case DownloadManager.STATUS_PAUSED:
                    break;
                case DownloadManager.STATUS_PENDING:
                    break;
                case DownloadManager.STATUS_RUNNING:
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    break;
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext,"download fail ",Toast.LENGTH_LONG).show();
                    break;
            }
        }
        c.close();
    }
    
    private void installAPK(){
        Uri downloadFileUri=downloadManager.getUriForDownloadedFile(downloadId);
        if (downloadFileUri != null){
            Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(downloadFileUri,"application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            mContext.unregisterReceiver(receiver);
        }
    }
    
    
    
    
    
    
    
    
}
