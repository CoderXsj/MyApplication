package com.cydai.cncx.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */

public class LoopPlayLayout extends RelativeLayout {
    private ViewPager mViewPager;
    private IndicatorView mIndicatorView;

    public LoopPlayLayout(Context context) {
        this(context,null);
    }

    public LoopPlayLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoopPlayLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
