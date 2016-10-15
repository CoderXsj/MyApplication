package com.cydai.cncx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 薛世君
 * Date : 2016/10/12
 * Email : 497881309@qq.com
 */

public class MainAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;

    public MainAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}