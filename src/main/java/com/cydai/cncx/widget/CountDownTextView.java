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

    Handler mHandler = new Handler();

//    private

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}