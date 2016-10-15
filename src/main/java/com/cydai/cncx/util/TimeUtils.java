package com.cydai.cncx.util;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private static int second = 120;
    private static boolean isStart;
    private static String tag;

    public static void setTag(String tag) {
        TimeUtils.tag = tag;
    }

    public static String getTag() {
        return tag;
    }

    public interface OnUpdateCallback {
        void onUpdate(int millions);

        void onComplete();
    }

    private static List<OnUpdateCallback> callbacks = new ArrayList<>();

    public static void addObserver(OnUpdateCallback callback) {
        callbacks.add(callback);
    }

    public static void removeObserver(OnUpdateCallback callback){
        callbacks.remove(callback);
    }

    static ScheduledExecutorService mThreadPoolExecutor;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                for (OnUpdateCallback callback : callbacks)
                    callback.onUpdate(second);
                break;
            case 1:
                for (Iterator<OnUpdateCallback> iterator = callbacks.iterator(); iterator.hasNext(); ) {
                    OnUpdateCallback nextCallback = iterator.next();
                    nextCallback.onComplete();
                    iterator.remove();
                }
                break;
        }
        }
    };

    public static void start() {
        if (isStart)
            return;

        isStart = true;
        mThreadPoolExecutor = Executors.newScheduledThreadPool(5);
        mThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
            synchronized (TimeUtils.class) {
                if (second > 0) {
                    second--;
                    handler.sendEmptyMessage(0);
                } else {
                    tag = null;
                    stop();
                }
            }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public static void stop() {
        isStart = false;
        second = 60;

        handler.sendEmptyMessage(1);

        mThreadPoolExecutor.shutdown();
        mThreadPoolExecutor = null;
    }
}