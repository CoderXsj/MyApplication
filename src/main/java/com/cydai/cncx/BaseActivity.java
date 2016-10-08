package com.cydai.cncx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public abstract class BaseActivity extends FragmentActivity{

    protected String TAG = getClass().getSimpleName();

    private List<Activity> activities = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activities.add(this);
    }

    public abstract void initVariables();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        activities.remove(this);
    }

    public void finishAll(){
        for(Activity act : activities){
            act.finish();
        }
        activities.clear();
    }

    public void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void jump2Activity(Class<? extends Activity> className,int animIn,int animOut){
        Intent intent = new Intent(this,className);
        startActivity(intent);
        overridePendingTransition(animIn,animOut);
    }

    @Override protected void onResume() { super.onResume(); JPushInterface.onResume(this); }
    @Override protected void onPause() { super.onPause(); JPushInterface.onPause(this); }
}