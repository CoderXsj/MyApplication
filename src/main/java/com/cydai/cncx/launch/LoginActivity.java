package com.cydai.cncx.launch;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cydai.cncx.common.BaseActivity;
import com.cydai.cncx.orders.MainActivity;
import com.cydai.cncx.util.DialogCreateFactory;
import com.cydai.cncx.util.MyToast;
import com.cydai.cncx.widget.CountDownTextView;
import com.example.apple.cjyc.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView {
    @BindView(R.id.tv_confirmation) CountDownTextView mTvConfirmation;
    @BindView(R.id.et_password_code) EditText mEtPasswordCode;
    @BindView(R.id.et_phone_number) EditText mEtPhoneNumber;
    @BindView(R.id.tv_contract) TextView mTvContract;
    @BindView(R.id.btn_back) Button mBtnBack;
    @BindView(R.id.tv_title) TextView mTvTitle;

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
        Module module = new Module();
        mPresenter = new LoginPresenter(this,module);
        module.setLoginCallback(mPresenter);
        module.setSendSmsCallback(mPresenter);

        mTvContract.setText("<<楚牛约车服务条款>>");
        mBtnBack.setVisibility(View.GONE);
        mTvTitle.setText("登录");
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
    public void hiddenProgressDialog() {
        mDialog.dismiss();
    }

    @Override
    public void showOriginalConfirmationButton() {
        mTvConfirmation.setClickable(true);
        mTvConfirmation.setEnabled(true);
        mTvConfirmation.setTextColor(Color.parseColor("#ffffff"));
        mTvConfirmation.setBackgroundResource(R.drawable.sp_confirmation_original);
        mTvConfirmation.setText("获取验证码");
    }

    @Override
    public void showMessageDialog(String msg) {
        DialogCreateFactory.createAlertDialog(this,"消息",msg+"").show();
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
    @OnClick(R.id.bt_register)
    public void jump2registerActivity() {
        jump2Activity(ValidateMobileActivity.class,R.anim.anim_left_in,R.anim.anim_left_out);
    }

    @Override
    public void jump2mainActivity() {
        jump2Activity(MainActivity.class,R.anim.anim_left_in,R.anim.anim_left_out);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void login(){
        mPresenter.login(mEtPhoneNumber.getText().toString(),mEtPasswordCode.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnsubscription();
    }
}