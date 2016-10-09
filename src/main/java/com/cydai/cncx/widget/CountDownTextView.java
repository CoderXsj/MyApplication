package com.cydai.cncx.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */
public class CountDownTextView extends TextView{
    private int mCount;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        }
;    };

    private Runnable mDelayRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };

    public CountDownTextView(Context context) {
        this(context,null);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}