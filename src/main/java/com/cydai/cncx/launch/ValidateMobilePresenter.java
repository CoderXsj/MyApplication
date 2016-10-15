package com.cydai.cncx.launch;

import android.widget.TextView;

import com.cydai.cncx.common.BasePresenter;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.util.MyToast;
import com.cydai.cncx.widget.CountDownTextView;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public class ValidateMobilePresenter extends BasePresenter<ValidateMobileContract.IValidateMobileView, ValidateMobileContract.IValidateMobileModule>
        implements ValidateMobileContract.IValidateMobilePresenter,
        Module.SendSmsCallback<String>,
        Module.ValidateCodeCallback
{
    private String mMobile;

    public ValidateMobilePresenter(ValidateMobileContract.IValidateMobileView mView, ValidateMobileContract.IValidateMobileModule mModule) {
        super(mView, mModule);
    }

    @Override
    public void postConfirmationCode(String mobile, CountDownTextView countDownTextView) {
        countDownTextView.start(60);
        countDownTextView.setOnUpdateTimeListener(new CountDownTextView.OnUpdateTimeListener() {
            @Override
            public void onStart() {
                mView.showOriginalConfirmationButton();
            }

            @Override
            public void onUpdateTime(TextView textView, long millions) {
                mView.updateConfirmationButton(millions);
            }

            @Override
            public void onFinish() {
                mView.showOriginalConfirmationButton();
            }
        });
        mMobile = mobile;
        Subscription confirmationCode = mModule.getConfirmationCode(mobile);
        addSubscription(confirmationCode);
    }

    @Override
    public void validateConfirmationCode(String mobile, String confirmationCode) {
        Subscription subscription = mModule.validateConfirmationCode(mobile, confirmationCode);
        addSubscription(subscription);
    }

    @Override
    public void sendSmsSuccess() {
        MyToast.L("发送成功");
    }

    @Override
    public void sendSmsFailed(String msg) {
        MyToast.L(msg);
    }

    @Override
    public void validateSuccess() {
        HashMap<String,String> params = new HashMap<>();
        params.put(Constants.INTENT_DRIVER_MOBILE,mMobile);
        mView.jump2authentication(params);
    }

    @Override
    public void validateFailed(String msg) {
        mView.showValidateFailedDialog();
    }
}