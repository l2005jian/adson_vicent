package cn.com.lws.androidknowlogion.custview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.com.lws.androidknowlogion.R;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/13 . 14:39
 * 描述
 */

public class CircleProgressBar extends View {
    
    private Paint roundColorPaint;//外圆画笔
    private Paint mCirclePaint;//中心圆画笔
    private Paint mTextPaint;//文字画笔
    private Paint mArcPaint;//外圆环的画笔
    private int mCircleX;//设置圆心坐标
    private int mCircleY;//
    private float mCurrentAngle;//当前角度
    private RectF mArcRecF;//画中心圆的外接矩形，用来画圆环用
    private float mStartSweepValue;//圆环开始角度
    private float mTargetPercent;//设置目标的百分比
    private float mCurrentPercent;//当前百分比
    
    private int mRadius;//圆的半径
    private int mCircleBackground;//中心圆的背景颜色
    private int mRingColor;//外圆的颜色；第二次
    private int roundColor;//外圆的颜色；第一次
    private int mTextSize;//字体大小
    private int mTextColor;//字体的yanse
    
    
    public CircleProgressBar(Context context) {
        super(context);
        
        //初始化
        init(context);
    }

    private void init(Context context) {
        //圆形开始的角度
        mStartSweepValue=-90;
        //当前角度
        mCurrentAngle=0;
        //当前百分比
        mCurrentPercent=0;
        //设置中心圆的画笔
        mCirclePaint=new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleBackground);
        mCirclePaint.setStyle(Paint.Style.FILL);
        
        //设置文字的画笔
        mTextPaint=new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth((float) 0.0025*mRadius);
        mTextPaint.setTextSize(mRadius/2);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        
        //设置外圆的画笔
        mArcPaint=new Paint();
        roundColorPaint=new Paint();
        roundColorPaint.setColor(roundColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(mRingColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth((float) 0.075*mRadius);
        //获得文字的字号
        mTextSize=(int)mTextPaint.getTextSize();
        
        
        
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       //自定义属性
        //使用TypeArray
        //使用Values/attr
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.PercentRing);
        //中间圆的背景颜色；默认白色
        mCircleBackground=typedArray.getColor(R.styleable.PercentRing_circleBackground,0xff000000);
        //外圆的颜色；默认灰色
        roundColor=typedArray.getColor(R.styleable.PercentRing_circleColor,0xfff5f5f5);
        //外圆的动态颜色；默认红色
        mRingColor=typedArray.getColor(R.styleable.PercentRing_ringColor,0xfff5f5f5);
        //中间圆的半径；默认80
        mRadius=typedArray.getInt(R.styleable.PercentRing_radius,80);
        //字体颜色默认灰色
        mTextColor=typedArray.getColor(R.styleable.PercentRing_textColor,0xff999999);
        typedArray.recycle();
        init(context);
    }
    
    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measure(widthMeasureSpec),measure(heightMeasureSpec));
        //设置圆心坐标
        mCircleX=getMeasuredWidth()/2;
        mCircleY=getMeasuredHeight()/2;
        //如果半径大于圆心坐标，须缩小半径的值，
        if (mRadius > mCircleX ){
            //设置半径大小为圆心的横坐标到原点的距离
            mRadius=mCircleX;
            mRadius=(int)(mCircleX-0.075*mRadius);
            //半径改变了，需重新设置字体高度
            mTextPaint.setStrokeWidth((float)0.025*mRadius);
            //重新设置字号
            mTextPaint.setTextSize(mRadius/2);
            //重新设置外圆的宽度
            mArcPaint.setStrokeWidth((float) 0.075*mRadius);
            //重新获取字体大小
            mTextSize=(int)mTextPaint.getTextSize();
        }
        //画中心圆的外接矩形，画圆环
        mArcRecF=new RectF(mCircleX-mRadius,mCircleY-mRadius,mCircleX+mRadius,mCircleY+mRadius);
        
    }
    //当wrap_content时候，view的大小根据半径的大小改变，最大不会超过屏幕
    private int measure(int measureSpec) {
        int result=0;
        //先获取测量模式，和测量大小
        //测量模式时match_parent或者精确值，则宽为测量的宽
        //测试模式是wrap_content，则宽为直径值与测量中的最小值，否则会当直径大于测量宽时，会绘制到屏幕外面
        int specMode=MeasureSpec.getMode(measureSpec);
        int specSize=MeasureSpec.getSize(measureSpec);
        if (specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result=(int)1.075*mRadius*2;
            if (specMode == MeasureSpec.AT_MOST){
                result=Math.min(result,specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画中心圆
        canvas.drawCircle(mCircleX,mCircleY,mRadius,mCirclePaint);
        //画圆环
        roundColorPaint.setStyle(Paint.Style.STROKE);
        //设置外圆的宽度
        roundColorPaint.setStrokeWidth((float) 0.075*mRadius);
        canvas.drawCircle(mCircleX,mCircleY,mRadius,roundColorPaint);
        //动态绘制红色圆环
        canvas.drawArc(mArcRecF,mStartSweepValue,mCurrentAngle,false,mArcPaint);
        //画文字 不能画有小数的百分比
       // canvas.drawText(String.valueOf(mCurrentPercent)+"%",mCircleX,mCircleY+mTextSize/4,mTextPaint);
        //判断当前百分比是否小于设置的目标的百分比
        if(mCurrentPercent < mTargetPercent){
            //当前百分比+1
            mCurrentPercent+=1;
            //当前角度+360
            mCurrentAngle+=3.6;
            //每10m重画一次
            postInvalidateDelayed(5);
        }
    }
    //设置目标的百分比
    public void setTargetPercent(float percent){
        this.mTargetPercent=percent;
    }
}
