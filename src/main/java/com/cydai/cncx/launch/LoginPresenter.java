package com.cydai.cncx.launch;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.cydai.cncx.BasePresenter;
import com.cydai.cncx.MyApplication;
import com.cydai.cncx.util.AppLogger;
import com.cydai.cncx.util.MyToast;
import com.cydai.cncx.widget.CountDownTextView;

import rx.Subscription;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by 薛世君
 * Date : 2016/10/10
 * Email : 497881309@qq.com
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView,LoginContract.ILoginModule> implements LoginContract.ILoginPresenter,LoginModule.HttpCallback<String>{

    public LoginPresenter(LoginContract.ILoginView view, LoginContract.ILoginModule module){
        super(view,module);
    }

    @Override
    public void postConfirmationCode(String mobile,CountDownTextView countDownTextView) {
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

        Subscription confirmationCode = mModule.getConfirmationCode(mobile, "0");
        addSubscription(confirmationCode);
    }

    @Override
    public void login(String username, String confirmationCode) {
        Context context = MyApplication.getContext();
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        String imei = TelephonyMgr.getDeviceId();

        Subscription subscription = mModule.login(username, confirmationCode, imei);

        addSubscription(subscription);
    }

    @Override
    public void loginSuccess(String msg) {
        MyToast.L("登陆成功");
    }

    @Override
    public void loginFailed(String msg) {
        MyToast.L("登陆失败");
    }

    @Override
    public void sendSmsSuccess(String msg) {
        MyToast.L("发送成功");
    }

    @Override
    public void sendSmsFailed(String msg) {
    }
}