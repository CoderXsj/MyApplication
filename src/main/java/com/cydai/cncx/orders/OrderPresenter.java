package com.cydai.cncx.orders;

import com.alibaba.fastjson.JSONObject;
import com.cydai.cncx.common.BasePresenter;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.util.AppLogger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 薛世君
 * Date : 2016/10/13
 * Email : 497881309@qq.com
 */

public class OrderPresenter extends BasePresenter<OrderContracts.IOrderView,OrderContracts.IOrderModule>
        implements OrderContracts.IOrderPresenter
{
    public OrderPresenter(OrderContracts.IOrderView mView, OrderContracts.IOrderModule mModule) {
        super(mView, mModule);
    }
    @Override
    public void loadMoreOrder(int page) {
        Call<String> gsonOrderEntityCall = mModule.loadMoreOrder(page);
        gsonOrderEntityCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = validateStatus(response);

                switch(body) {
                    case Constants.STATUS_SUCCESS:          //更新ui
                        break;
                    case Constants.STATUS_FAILURE:          //失败    提示消息
                        break;
                    case Constants.STATUS_ERROR:            //服务器错误     提示
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    @Override
    public void loadHomeInfo() {
        Call<String> call = mModule.requestDriverHomeInfo();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = validateStatus(response);

                switch(body) {
                    case Constants.STATUS_SUCCESS:          //更新ui
                        break;
                    case Constants.STATUS_FAILURE:          //失败    提示消息
                        break;
                    case Constants.STATUS_ERROR:            //服务器错误     提示
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    @Override
    public void loadOrder() {
        mView.showRefreshing();
        Call<String> gsonOrderEntityCall = mModule.refreshOrder();
        gsonOrderEntityCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = validateStatus(response);

                switch(body){
                    case Constants.STATUS_SUCCESS:          //更新ui订单
                        break;
                    case Constants.STATUS_FAILURE:          //失败    返回登陆页面
                        break;
                    case Constants.STATUS_ERROR:            //服务器错误     返回登陆页面
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                AppLogger.e("error");
            }
        });
    }

    @Override
    public void loadDriverInfo() {
        Call<String> call = mModule.requestDriverHomeInfo();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void dispatchCar() {
        Call<String> call = mModule.requestDispatchCar();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                JSONObject jsonObject = JSONObject.parseObject(body);
                if(Constants.STATUS_SUCCESS.equals(jsonObject.getString("status"))){
                    mView.showBackCar();
                }else if(Constants.STATUS_FAILURE.equals(jsonObject.getString("status"))){
                    mView.showMessageDialog("出车失败");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void backCar() {
        Call<String> call = mModule.requestBackCar();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                JSONObject jsonObject = JSONObject.parseObject(body);
                if(Constants.STATUS_SUCCESS.equals(jsonObject.getString("status"))){
                    mView.showDispatchCar();
                }else if(Constants.STATUS_FAILURE.equals(jsonObject.getString("status"))){
                    mView.showMessageDialog("收车失败");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private String validateStatus(Response<String> response) {
        String body = response.body();
        JSONObject jsonObject = JSONObject.parseObject(body);

        if(Constants.STATUS_SUCCESS.equals(jsonObject.getString("status"))){
            return Constants.STATUS_SUCCESS;
        }else if(Constants.STATUS_FAILURE.equals(jsonObject.getString("status"))){
            return Constants.STATUS_FAILURE;
        }else{
            return Constants.STATUS_ERROR;
        }
    }
}