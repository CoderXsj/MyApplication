package com.cydai.cncx.launch;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cydai.cncx.common.BaseActivity;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.util.AppLogger;
import com.cydai.cncx.util.DialogCreateFactory;
import com.cydai.cncx.widget.dialog.MaterialDialog;
import com.cydai.cncx.widget.dialog.NumberPickerDialog;
import com.example.apple.cjyc.R;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 *
 * 车主认证界面
 */
public class AuthenticationActivity extends BaseActivity implements AuthenticationContract.IAuthenticationView,NumberPickerDialog.OnConfirmListener{
    private AuthenticationPresenter mPresenter;
    private Dialog customProgressDialog;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.iv_idcard) ImageView mIvIdCard;
    @BindView(R.id.iv_driver_license) ImageView mIvDriverLicense;

    @BindView(R.id.et_name) EditText mEtName;
    @BindView(R.id.et_driver_license) EditText mEtDriverLicense;
    @BindView(R.id.tv_license_date) TextView mTvLicenseDate;

    private String mLicenseDate;
    private String mDriverMobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenication);
        ButterKnife.bind(this);

        initVariables();
    }

    @Override
    public void initVariables() {
        Module module = new Module();
        mPresenter = new AuthenticationPresenter(this,module);
        module.setProgressListener(mPresenter);

        mTvTitle.setText("车主认证");

        mDriverMobile = getIntent().getStringExtra(Constants.INTENT_DRIVER_MOBILE);
        AppLogger.e("tag : " + mDriverMobile);
    }

    @OnClick(R.id.btn_back)
    public void back(){
        MaterialDialog chooseDialog = DialogCreateFactory.createChooseDialog(this, "请确认", "您是否要放弃您已填写的资料", new DialogCreateFactory.OnClickListener() {
            @Override
            public void confirm(MaterialDialog dialog) {
                finish();
                overridePendingTransition(R.anim.anim_left_in,R.anim.anim_left_out);
            }

            @Override
            public void cancel(MaterialDialog dialog) {
                dialog.dismiss();
            }
        });
        chooseDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(customProgressDialog != null) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }

        mPresenter.onUnsubscription();
    }

    @Override
    public void showUploadPictureProgressDialog() {
        customProgressDialog = DialogCreateFactory.createCustomProgressDialog(this);
        customProgressDialog.show();
    }

    @Override
    public void hiddenUploadPictureProgressDialog() {
        if(customProgressDialog != null) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }
    }

    @Override
    public void showMessageDialog(String msg) {
        DialogCreateFactory.createAlertDialog(this,"消息","msg :" + msg).show();
    }

    @Override
    public void setDriverIdCardImage(Bitmap driverIdCardImage) {
        mIvIdCard.setImageBitmap(driverIdCardImage);
    }

    @Override
    public void setDriverLicenseImage(Bitmap driverLicenseImage) {
        mIvDriverLicense.setImageBitmap(driverLicenseImage);
    }

    @Override
    public void jump2BindCar(@NonNull Map<String,String> params) {
        params.put(Constants.INTENT_DRIVER_MOBILE, mDriverMobile);
        jump2Activity(BindCarActivity.class,R.anim.anim_left_in,R.anim.anim_left_out,params);
    }

    @OnClick(R.id.bt_next_step)
    public void onNext() {
        mPresenter.validateData(mEtName.getText().toString(),mEtDriverLicense.getText().toString(),mLicenseDate);
    }

    @OnClick(R.id.btn_upload_idcard)
    public void uploadIdCard(View view){
        PhotoPicker.builder()
            .setPhotoCount(1)
            .setShowCamera(true)
            .setShowGif(true)
            .setPreviewEnabled(false)
            .start(this, Constants.REQUEST_ID_CARD);
    }

    @OnClick(R.id.btn_upload_driver_license)
    public void uploadDriverLicense(View view){
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, Constants.REQUEST_DRIVER_LICENSE);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        //如果是请求
        if (resultCode == RESULT_OK ) {
            if (data != null) {
                Observable.create(new Observable.OnSubscribe<ArrayList<String>>() {
                    @Override
                    public void call(Subscriber<? super ArrayList<String>> subscriber) {
                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    subscriber.onNext(photos);
                    }
                }).filter(new Func1<ArrayList<String>, Boolean>() {
                    @Override
                    public Boolean call(ArrayList<String> paths) {
                    return paths != null && paths.size() >= 1;
                    }
                }).map(new Func1<ArrayList<String>, String>() {
                    @Override
                    public String call(ArrayList<String> paths) {
                    return paths.get(0);
                    }
                }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String path) {
                    switch(requestCode){
                        case Constants.REQUEST_ID_CARD:         //id card
                            mPresenter.uploadPicture(Constants.DRIVER_ID_CARD,path);
                            break;
                        case Constants.REQUEST_DRIVER_LICENSE:  //驾驶证
                            mPresenter.uploadPicture(Constants.DRIVER_LICENSE,path);
                            break;
                    }
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.tv_license_date)
    public void showChooseDriverLicenseDateDialog(){
        NumberPickerDialog dialog = new NumberPickerDialog(this);
        dialog.setOnConfirmListener(this);
        dialog.show();
    }

    @Override
    public void onConfirm(String year, String month, String day) {
        mTvLicenseDate.setText(year + month + day);

        mLicenseDate = year + month + day;
    }
}