package com.cydai.cncx.launch;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */

public interface ApiService {

    @POST("driver/login")
    Observable<String> login(@Query("code") String code, @Query("mobile") String mobile, @Query("driver_token") String driverToken);

    @POST("ispublic/send")
    Observable<Response<String>> sendSms(@Query("mobile") String mobile, @Query("type") String type);

    @POST("ispublic/regsend")
    Observable<Response<String>> sendSms(@Query("mobile") String mobile);

    @POST("ispublic/verify")
    Observable<Response<String>> validateCode(@Query("mobile") String mobile,@Query("code") String code);

    @Multipart
    @POST("driver/upload")
    Observable<Response<String>> uploadPicture(@Part MultipartBody.Part file);

    @POST("driver/register")
    Call<String> register(@QueryMap Map<String,String> params);


}