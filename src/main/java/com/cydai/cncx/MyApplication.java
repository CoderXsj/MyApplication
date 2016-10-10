package com.cydai.cncx;

import android.app.Application;
import android.content.Context;

import com.cydai.cncx.common.Constants;
import com.cydai.cncx.network.NetworkClient;
import com.cydai.cncx.util.MyToast;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */

public class MyApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkClient.init(this,Constants.BASE_URL);
        MyToast.init(this);

        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
