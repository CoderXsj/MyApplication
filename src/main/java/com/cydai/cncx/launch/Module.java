package com.cydai.cncx.launch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.network.NetworkClient;
import com.cydai.cncx.network.UploadFileRequestBody;
import com.cydai.cncx.util.AppLogger;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

public class Module implements
        LoginContract.ILoginModule,
        ValidateMobileContract.IValidateMobileModule,
        AuthenticationContract.IAuthenticationModule,
        BindCarContract.IBindCarModule
{

    public interface LoginCallback<T>{
        void loginSuccess(T accessToken);

        void loginFailed(T msg);
    }

    public interface SendSmsCallback<T>{
        void sendSmsSuccess();

        void sendSmsFailed(T msg);
    }

    public interface ValidateCodeCallback{
        void validateSuccess();

        void validateFailed(String msg);
    }

    public interface RegisterDriverCallback{
        void registerSuccess();

        void registerFailed(String msg);
    }

    private ApiService mService;
    private LoginCallback<String> mLoginCallback;
    private SendSmsCallback<String> mSendSmsCallback;
    private ValidateCodeCallback mValidateCodeCallback;
    private UploadFileRequestBody.ProgressListener mProgressListener;
    private RegisterDriverCallback mRegisterDriverCallback;

    public Module(){
        mService = NetworkClient.getService(ApiService.class);
    }

    public void setLoginCallback(LoginCallback<String> callback) {
        this.mLoginCallback = callback;
    }

    public void setSendSmsCallback(SendSmsCallback<String> callback) {
        this.mSendSmsCallback = callback;
    }

    public void setValidateCodeCallback(ValidateCodeCallback mValidateCodeCallback) {
        this.mValidateCodeCallback = mValidateCodeCallback;
    }

    public void setProgressListener(UploadFileRequestBody.ProgressListener mProgressListener) {
        this.mProgressListener = mProgressListener;
    }

    public void setRegisterDriverCallback(RegisterDriverCallback mRegisterDriverCallback) {
        this.mRegisterDriverCallback = mRegisterDriverCallback;
    }

    @Override
    public Subscription login(String username, String confirmationCode,String imei) {
        Observable<String> loginRequest = mService.login(confirmationCode, username, imei);
        Subscription subscription = loginRequest.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
            if(mLoginCallback != null){
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString("status");

                if("success".equals(status)){
                    String accessToken = jsonObject.getString("Access_Token");
                    mLoginCallback.loginSuccess(accessToken);
                }else{
                    mLoginCallback.loginFailed(jsonObject.getString("errorMsg"));
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
                    mSendSmsCallback.sendSmsSuccess();
                } else if(Constants.STATUS_FAILURE.equals(status)){
                    String errorMsg = jsonObject.getString("errorMsg");
                    mSendSmsCallback.sendSmsFailed(errorMsg);
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


    @Override
    public Subscription getConfirmationCode(String username) {
        Observable<Response<String>> confirmationCodeRequest = mService.sendSms(username);
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
                        mSendSmsCallback.sendSmsSuccess();
                    } else if(Constants.STATUS_FAILURE.equals(status)){
                        String errorMsg = jsonObject.getString("errorMsg");
                        mSendSmsCallback.sendSmsFailed(errorMsg);
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

    @Override
    public Subscription validateConfirmationCode(String mobile, String confirmationCode) {
        Observable<Response<String>> confirmationCodeRequest = mService.validateCode(mobile,confirmationCode);
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

                    if ("VALID".equals(status)) {
                        mValidateCodeCallback.validateSuccess();
                    } else if(Constants.STATUS_FAILURE.equals(status)){
                        mValidateCodeCallback.validateFailed("");
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

    @Override
    public Subscription uploadPicture(final int type,final String pic) {
        File file = new File(pic);
        if(!file.exists()){
            return null;
        }

        String fileName = pic.substring(pic.lastIndexOf("/") + 1,pic.length());

//        AppLogger.e("fileName : " + fileName + "   " + file.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(requestBody,mProgressListener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("images",fileName,uploadFileRequestBody);

        Subscription subscribe = mService.uploadPicture(part)
            .subscribeOn(Schedulers.io())
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
                if ("VALID".equals(status)) {
                    String imageName = jsonObject.getString("imageName");

                    mProgressListener.onFinish(Constants.STATUS_SUCCESS, type + ";" + imageName + ";" + pic);
                } else if (Constants.STATUS_FAILURE.equals(status)) {
                    mProgressListener.onFinish(Constants.STATUS_FAILURE, "error");
                } else {
                }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    mProgressListener.onFinish(Constants.STATUS_FAILURE, "error");
                    printCallStatck(throwable);
                }
            });

        return subscribe;
    }

    @Override
    public Call<String> register(Map<String,String> params) {
        Call<String> register = mService.register(params);
        return register;
    }

    private void printCallStatck(Throwable throwable) {
        StackTraceElement[] stackElements = throwable.getStackTrace();//通过Throwable获得堆栈信息
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                AppLogger.e(stackElements[i].getClassName() + "\t" + stackElements[i].getFileName() + "\t" + stackElements[i].getLineNumber() + "\t" + stackElements[i].getMethodName());
            }
        }
    }
}