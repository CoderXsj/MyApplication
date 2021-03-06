package com.cydai.cncx.launch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.network.NetworkClient;
import com.cydai.cncx.util.AppLogger;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 薛世君
 * Date : 2016/10/10
 * Email : 497881309@qq.com
 */

public class LoginModule implements LoginContract.ILoginModule {

    public interface HttpCallback<T>{
        void loginSuccess();

        void loginFailed(T msg);

        void sendSmsSuccess(T msg);

        void sendSmsFailed(T msg);
    }

    private LoginApiService mService;
    private HttpCallback<String> callback;

    public LoginModule(){
        mService = NetworkClient.getService(LoginApiService.class);
    }

    public void setCallback(HttpCallback<String> callback) {
        this.callback = callback;
    }

    @Override
    public Subscription login(String username, String confirmationCode,String imei) {
        Observable<String> loginRequest = mService.login(confirmationCode, username, imei);
        Subscription subscription = loginRequest.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
            if(callback != null){
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString("status");

                if("success".equals(status)){
                    callback.loginSuccess();
                }else{
                    callback.loginFailed(jsonObject.getString("errorMsg"));
                }
            }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
        return subscription;
    }

    @Override
    public Subscription getConfirmationCode(String username, String type) {
        Observable<Response<String>> confirmationCodeRequest = mService.sendSms(username, type);
        Subscription subscription = confirmationCodeRequest.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Func1<Response<String>, String>() {
                @Override
                public String call(Response<String> response) {
                    return response.body();
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    AppLogger.e("call :" + s);
                    JSONObject jsonObject = JSON.parseObject(s);
                    String status = jsonObject.getString("status");

                    if (Constants.STATUS_SUCCESS.equals(status)) {
                        callback.sendSmsSuccess(s);
                    } else if(Constants.STATUS_FAILURE.equals(status)){
                        callback.sendSmsFailed(s);
                    } else {
                        //服务器错误
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    AppLogger.e("服务器加载失败 : " + throwable.toString() + " throwable : " + throwable.getMessage());
                }
            });
        return subscription;
    }
}