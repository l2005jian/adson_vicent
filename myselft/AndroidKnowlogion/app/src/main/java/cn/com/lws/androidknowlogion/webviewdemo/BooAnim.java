package cn.com.lws.androidknowlogion.webviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.com.lws.androidknowlogion.R;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/13 . 17:12
 * 描述
 */

public class BooAnim extends Activity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    // 引导图片资源
    private static final int[] pics={R.mipmap.am_01,R.mipmap.am_02,R.mipmap.am_03};
    //底部圆点图片
    private ImageView[] dots;
    //记录当前选中的位置
    private int currentIndex;
    SharedPreferences preferences;
    List<View> views;
    ViewPager vp;
    ViewPagerAdapter vpAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bootani);
        //判断程序是否第一次在本手机上运行
        isFirstRun();
        //初始化Viewpage的各个界面
        initViewList();
        //初始化底部小点
        initDots();
        
        
    }

    private void isFirstRun() {
        preferences=getSharedPreferences("count",MODE_ENABLE_WRITE_AHEAD_LOGGING);
        int count=preferences.getInt("count",0);
        
        if (count != 0){
            Intent intent=new Intent();
            intent.setClass(getApplicationContext(), BooAnim.class);
            startActivity(intent);
            this.finish();
        }
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("count",++count);
        editor.commit();
    }
    private void setCurView(int position){
        if (position < 0 || position >= pics.length){
            return;
        }
        vp.setCurrentItem(position);
    }
    private void setCurDot(int position){
        if (position < 0 || position > pics.length -1 || currentIndex == position){
            return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex= position;
    }
    private void initViewList() {
        views=new ArrayList<View>();
        LinearLayout.LayoutParams mParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
                );
        //初始化引导图片
        for (int i=0;i < pics.length;i++){
            ImageView iv=new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        vp=findViewById(R.id.viewpager);
        vpAdapter=new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener((ViewPager.OnPageChangeListener) this);
    }

    private void initDots() {
        LinearLayout ll=findViewById(R.id.ll);
        dots=new ImageView[pics.length];
        //取得小点图片
        for (int i=0;i < pics.length;i++){
            dots[i]= (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);
            dots[i].setOnClickListener((View.OnClickListener) this);
            dots[i].setTag(i);//设置位置
        }
        currentIndex=0;
        dots[currentIndex].setEnabled(false); //选中状态
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == views.size() -1){
            startActivity(new Intent(this,BooAnim.class));
            finish();
        }
    }

    @Override
    public void onPageSelected(int position) {
        setCurDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
    int position=(Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private class ViewPagerAdapter extends PagerAdapter {
        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            if (views != null){
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(views.get(position));
        }

        @Override
        public void finishUpdate(View container) {
            super.finishUpdate(container);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
