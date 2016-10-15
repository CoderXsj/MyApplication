package com.cydai.cncx.orders;

import com.cydai.cncx.entity.GsonHomeInfoEntity;
import com.cydai.cncx.entity.GsonPushOrdersEntity;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */

public interface OrderContracts {

    interface IOrderView{

        void showRefreshing();

        void showRefreshComplete();

        void setBanner();

        void refreshOrder(List<GsonPushOrdersEntity.PushDriverOrderNodeBean> orders);

        void loadMoreOrder(List<GsonPushOrdersEntity.PushDriverOrderNodeBean> orders);

        void showDispatchCar();

        void showBackCar();

        void showMessageDialog(String msg);

        void setDriverHomeInfo(GsonHomeInfoEntity entity);
    }

    interface IOrderPresenter{
        void loadMoreOrder(int page);

        void loadHomeInfo();

        void loadOrder();

        void loadDriverInfo();

        void dispatchCar();

        void backCar();
    }

    interface IOrderModule{
        Call<String> refreshOrder();

        Call<String> loadMoreOrder(int page);

        Call<String> requestDriverHomeInfo();

        Call<String> requestDispatchCar();

        Call<String> requestBackCar();
    }
}