package cn.com.lws.androidknowlogion.recycledemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.com.lws.androidknowlogion.R;

public class MeiziDetialActivity extends AppCompatActivity {

    ActionBar actionBar;
    @BindView(R.id.meizi_item_detial_image)  ImageView iv_image;
    @BindViews({R.id.meizi_item_detial_title,R.id.meizi_item_detial_name,
            R.id.meizi_item_detial_favorites,R.id.meizi_item_detial_comments})
    TextView tv_title,tv_name,tv_favorites,tv_comments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_detial);
        ButterKnife.bind(this);
        actionBar=getSupportActionBar();
        getMeiziIntent();

    }

    private void getMeiziIntent() {
//        intent.putExtra("image_url",meizi.getImageUrl());
//        intent.putExtra("title",meizi.getTitle());
//        intent.putExtra("name",meizi.getName());
//        intent.putExtra("favorites",meizi.getFavorites());
//        intent.putExtra("comments",meizi.getComments());
        String imageUrl=getIntent().getStringExtra("image_url");
        String title=getIntent().getStringExtra("title");
        String name=getIntent().getStringExtra("name");
        int favorites=getIntent().getIntExtra("favorites",0);
        int comments=getIntent().getIntExtra("comments",0);
        setMeizi(imageUrl,title,name,favorites,comments);
    }

    private void setMeizi(String imageUrl, String title, String name, int favorites, int comments) {


//        ImageView iv_image=findViewById(R.id.meizi_item_detial_image);
//        TextView tv_title=findViewById(R.id.meizi_item_detial_title);
//        TextView tv_name=findViewById(R.id.meizi_item_detial_name);
//        TextView tv_favorites=findViewById(R.id.meizi_item_detial_favorites);
//        TextView tv_comments=findViewById(R.id.meizi_item_detial_comments);
        Picasso.get()
                .load(imageUrl)
                .into(iv_image);
        tv_title.setText(title);
        tv_name.setText(name);
        tv_favorites.setText(String.valueOf(favorites));
        tv_comments.setText(String.valueOf(comments));
        actionBar.setTitle(name);

    }
}
