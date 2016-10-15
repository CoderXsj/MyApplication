package com.cydai.cncx.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */
public class CountDownTextView extends TextView{
    private int mCount;
    private OnUpdateTimeListener onUpdateTimeListener;
    private boolean isStart;

    public interface OnUpdateTimeListener{
        void onStart();

        void onUpdateTime(TextView textView,long millions);

        void onFinish();
    }

    private Handler mHandler = new Handler();

    private Runnable mDelayRunnable = new Runnable() {
        @Override
        public void run() {
        if(mCount > 0) {
            if(onUpdateTimeListener != null)
                onUpdateTimeListener.onUpdateTime(CountDownTextView.this,mCount);

            mCount--;
            mHandler.postDelayed(mDelayRunnable, 1000);
        }else{
            isStart = false;
            if(onUpdateTimeListener != null)
                onUpdateTimeListener.onFinish();
        }
        }
    };

    public void setOnUpdateTimeListener(OnUpdateTimeListener onUpdateTimeListener) {
        this.onUpdateTimeListener = onUpdateTimeListener;
    }

    public CountDownTextView(Context context) {
        this(context,null);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void start(int millions){
        if(isStart)
            return;

        isStart = true;
        mCount = millions;

        if(onUpdateTimeListener != null){
            onUpdateTimeListener.onStart();
        }

        mHandler.post(mDelayRunnable);
    }
}