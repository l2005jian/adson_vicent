package cn.com.lws.androidknowlogion.disklrucache;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import cn.com.lws.androidknowlogion.R;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/19 . 16:41
 * 描述
 */

public class PhotoWallActivity extends Activity {
    private GridView mPhotoWall;
    private  PhotoWallAdapter mAdapter;
    private  int mImageThumbSize;
    private int mImageThumbSpacing;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photolrucache);
        mImageThumbSize=getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing=getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        
        mPhotoWall=findViewById(R.id.photo_wall);
        mAdapter=new PhotoWallAdapter(this,0, Image.imageThumbUrls,mPhotoWall);
        mPhotoWall.setAdapter(mAdapter);
        mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final  int numColums= (int) Math.floor(mPhotoWall.getWidth()/(mImageThumbSpacing+mImageThumbSize));
                if (numColums > 0){
                    int columnWidth=(mPhotoWall.getWidth()/numColums)-mImageThumbSpacing;
                    mAdapter.setItemHeight(columnWidth);
                    mPhotoWall.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                
            }
        });
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.fluchCache();
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cancelAllTasks();;
        
    }
}
