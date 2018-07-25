package cn.com.lws.androidknowlogion.custview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.NumberFormat;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/13 . 11:37
 * 描述
 */

public class DonutView extends View {
    private static final int TOP=90;
    private float mStrokeWidth;
    private float mDeviceDensity;
    private int mPresent;
    private Paint mBackgroundCircle;
    private Paint mFiledArc;
    private TextPaint mTextPaint;
    private TextPaint mBigNumberPaint;
    private String mPercentString;
    private String mDescriptionString="";
    
    private static final int METER_BG_COLOR = Color.parseColor("#ffd1d1d1");
    private static final int[] METER_CONSUMED_COLOR = new int[]{
            Color.parseColor("#ff69bd2e"),
            Color.parseColor("#ffff6f26"),
            Color.parseColor("#ffff5147"),
            
    };
    
    private final int GREEN_PRECENT=60;
    private final int ORANGE_PERCENT=85;
    
    
    
    public DonutView(Context context) {
        super(context);
    }

    public DonutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       mDeviceDensity = getResources().getDisplayMetrics().density;
        mStrokeWidth = 6f * mDeviceDensity;
        
        mBackgroundCircle=new Paint();
        mBackgroundCircle.setAntiAlias(true);
        mBackgroundCircle.setStrokeCap(Paint.Cap.BUTT);
        mBackgroundCircle.setStyle(Paint.Style.STROKE);
        mBackgroundCircle.setStrokeWidth(mStrokeWidth);
        mBackgroundCircle.setColor(METER_BG_COLOR);
        
        mFiledArc=new Paint();
        mFiledArc.setAntiAlias(true);
        mFiledArc.setStrokeCap(Paint.Cap.BUTT);
        mFiledArc.setStyle(Paint.Style.STROKE);
        mFiledArc.setStrokeWidth(mStrokeWidth);
        
        mTextPaint=new TextPaint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(12f*mDeviceDensity);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        
        mBigNumberPaint = new TextPaint();
        mBigNumberPaint.setColor(Color.BLACK);
        mBigNumberPaint.setAntiAlias(true);
        mBigNumberPaint.setTextSize(24f*mDeviceDensity);
        mBigNumberPaint.setTextAlign(Paint.Align.CENTER);
        setPercentage(0);
    }

    private void setPercentage(int percent) {
        mPresent=percent;
        int i=0;
        if (mPresent < GREEN_PRECENT){
            i=0;
        }else if (mPresent >= GREEN_PRECENT && mPresent< ORANGE_PERCENT){
            i=1;
        }else if (mPresent>= ORANGE_PERCENT){
            i=2;
        }
        mFiledArc.setColor(METER_CONSUMED_COLOR[i]);
        mPercentString= formatPercentage(mPresent);
        invalidate();
    }

    private String formatPercentage(int percentage) {
        return formatPercentage(((double)percentage)/100.0);
    }

    private String formatPercentage(double percentage) {
        return NumberFormat.getPercentInstance().format(percentage);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDonut(canvas);
        drawInnerText(canvas);
    }

    private void drawInnerText(Canvas canvas) {
        final float centerX=getWidth()/2;
        final float centerY=getHeight()/2;
        final  float totalHeight =getTextHeight(mTextPaint)+getTextHeight(mBigNumberPaint);
        final float startY=centerY+totalHeight/2;
        if (mPercentString == null){
            return;
        }
        canvas.drawText(mPercentString,centerX,startY-getTextHeight(mTextPaint)-mBigNumberPaint.descent(),mBigNumberPaint);
        CharSequence mcharSequence=mDescriptionString;
        mDescriptionString= TextUtils.ellipsize(mcharSequence,mTextPaint,getWidth()-60,
                TextUtils.TruncateAt.END).toString();
        canvas.drawText(mDescriptionString,centerX,startY-mTextPaint.descent(),mTextPaint);
    }

    private float getTextHeight(TextPaint paint) {
        return paint.descent()-paint.ascent();
    }

    private void drawDonut(Canvas canvas) {
        canvas.drawArc(0+mStrokeWidth,0+mStrokeWidth,getWidth()-mStrokeWidth,
                getHeight()-mStrokeWidth,TOP,360,false,mBackgroundCircle);
        canvas.drawArc(0+mStrokeWidth,0+mStrokeWidth,getWidth()-mStrokeWidth,
                getHeight()-mStrokeWidth,TOP,(360*mPresent/100),false,mFiledArc);
    }
}
