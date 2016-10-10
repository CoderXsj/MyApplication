package com.cydai.cncx.launch;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cydai.cncx.BaseActivity;
import com.cydai.cncx.R;
import com.cydai.cncx.util.MyToast;
import com.cydai.cncx.widget.CountDownTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView {
    @BindView(R.id.tv_confirmation) CountDownTextView mTvConfirmation;
    @BindView(R.id.iv_password) ImageView mIvPassword;
    @BindView(R.id.et_password_code) EditText mEtPasswordCode;
    @BindView(R.id.iv_username) ImageView mIvUserName;
    @BindView(R.id.et_phone_number) EditText mEtPhoneNumber;
    @BindView(R.id.tv_contract) TextView mTvContract;

    private LoginPresenter mPresenter;
    private ProgressDialog mDialog;

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

        LoginModule module = new LoginModule();
        mPresenter = new LoginPresenter(this,module);
        module.setCallback(mPresenter);

        mTvContract.setText("<<楚牛约车服务条款>>");
    }

    /**
     * 请求验证码
     */
    @OnClick(R.id.tv_confirmation)
    public void postConfirmationCode(){
        String tel = mEtPhoneNumber.getText().toString();

        if(TextUtils.isEmpty(tel)){
            MyToast.L("手机号码不能为空");
            return;
        }

        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        if(!m.matches()){
            MyToast.L("手机号码格式不正确");
            return;
        }

        mPresenter.postConfirmationCode(tel,mTvConfirmation);
    }

    @Override
    public void showProgressDialog() {
        mDialog = ProgressDialog.show(this, "", "正在登陆", true, false);
    }

    @Override
    public void dismissProgressDialog() {
        mDialog.dismiss();
    }

    @Override
    public void showOriginalConfirmationButton() {
        mTvConfirmation.setClickable(false);
        mTvConfirmation.setEnabled(false);
        mTvConfirmation.setTextColor(Color.parseColor("#705753"));
        mTvConfirmation.setBackgroundResource(R.drawable.sp_confirmation_original);
        mTvConfirmation.setText("获取验证码");
    }

    @Override
    public void updateConfirmationButton(long million) {
        mTvConfirmation.setBackgroundResource(R.drawable.sp_confirmation_updating);
        mTvConfirmation.setTextColor(Color.parseColor("#ffffff"));
        mTvConfirmation.setText("发送成功(" + million + ")");
        mTvConfirmation.setClickable(false);
        mTvConfirmation.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnsubscription();
    }
}