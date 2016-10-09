package com.cydai.cncx.launch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cydai.cncx.BaseActivity;
import com.cydai.cncx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.bt_confirmation) Button mBtnConfirmation;
    @BindView(R.id.iv_password) ImageView mIvPassword;
    @BindView(R.id.et_password_code) EditText mEtPasswordCode;
    @BindView(R.id.iv_username) ImageView mIvUserName;
    @BindView(R.id.et_phone_number) EditText mEtPhoneNumber;
    @BindView(R.id.tv_contract) TextView mTvContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        initVariables();
    }

    @Override
    public void initVariables() {
        mEtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //得到焦点
                if (hasFocus) {
                    mEtPhoneNumber.setTextColor(getResources().getColor(R.color.colorWhite));
                    mEtPhoneNumber.setCursorVisible(true);
                    mIvUserName.setImageDrawable(getResources().getDrawable(R.mipmap.ic_username_selected));
                } else {
                    //失去焦点
                    mEtPhoneNumber.setCursorVisible(false);
                    mIvUserName.setImageDrawable(getResources().getDrawable(R.mipmap.ic_username_unselected));
                }
            }
        });

        mEtPasswordCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //得到焦点
                if (hasFocus) {
                    mEtPasswordCode.setTextColor(getResources().getColor(R.color.colorWhite));
                    mEtPasswordCode.setCursorVisible(true);
                    mIvPassword.setImageDrawable(getResources().getDrawable(R.mipmap.ic_password_selected));
                } else {
                    //失去焦点
                    mEtPasswordCode.setCursorVisible(false);
                    mIvPassword.setImageDrawable(getResources().getDrawable(R.mipmap.ic_password_unselected));
                }
            }
        });

        mTvContract.setText("<<楚牛约车服务条款>>");
    }


}