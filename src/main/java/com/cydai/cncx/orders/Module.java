package com.cydai.cncx.orders;

import com.cydai.cncx.network.NetworkClient;

import retrofit2.Call;

/**
 * Created by 薛世君
 * Date : 2016/10/13
 * Email : 497881309@qq.com
 */

public class Module
        implements OrderContracts.IOrderModule
{
    ApiService mService;

    public Module(){
        mService = NetworkClient.getService(ApiService.class);
    }

    @Override
    public Call<String> refreshOrder() {
        return mService.requestPushOrder(0,5);
    }

    @Override
    public Call<String> loadMoreOrder(int page) {
        return mService.requestPushOrder(page,5);
    }

    @Override
    public Call<String> requestDriverHomeInfo() {
        return mService.requestDriverHomeInfo();
    }

    @Override
    public Call<String> requestDispatchCar() {
        return mService.requestDriverDispatch();
    }

    @Override
    public Call<String> requestBackCar() {
        return mService.requestDriverBackCar();
    }
}