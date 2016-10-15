package com.cydai.cncx.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.cydai.cncx.common.Constants;
import com.cydai.cncx.entity.GsonPushOrdersEntity;
import com.cydai.cncx.util.TimeUtils;
import com.example.apple.cjyc.R;

import java.util.List;

/**
 * Created by 薛世君
 * Date : 2016/10/13
 * Email : 497881309@qq.com
 */

public class OrdersAdapter extends BaseRecyclerAdapter<GsonPushOrdersEntity.PushDriverOrderNodeBean>{

    public OrdersAdapter(Context context, int layoutId, List<GsonPushOrdersEntity.PushDriverOrderNodeBean> data) {
        super(context, layoutId, data);

        TimeUtils.addObserver(callback);
    }

    @Override
    public void convert(BaseViewHolder holder, GsonPushOrdersEntity.PushDriverOrderNodeBean gsonOrderEntity,int position) {
        TextView mTvOrderStatus = holder.getView(R.id.tv_order_status);
        TextView mTvMessageDate = holder.getView(R.id.tv_message_date);
        TextView mTvFromCity = holder.getView(R.id.tv_from_city);
        TextView mTvDestCity = holder.getView(R.id.tv_dest_city);
        TextView mTvMessagePrompt = holder.getView(R.id.tv_message_prompt);

        holder.setTag(position);
        mTvMessageDate.setText(gsonOrderEntity.getCreated());
        mTvFromCity.setText(gsonOrderEntity.getFrom_city());
        mTvDestCity.setText(gsonOrderEntity.getDest_city());
        if(gsonOrderEntity.getStatus() == Constants.STATUS_ORDER_GET){
            CharSequence str = "订单已被抢到";

            if(gsonOrderEntity.getMobile() != null) {
                str = getPhoneNumberChar(gsonOrderEntity.getMobile());
            }

            mTvOrderStatus.setText("已过期");
            mTvMessagePrompt.setText(str);
        }else{
            mTvOrderStatus.setText("正在抢单");
            mTvMessagePrompt.setText("订单正在抢，手快有，手慢无");
            callback.setUpdateTextView(gsonOrderEntity.getOrder_no(),mTvOrderStatus,mTvMessagePrompt);
        }
    }

    private UpdateCallback callback = new UpdateCallback();
    class UpdateCallback implements TimeUtils.OnUpdateCallback{
        private String orderNo;
        private TextView mTvMessageStatus;
        private TextView mTvMessagePrompt;

        public void setUpdateTextView(String orderNo,TextView mTvMessageStatus,TextView mTvMessagePrompt) {
            this.mTvMessageStatus = mTvMessageStatus;
            this.mTvMessagePrompt = mTvMessagePrompt;
            this.orderNo = orderNo;
        }

        @Override
        public void onUpdate(int millions) {
            if(mTvMessageStatus != null && orderNo.equals(TimeUtils.getTag())) {
                mTvMessageStatus.setTextColor(Color.parseColor("#d28c31"));

                mTvMessageStatus.setText("正在抢单（" + millions + "）");

                mTvMessagePrompt.setText("订单正在抢，手快有，手慢无");
            }
        }

        @Override
        public void onComplete() {
            if(mTvMessageStatus != null) {
                mTvMessageStatus.setTextColor(Color.parseColor("#504667"));

                mTvMessageStatus.setText("已过期");
            }
        }
    }

    private CharSequence getPhoneNumberChar(String number){
        String first3num = number.substring(0, 3);
        String end4num = number.substring(7, 11);
        String phoneNumber = first3num + "****" + end4num;
        String contentText = "订单已被用户:" + phoneNumber + "抢到";
        SpannableString string = new SpannableString(contentText);
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#e3830c")),contentText.indexOf(":"),contentText.indexOf(":") + 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return string;
    }
}