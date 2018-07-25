package cn.com.lws.androidknowlogion;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.lws.androidknowlogion.disklrucache.Image;

public class MainActivity extends AppCompatActivity {
    
//    private DownloadUtils downloadUtils;

//    private CircleProgressBar recommend_progressBar;//circle百分比
//    private TextView recommend_rate_circle;//百分比利率
//    private float aFloat = 75.8f;
    //private ImageView mImageView;
    @BindView(R.id.lrucache)
    private ImageView mImageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        http://app.fanyi.baidu.com/transapp/appdownloadpage?appchannel=webbutton
        
//        downloadUtils=new DownloadUtils(MainActivity.this);
        
//        downloadUtils.downloadAPK("http://fast.yingyonghui.com/4bdd9a307a097e70bef1f55a701876b7/5b223b96/apk/5878772/9c8515e639b74a965db085ca0247c62c","baidufanyi.apk");
    //mImageView=findViewById(R.id.lrucache);
    for (int i=0;i< Image.imageThumbUrls.length;i++) {
        Uri uri=Uri.parse(Image.imageThumbUrls[i]);
        Picasso.get()
                .load(uri)
                .placeholder(R.drawable.selectdots)
                .onlyScaleDown()
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                .error(R.drawable.selectdots)
                .resize(400, 200)
                .into(mImageView);
    }
        
//        recommend_progressBar = (CircleProgressBar) findViewById(recommend_progressBar);
//        recommend_rate_circle = (TextView) findViewById(recommend_rate_circle);
//
//        recommend_rate_circle.setText(String.valueOf(aFloat));//百分比利率
//        recommend_progressBar.setTargetPercent(aFloat);//circle百分比
    }
}
