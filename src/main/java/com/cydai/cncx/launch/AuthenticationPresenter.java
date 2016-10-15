package com.cydai.cncx.launch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.cydai.cncx.common.BasePresenter;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.network.UploadFileRequestBody;
import com.cydai.cncx.util.AppLogger;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public class AuthenticationPresenter extends BasePresenter<AuthenticationContract.IAuthenticationView,AuthenticationContract.IAuthenticationModule>
        implements AuthenticationContract.IAuthenticationPresenter,
        UploadFileRequestBody.ProgressListener
{
    private String mDriverIdCardImage;
    private String mDriverLicenseImage;

    public AuthenticationPresenter(AuthenticationContract.IAuthenticationView mView, AuthenticationContract.IAuthenticationModule mModule) {
        super(mView, mModule);
    }

    @Override
    public void uploadPicture(int type,String pic) {
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
    public void validateData(String name, String driverLicense, String date) {
        String msg = null;

        String regexName = "[\u4E00-\u9FA5]+";
        String regexDriverLicense = "[0-9]{15}|[0-9]{18}";
        if(TextUtils.isEmpty(name)){
            msg = "姓名不能为空";
        }else if(!name.matches(regexName)){
            msg = "姓名请输入汉字";
        }else if(TextUtils.isEmpty(driverLicense)){
            msg = "请输入驾驶证号码";
        } else if(driverLicense.matches(regexDriverLicense)){
            msg = "驾驶证号码输入有误";
        }else if(TextUtils.isEmpty(date)){
            msg = "领证时间不能为空";
        }else if(TextUtils.isEmpty(mDriverIdCardImage)){
            msg = "请上传您的身份证号码";
        }else if(TextUtils.isEmpty(mDriverLicenseImage)){
            msg = "请上传您的驾驶证";
        }

        mView.hiddenUploadPictureProgressDialog();
        if(!TextUtils.isEmpty(msg)){
            mView.showMessageDialog(msg);
        }else{
            Map<String,String> params = new HashMap<>();
            params.put(Constants.INTENT_DRIVER_LICENSE_IMAGE, mDriverLicenseImage);
            params.put(Constants.INTENT_DRIVER_ID_CARD_IMAGE, mDriverIdCardImage);
            params.put(Constants.INTENT_DRIVER_NAME, name);
            params.put(Constants.INTENT_DRIVER_LICENSE, driverLicense);
            params.put(Constants.INTENT_DRIVER_DATE, date);
            mView.jump2BindCar(params);
        }
    }

    @Override
    public void onProgress(int progress) {
        AppLogger.e("" + progress);
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

            mView.showMessageDialog("上传图片成功");
            switch(imageType){
                case Constants.DRIVER_ID_CARD:
                    mDriverIdCardImage = imageName;
                    mView.setDriverIdCardImage(bitmap);
                    break;
                case Constants.DRIVER_LICENSE:
                    mDriverLicenseImage = imageName;
                    mView.setDriverLicenseImage(bitmap);
                    break;
            }
        }else if(Constants.STATUS_FAILURE.equals(status)){
            mView.showMessageDialog("" + msg);
        }
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