package com.cydai.cncx.launch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.cydai.cncx.common.BaseActivity;
import com.cydai.cncx.util.DialogCreateFactory;
import com.cydai.cncx.util.MyToast;
import com.cydai.cncx.widget.CountDownTextView;
import com.cydai.cncx.widget.dialog.MaterialDialog;
import com.example.apple.cjyc.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public class ValidateMobileActivity extends BaseActivity implements ValidateMobileContract.IValidateMobileView{
    @BindView(R.id.tv_confirmation) CountDownTextView mTvConfirmation;
    @BindView(R.id.et_phone_number) EditText mEtPhoneNumber;
    @BindView(R.id.tv_contract) TextView mTvContract;
    @BindView(R.id.et_password_code) EditText mEtConfirmationCode;
    @BindView(R.id.tv_title) TextView mTvTitle;
    private ValidateMobilePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        ButterKnife.bind(this);
        initVariables();
    }

    @Override
    public void initVariables() {
        Module module = new Module();
        mPresenter = new ValidateMobilePresenter(this,module);
        module.setSendSmsCallback(mPresenter);
        module.setValidateCodeCallback(mPresenter);

        mTvContract.setText("<<楚牛约车服务条款>>");
        mTvTitle.setText("验证手机");
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnsubscription();
    }

    @OnClick(R.id.btn_back)
    public void back(){
        finish();
        overridePendingTransition(R.anim.anim_left_in,R.anim.anim_left_out);
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
    public void showOriginalConfirmationButton() {
        mTvConfirmation.setClickable(true);
        mTvConfirmation.setEnabled(true);
        mTvConfirmation.setTextColor(Color.parseColor("#ffffff"));
        mTvConfirmation.setBackgroundResource(R.drawable.sp_confirmation_original);
        mTvConfirmation.setText("获取验证码");
    }

    @Override
    public void jump2authentication(Map<String,String> params) {
        jump2Activity(AuthenticationActivity.class,R.anim.anim_left_in,R.anim.anim_left_out,params);
    }

    @Override
    @OnClick(R.id.bt_next_step)
    public void validateConfirmationCode() {
        String tel = mEtPhoneNumber.getText().toString();
        String confirmationCode = mEtConfirmationCode.getText().toString();

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

        if(TextUtils.isEmpty(confirmationCode)){
            MaterialDialog alertDialog = DialogCreateFactory.createAlertDialog(this, "消息", "请输入短信验证码");
            alertDialog.show();
            return;
        }

        mPresenter.validateConfirmationCode(tel,confirmationCode);
    }

    @Override
    public void showValidateFailedDialog() {
        MaterialDialog alertDialog = DialogCreateFactory.createAlertDialog(this, "消息", "验证码不正确");
        alertDialog.show();
    }
}