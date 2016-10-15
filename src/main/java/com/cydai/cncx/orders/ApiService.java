package com.cydai.cncx.orders;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 薛世君
 * Date : 2016/10/12
 * Email : 497881309@qq.com
 */

public interface ApiService {

    @POST("pushDriverOrder/getPushOrderList")
    Call<String> requestPushOrder(@Query("page") int page, @Query("pagesize") int pageSize);

    @POST("driver/out")
    Call<String> requestDriverDispatch();

    @POST("driver/home")
    Call<String> requestDriverBackCar();

    @POST("driver/index")
    Call<String> requestDriverHomeInfo();
}