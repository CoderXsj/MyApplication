package com.cydai.cncx.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 懒加载的Fragment
 * Created by 薛世君
 * Date : 2016/10/12
 * Email : 497881309@qq.com
 */
public abstract class BaseFragment extends Fragment{
    private Context mContext;

    public abstract int layoutId();

    public abstract void initVariables();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflateView = inflater.inflate(layoutId(), container, false);
        mContext = getActivity();
        ButterKnife.bind(this,inflateView);

        initVariables();

        return inflateView;
    }

    public void jump2Activity(Class<? extends Activity> className, int animIn, int animOut){
        Activity act = (Activity) mContext;

        Intent intent = new Intent(mContext,className);
        act.startActivity(intent);
        act.overridePendingTransition(animIn,animOut);
    }
}
