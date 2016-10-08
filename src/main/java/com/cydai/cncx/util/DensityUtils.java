package com.cydai.cncx.util;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 薛世君
 * Date : 2016/10/8
 * Email : 497881309@qq.com
 */

public class DensityUtils {

    public static int dp2px(@NonNull  Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dp*density);
    }
    public static int px2dp(@NonNull Context context,int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(px/density);
    }
}
