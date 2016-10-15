package com.cydai.cncx.launch;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */

public interface LoginApiService {

    @POST("driver/login")
    Observable<String> login(@Query("code") String code, @Query("mobile") String mobile, @Query("driver_token") String driverToken);

    @POST("ispublic/send")
    Observable<Response<String>> sendSms(@Query("mobile") String mobile, @Query("type") String type);
}