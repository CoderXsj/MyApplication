package com.cydai.cncx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import com.cydai.cncx.R;
import com.cydai.cncx.util.DensityUtils;

/**
 * Created by 薛世君
 * Date : 2016/10/8
 * Email : 497881309@qq.com
 */
public class IndicatorView extends View implements ViewPager.OnPageChangeListener{

    private int mIndicatorSize;         //圆点的大小
    private int mIndicatorColor;        //圆点的颜色
    private int mIndicatorCount;        //圆点的多少

    private Paint mCirclePaint;         //静止的圆的画笔(空心)
    private Paint mCurrentPaint;        //当前圆的画笔(实心)

    private int lastPosition = 0;
    private float mOffset;

    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(attrs);
    }

    private void initView(AttributeSet attrs){
        if(attrs != null) {
            TypedArray array = getResources().obtainAttributes(attrs, R.styleable.IndicatorView);
            for (int i = 0; i < array.getIndexCount(); i++) {
                int index = array.getIndex(i);
                switch (index) {
                    case R.styleable.IndicatorView_indicator_size:
                        mIndicatorSize = array.getDimensionPixelSize(index, DensityUtils.dp2px(getContext(),20));
                        break;
                    case R.styleable.IndicatorView_indicator_color:
                        mIndicatorSize = array.getColor(index, Color.WHITE);
                        break;
                }
            }
            array.recycle();
        }else{
            //设置默认值
            mIndicatorSize = DensityUtils.dp2px(getContext(),20);
            mIndicatorColor = Color.WHITE;
        }

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mIndicatorColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);         //Style的三种模式:STROKE描边
        mCirclePaint.setStrokeWidth(1.0f);

        mCurrentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurrentPaint.setColor(mIndicatorColor);
        mCurrentPaint.setStyle(Paint.Style.FILL_AND_STROKE);         //Style的三种模式:STROKE描边
        mCurrentPaint.setStrokeWidth(1.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = mIndicatorSize;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){
            width = MeasureSpec.getSize(widthMeasureSpec);
        }else if(widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED){
            width = getPaddingLeft()
                    + mIndicatorCount * mIndicatorSize
                    + (mIndicatorCount == 0 ? 0 : mIndicatorSize * 3)
                    + getPaddingRight();
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();

        //画出实心小圆点
        for(int i = 0;i < mIndicatorCount;i++){
            canvas.drawCircle(paddingLeft,0,mIndicatorSize / 2,mCirclePaint);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(lastPosition == position){
            mOffset = positionOffset;
        }else{
            lastPosition = position;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}