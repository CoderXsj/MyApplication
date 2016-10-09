package com.cydai.cncx;

import android.app.Application;

import com.cydai.cncx.common.Constants;
import com.cydai.cncx.network.NetworkClient;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkClient.init(this,Constants.BASE_URL);
    }
}
