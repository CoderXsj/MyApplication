package com.cydai.cncx.launch;

import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */

public interface LoginApiService {

    @POST("driver/login")
    Observable<String> login(@Header("code") String code,@Header("mobile") String mobile,@Header("driver_token") String driverToken);

    @POST("ispublic/send")
    Observable<String> sendSms(@Header("mobile") String mobile,@Header("type") String type);
}
