package com.cydai.cncx.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 薛世君
 * Date : 2016/10/10
 * Email : 497881309@qq.com
 */

public class MyToast {
    private static Context mContext;

    public static  void init(Context context){
        mContext = context;
    }

    public static void S(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public static void L(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }

}
