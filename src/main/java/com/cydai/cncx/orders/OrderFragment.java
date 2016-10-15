package com.cydai.cncx.orders;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cydai.cncx.adapter.OrdersAdapter;
import com.cydai.cncx.common.BaseFragment;
import com.cydai.cncx.entity.GsonHomeInfoEntity;
import com.cydai.cncx.entity.GsonPushOrdersEntity;
import com.cydai.cncx.util.DialogCreateFactory;
import com.cydai.cncx.widget.TimerKeeperTextView;
import com.example.apple.cjyc.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 薛世君
 * Date : 2016/10/12
 * Email : 497881309@qq.com
 */
public class OrderFragment extends BaseFragment
        implements XRecyclerView.LoadingListener,
        OrderContracts.IOrderView,
        TimerKeeperTextView.TimerUpdateListener
{
    @BindView(R.id.rv_messages) XRecyclerView mXRecyclerView;
    @BindView(R.id.tv_dispatch_car) TextView mTvDispatchCar;
    @BindView(R.id.tv_back_car) TextView mTvBackCar;
    @BindView(R.id.rl_getting_car) RelativeLayout mRlGettingCar;
    @BindView(R.id.tv_dispatch_time) TimerKeeperTextView mTvDispatchTime;

    private OrdersAdapter mOrderAdapter;
    private List<GsonPushOrdersEntity.PushDriverOrderNodeBean> mOrders = new ArrayList<>();
    private OrderPresenter mPresenter;

    @Override
    public int layoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initVariables() {
        Module module = new Module();
        mPresenter = new OrderPresenter(this,module);

        mOrderAdapter = new OrdersAdapter(getContext(),R.layout.layout_order_item,mOrders);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setAdapter(mOrderAdapter);

        mPresenter.loadOrder();
    }

    @Override
    public void onRefresh() {
        mPresenter.loadOrder();
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUnsubscription();
    }

    @Override
    public void showRefreshing() {
        mXRecyclerView.setRefreshing(true);
    }

    @Override
    public void showRefreshComplete() {
        mXRecyclerView.refreshComplete();
    }

    @Override
    public void setBanner() {

    }

    @Override
    public void refreshOrder(List<GsonPushOrdersEntity.PushDriverOrderNodeBean> orders) {
        mOrders.clear();
        mOrders.addAll(orders);
        mOrderAdapter.notifyItemRangeChanged(0,5);
    }

    @Override
    public void loadMoreOrder(List<GsonPushOrdersEntity.PushDriverOrderNodeBean> orders) {
    }

    @Override
    public void showDispatchCar() {
        mRlGettingCar.setVisibility(View.INVISIBLE);
        mTvDispatchCar.setVisibility(View.VISIBLE);
        mTvBackCar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBackCar() {
        mRlGettingCar.setVisibility(View.VISIBLE);
        mTvDispatchCar.setVisibility(View.INVISIBLE);
        mTvBackCar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessageDialog(String msg) {
        DialogCreateFactory.createAlertDialog(getActivity(),"消息",msg).show();
    }

    @Override
    public void setDriverHomeInfo(GsonHomeInfoEntity entity) {

    }

    @OnClick(R.id.tv_dispatch_car)
    public void dispatchCar(){
        mTvDispatchTime.start(6, TimeUnit.MILLISECONDS);         //六分钟更新一次
        mPresenter.dispatchCar();
    }

    @OnClick(R.id.tv_back_car)
    public void backCar(){
        mTvDispatchTime.stop();
        mPresenter.backCar();
    }

    @Override
    public void onUpdate(long millions) {
        double dispatchHour = millions * 0.1d / 1000 / 60 / 60;
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        mTvDispatchTime.setText(decimalFormat.format(dispatchHour));
    }

    @Override
    public void onUpdateComplete() {
        mTvDispatchTime.setText("0.0小时");
    }
}