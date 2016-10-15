package com.cydai.cncx.launch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.cydai.cncx.common.BasePresenter;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.network.UploadFileRequestBody;
import com.cydai.cncx.util.AppLogger;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public class BindCarPresenter extends BasePresenter<BindCarContract.IBindCarView,BindCarContract.IBindCarModule>
        implements BindCarContract.IBindCarPresenter,
        UploadFileRequestBody.ProgressListener,
        Module.RegisterDriverCallback
{
    private String mDrivingLicense;

    public BindCarPresenter(BindCarContract.IBindCarView mView, BindCarContract.IBindCarModule mModule) {
        super(mView, mModule);
    }


    @Override
    public void uploadPicture(int type, String pic) {
        mView.showUploadPictureProgressDialog();
        Subscription subscription = mModule.uploadPicture(type,pic);

        if(subscription == null) {
            mView.hiddenUploadPictureProgressDialog();
            mView.showMessageDialog("图片上传失败,没有找到对应的图片");
            return;
        }

        addSubscription(subscription);
    }

    @Override
    public void register(Map<String,String> params,String driverLicenseImage,String driverIdCardImage) {
        String msg = null;

        String vehicleNo = params.get("vehicle_no");                    //车牌号
        String mobile = params.get("mobile");                           //手机号
        String vehicleModel = params.get("vehicle_model");              //车辆类型
        String vehicleColor = params.get("vehicle_color");              //车辆颜色
        String ownerName = params.get("owner_name");                    //所有人姓名
        String uploadMessage = "driving_license_url:"+driverLicenseImage + ",driver_id_url:" + driverIdCardImage + ",drivers_license_url:" + mDrivingLicense;             //上传格式
        params.put("uploadMessage",uploadMessage);
        String licensingOfTime = params.get("licensingOfTime");         //驾驶证领证时间
        String vin = params.get("vin");                                 //车架号
        String driverLicense = params.get("drivers_license");           //驾驶证号码
        String username = params.get("username");                       //注册人姓名

        if(TextUtils.isEmpty(vehicleNo)){
            msg = "车牌号不能为空";
        }else if(TextUtils.isEmpty(vehicleModel)){
            msg = "车辆类型不能为空";
        }else if(TextUtils.isEmpty(vehicleColor)){
            msg = "车辆颜色不能为空";
        } else if(TextUtils.isEmpty(ownerName)){
            msg = "所有人姓名不能为空";
        }else if(TextUtils.isEmpty(uploadMessage)){
            msg = "上传图片信息不能为空";
        }else if(TextUtils.isEmpty(vin)){
            msg = "车架号不能为空";
        }else if(TextUtils.isEmpty(mDrivingLicense)){
            msg = "行驶证没有上传";
        }

        if(!TextUtils.isEmpty(msg)){
            mView.showMessageDialog(msg);
        }else{
            for(Map.Entry<String,String> entry : params.entrySet()){
                AppLogger.e("key : " + entry.getKey() + " ,value : " + entry.getValue());
            }

            Call<String> register = mModule.register(params);
            register.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String body = response.body();
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    String status = jsonObject.getString("status");
                    if("VALID".equals(status)){
                        mView.showSubmitSuccessDialog();
                    }else if(Constants.STATUS_FAILURE.equals(status)){
                        mView.showMessageDialog("注册失败。请联系客服");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mView.showMessageDialog("服务器异常");
                }
            });
        }
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onFinish(String status, String msg) {
        mView.hiddenUploadPictureProgressDialog();

        if(Constants.STATUS_SUCCESS.equals(status)){
            String[] messages = msg.split(";");
            int imageType = Integer.valueOf(messages[0]);
            String imageName = messages[1];
            String imagePath = messages[2];
            Bitmap bitmap = getBitmapFromPath(imagePath);
            if(bitmap == null) {        //找不到本地图片
                mView.showMessageDialog("无法获取本地图片");
                return;
            }

            switch(imageType){
                case Constants.DRIVING_LICENSE:
                    mDrivingLicense = imageName;
                    mView.setDrivingLicenseImage(bitmap);
                    mView.showMessageDialog("上传图片成功");
                    break;
            }
        }else if(Constants.STATUS_FAILURE.equals(status)){
            mView.showMessageDialog("" + msg);
        }
    }

    @Override
    public void registerSuccess() {
        mView.showSubmitSuccessDialog();
    }

    @Override
    public void registerFailed(String msg) {
        mView.showMessageDialog(msg);
    }

    private Bitmap getBitmapFromPath(String path){
        Bitmap bitmap = null;
        AppLogger.e("path : " + path);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(path, options);

        int realWidth = options.outWidth;
        int realHeight = options.outHeight;
        int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / 100);
        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(path, options);

        return bitmap;
    }
}