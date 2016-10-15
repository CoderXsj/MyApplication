package com.cydai.cncx.launch;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Map;

import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public class AuthenticationContract {

    interface IAuthenticationView {

        void showUploadPictureProgressDialog();

        void hiddenUploadPictureProgressDialog();

        void showMessageDialog(String msg);

        void setDriverIdCardImage(Bitmap driverIdCardImage);

        void setDriverLicenseImage(Bitmap driverLicenseImage);

        void jump2BindCar(@NonNull Map<String,String> params);
    }

    interface IAuthenticationPresenter{

        void uploadPicture(int type,String pic);

        void validateData(String name,String driverLicense,String date);
    }

    interface IAuthenticationModule{

        Subscription uploadPicture(int type,String pic);

    }
}