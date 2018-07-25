package cn.com.lws.androidknowlogion.disklrucache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import cn.com.lws.androidknowlogion.R;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/19 . 15:26
 * 描述
 */

public class PhotoWallAdapter extends ArrayAdapter<String> {

    //记录正在下载和等待下载的任务
    private Set<BitmapWorkerTask> taskCollection;

    //缓存下载的图片
    private LruCache<String, Bitmap> mMemoryCache;

    //缓存到磁盘
    DiskLruCache mDiskLruCahe;

    //GridView
    private GridView mPhotoWall;

    //记录每个子项的高度
    private int mItemHeight = 0;


    public PhotoWallAdapter(@NonNull Context context, @LayoutRes int textViewResourceId, @NonNull String[] objects, GridView photoWall) {
        super(context, textViewResourceId, objects);
        mPhotoWall = photoWall;
        taskCollection = new HashSet<BitmapWorkerTask>();
        //获取应用程序的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSzie = maxMemory / 8;
        //设置图片缓存的大小为程序可用内存的1/8
        mMemoryCache = new LruCache<String, Bitmap>(cacheSzie) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        //获取图片的缓存路径
        File cacheDir = getDiskCacheDir(context, "thumb");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        try {//创建disklrucache实例
            mDiskLruCahe = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String url = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, null);
        } else {
            view = convertView;
        }
        final ImageView imageView = view.findViewById(R.id.photo);
        if (imageView.getLayoutParams().height != mItemHeight) {
            imageView.getLayoutParams().height = mItemHeight;
        }
        imageView.setTag(url);
        imageView.setImageResource(R.drawable.selectdots);
        loadBitmaps(imageView, url);
        return view;


    }

    private void loadBitmaps(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if (bitmap == null) {
            BitmapWorkerTask task = new BitmapWorkerTask();
            taskCollection.add(task);
            task.execute(url);
        } else {
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWorkerTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }

    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        notifyDataSetChanged();

    }


    public void addBitmapToMemory(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }


    private int getAppVersion(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private File getDiskCacheDir(Context context, String thumb) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + thumb);
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = byteToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
            e.printStackTrace();
        }
        return cacheKey;

    }
    public  void fluchCache(){
       
            try {
                if (mDiskLruCahe != null){
                mDiskLruCahe.flush();
                }        
            } catch (IOException e) {
                e.printStackTrace();
            }
      
        
    }
    
    

    private String byteToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(0xFF & digest[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //异步下载任务
    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            DiskLruCache.Snapshot snapShot = null;
            final String key = hashKeyForDisk(imageUrl);
            try {
                snapShot = mDiskLruCahe.get(key);
                if (snapShot == null) {
                    DiskLruCache.Editor editor = mDiskLruCahe.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downLoadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    snapShot = mDiskLruCahe.get(key);
                }
                if (snapShot != null) {
                    fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }
                Bitmap bitmap = null;
                if (fileDescriptor != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                }
                if (bitmap != null) {
                    addBitmapToMemory(params[0], bitmap);
                }
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileDescriptor == null && fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = mPhotoWall.findViewWithTag(imageUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }

        private boolean downLoadUrlToStream(String imageUrl, OutputStream outputStream) {
            HttpURLConnection urlConnection = null;
            BufferedOutputStream out = null;
            BufferedInputStream in = null;
            try {
                final URL url = new URL(imageUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
                out = new BufferedOutputStream(outputStream, 8 * 1024);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null){
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return false;
        }

    }


}
