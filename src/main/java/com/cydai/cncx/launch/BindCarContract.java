package com.cydai.cncx.launch;

import android.graphics.Bitmap;

import java.util.Map;

import retrofit2.Call;
import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public class BindCarContract {
    interface IBindCarView {
        void showSubmitSuccessDialog();

        void showUploadPictureProgressDialog();

        void hiddenUploadPictureProgressDialog();

        void showMessageDialog(String msg);

        void setDrivingLicenseImage(Bitmap bitmap);
    }

    interface IBindCarPresenter{

        void uploadPicture(int type,String pic);

        void register(Map<String,String> params,String driverLicenseImage,String driverIdCardImage);
    }

    interface IBindCarModule{
        Call<String> register(Map<String,String> params);

        Subscription uploadPicture(int type,String pic);
    }
}