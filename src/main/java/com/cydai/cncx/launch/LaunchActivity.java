package com.cydai.cncx.launch;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cydai.cncx.common.AnimatorAdapter;
import com.cydai.cncx.common.BaseActivity;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.orders.MainActivity;
import com.cydai.cncx.util.AppLogger;
import com.cydai.cncx.util.SharedPreUtils;
import com.cydai.cncx.widget.IndicatorView;
import com.example.apple.cjyc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LaunchActivity extends BaseActivity{

    @BindView(R.id.iv_launch_bg) ImageView mIvLaunchBg;
    @BindView(R.id.vp_launch_start) ViewPager mVpLaunchStart;
    @BindView(R.id.idv_indicator) IndicatorView mIndicatorView;
    @BindView(R.id.btn_start) Button mButtonStart;

    private Runnable mDelayRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isFirstLogin = SharedPreUtils.getBoolean(Constants.SP_FIRST_ENTER, true, LaunchActivity.this);

            if (isFirstLogin) {
                SharedPreUtils.putBoolean(Constants.SP_FIRST_ENTER, false, LaunchActivity.this);
                showWelcomePage();
            } else {
                //不是第一次进入程序
                if(SharedPreUtils.getBoolean(Constants.SP_HAVE_LOGIN, false, LaunchActivity.this)) {
                    jump2Activity(MainActivity.class, R.anim.anim_left_in, R.anim.anim_left_out);
                    finish();
                }else{
                    jump2Activity(LoginActivity.class, R.anim.anim_left_in, R.anim.anim_left_out);
                    finish();
                }
            }
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        private int lastPosition;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            AppLogger.e("position : " + position + " lastPosition" + lastPosition);
            if(position != lastPosition  && position == 3){
                //滑动到最后一个页面
                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mButtonStart,"alpha",0.0f,1.0f);
                alphaAnim.setDuration(500).start();
                ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mButtonStart,"translationY",mButtonStart.getHeight(),0.0f);
                translationAnim.setDuration(500).start();
            }else if(position != lastPosition && lastPosition == 3){
                //从最后一个页面滑动到之前的页面
                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mButtonStart,"alpha",1.0f,0.0f);
                alphaAnim.setDuration(500).start();
                ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mButtonStart,"translationY",0.0f,mButtonStart.getHeight());
                translationAnim.setDuration(500).start();
            }

            if(lastPosition != position)
                lastPosition = position;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private AnimatorAdapter animListener = new AnimatorAdapter(){
        @Override
        public void onAnimationEnd(Animator animator) {
            mIvLaunchBg.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        initVariables();

        //进行2s的延迟任务
        Handler handler = new Handler();
        handler.postDelayed(mDelayRunnable, 2000);
    }

    @Override
    public void initVariables() {
        mVpLaunchStart.setAdapter(new LaunchPagerAdapter(this));
        mVpLaunchStart.addOnPageChangeListener(mOnPageChangeListener);
        mIndicatorView.setViewPager(mVpLaunchStart);
    }

    /**
     * 显示欢迎页
     */
    private void showWelcomePage(){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        ObjectAnimator anim = ObjectAnimator.ofFloat(mIvLaunchBg,"x",0.0f,-displayMetrics.widthPixels).setDuration(200);
        anim.addListener(animListener);
        anim.start();

        mIndicatorView.setVisibility(View.VISIBLE);
        mVpLaunchStart.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(mVpLaunchStart,"x",displayMetrics.widthPixels,0.0f)
                .setDuration(200).start();
    }

    @OnClick(R.id.btn_start)
    public void startLogin(){
        jump2Activity(LoginActivity.class,R.anim.anim_left_in,R.anim.anim_left_out);
    }
}

class LaunchPagerAdapter extends PagerAdapter{
    private Context mContext;
    private int[] ids = new int[]{R.mipmap.welcome_pic_1, R.mipmap.welcome_pic_2, R.mipmap.welcome_pic_3, R.mipmap.welcome_pic_4};

    public LaunchPagerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(ids[position]);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public int getCount() {
        return ids.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}