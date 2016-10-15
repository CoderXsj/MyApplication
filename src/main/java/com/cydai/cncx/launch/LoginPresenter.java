package com.cydai.cncx.launch;

import android.widget.TextView;

import com.cydai.cncx.MyApplication;
import com.cydai.cncx.common.BasePresenter;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.util.MyToast;
import com.cydai.cncx.util.SharedPreUtils;
import com.cydai.cncx.widget.CountDownTextView;

import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/10
 * Email : 497881309@qq.com
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView,LoginContract.ILoginModule>
        implements LoginContract.ILoginPresenter,
                    Module.LoginCallback<String>,
                    Module.SendSmsCallback<String>
{

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
        mView.showProgressDialog();
//        Context context = MyApplication.getContext();
//        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
//        String imei = TelephonyMgr.getDeviceId();

        Subscription subscription = mModule.login(username, confirmationCode, username);
        addSubscription(subscription);
    }

    @Override
    public void loginSuccess(String accessToken) {
        mView.hiddenProgressDialog();
        SharedPreUtils.putString (Constants.SP_ACCESS_TOKEN,accessToken,MyApplication.getContext());
        SharedPreUtils.putBoolean (Constants.SP_HAVE_LOGIN,true,MyApplication.getContext());
        mView.jump2mainActivity();
    }

    @Override
    public void loginFailed(String msg) {
        mView.hiddenProgressDialog();
        mView.showMessageDialog(msg);
    }

    @Override
    public void sendSmsSuccess() {
        MyToast.L("发送成功");
    }

    @Override
    public void sendSmsFailed(String msg) {
        MyToast.L(msg);
    }
}