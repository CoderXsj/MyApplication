package com.cydai.cncx.orders;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cydai.cncx.adapter.MainAdapter;
import com.cydai.cncx.common.BaseActivity;
import com.example.apple.cjyc.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 薛世君
 * Date : 2016/10/12
 * Email : 497881309@qq.com
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.tl_top_tab) TabLayout mTopTabLayout;
    @BindView(R.id.vp_viewpager) ViewPager mVpViewPager;

    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_main);
        ButterKnife.bind(this);

        initVariables();
    }

    @Override
    public void initVariables() {
        mFragments.add(new OrderFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new OrderFragment());
        mVpViewPager.setAdapter(new MainAdapter(mFragments, getSupportFragmentManager()));
        mVpViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTopTabLayout));

        TextView tvPersonal = new TextView(this);
        tvPersonal.setText("我");
        tvPersonal.setTextColor(Color.parseColor("#858397"));
        tvPersonal.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15, getResources().getDisplayMetrics()));
        tvPersonal.setGravity(Gravity.CENTER);

        TextView tvTrip = new TextView(this);
        tvTrip.setText("行程");
        tvTrip.setTextColor(Color.parseColor("#858397"));
        tvTrip.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15, getResources().getDisplayMetrics()));
        tvTrip.setGravity(Gravity.CENTER);

        ImageView ivMainImageView = new ImageView(this);
        ivMainImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ivMainImageView.setImageResource(R.mipmap.ic_tab_logo_unsel);

        mTopTabLayout.addTab(mTopTabLayout.newTab().setCustomView(tvPersonal));
        mTopTabLayout.addTab(mTopTabLayout.newTab().setCustomView(ivMainImageView));
        mTopTabLayout.addTab(mTopTabLayout.newTab().setCustomView(tvTrip));

        mTopTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.parseColor("#fa8d00"));
                } else if (view instanceof ImageView) {
                    ImageView iv = (ImageView) view;
                    iv.setImageResource(R.mipmap.ic_tab_logo_sel);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.parseColor("#858397"));
                } else if (view instanceof ImageView) {
                    ImageView iv = (ImageView) view;
                    iv.setImageResource(R.mipmap.ic_tab_logo_unsel);
                }
            }
        });
        mTopTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#fa8d00"));
        mVpViewPager.setCurrentItem(1);
    }


}