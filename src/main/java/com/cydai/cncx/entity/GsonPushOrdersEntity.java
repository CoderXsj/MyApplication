package com.cydai.cncx.entity;

import java.util.List;

/**
 * Created by 薛世君
 * Date : 2016/10/13
 * Email : 497881309@qq.com
 */

public class GsonPushOrdersEntity {


    /**
     * success : true
     * status : success
     * message : 获取数据成功
     * pushDriverOrderNode : [{"order_no":"2016092917255635665","status":1,"mobile":null,"created":"1475141156","driverName":null,"fee":"0.00","from_city":"武汉市","dest_city":"崇阳县"},{"order_no":"2016092916342978408","status":0,"mobile":"132****2251","created":"1475138069","driverName":"裴祥艮","fee":"0.00","from_city":"武汉市","dest_city":"崇阳县"},{"order_no":"2016092916032890019","status":0,"mobile":"158****8278","created":"1475136208","driverName":"张绍望","fee":"0.00","from_city":"武汉市","dest_city":"崇阳县"}]
     * count : 3
     */

    private boolean success;
    private String status;
    private String message;
    private int count;
    /**
     * order_no : 2016092917255635665
     * status : 1
     * mobile : null
     * created : 1475141156
     * driverName : null
     * fee : 0.00
     * from_city : 武汉市
     * dest_city : 崇阳县
     */

    private List<PushDriverOrderNodeBean> pushDriverOrderNode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PushDriverOrderNodeBean> getPushDriverOrderNode() {
        return pushDriverOrderNode;
    }

    public void setPushDriverOrderNode(List<PushDriverOrderNodeBean> pushDriverOrderNode) {
        this.pushDriverOrderNode = pushDriverOrderNode;
    }

    public static class PushDriverOrderNodeBean {
        private String order_no;
        private int status;
        private String mobile;
        private String created;
        private String driverName;
        private String fee;
        private String from_city;
        private String dest_city;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getFrom_city() {
            return from_city;
        }

        public void setFrom_city(String from_city) {
            this.from_city = from_city;
        }

        public String getDest_city() {
            return dest_city;
        }

        public void setDest_city(String dest_city) {
            this.dest_city = dest_city;
        }
    }

    @Override
    public String toString() {
        return "GsonPushOrdersEntity{" +
                "success=" + success +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", count=" + count +
                ", pushDriverOrderNode=" + pushDriverOrderNode +
                '}';
    }
}
