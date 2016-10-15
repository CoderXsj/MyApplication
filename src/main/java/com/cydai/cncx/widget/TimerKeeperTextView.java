package com.cydai.cncx.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 薛世君
 * Date : 2016/10/14
 * Email : 497881309@qq.com
 */

public class TimerKeeperTextView extends TextView{
    public interface TimerUpdateListener{
        void onUpdate(long millions);

        void onUpdateComplete();
    }

    public TimerKeeperTextView(Context context) {
        this(context,null);
    }

    public TimerKeeperTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimerKeeperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private TimerUpdateListener onTimerUpdateListener;
    private ScheduledExecutorService mThreadPoolExecutor;
    private long startTime;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(onTimerUpdateListener != null){
                onTimerUpdateListener.onUpdate(System.currentTimeMillis() - startTime);
            }
        }
    };

    public void setOnTimerUpdateListener(TimerUpdateListener onTimerUpdateListener) {
        this.onTimerUpdateListener = onTimerUpdateListener;
    }

    /**
     * @param unit  单位 TimeUnit
     * @param refreshRate   更新的频率
     */
    public void start(int refreshRate,TimeUnit unit){
        startTime = System.currentTimeMillis();
        mThreadPoolExecutor = Executors.newScheduledThreadPool(5);
        mThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, refreshRate, unit);
    }

    public void stop(){
        mThreadPoolExecutor.shutdown();
        if(onTimerUpdateListener != null){
            onTimerUpdateListener.onUpdateComplete();
        }
    }
}
