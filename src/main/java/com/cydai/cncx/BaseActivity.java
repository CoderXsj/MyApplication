package com.cydai.cncx;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity{

    protected String TAg = getClass().getSimpleName();

    private List<Activity> activities = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activities.add(this);
    }

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
}