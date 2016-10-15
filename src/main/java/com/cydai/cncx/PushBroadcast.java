package com.cydai.cncx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cydai.cncx.util.TimeUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送过来的广播
 */
public class PushBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

            Log.e("tag","收到了自定义消息。消息内容是：" + title + " : " + message + " : " + extras);

            TimeUtils.start();
            TimeUtils.setTag(message);
        }
    }
}