package com.cydai.cncx.launch;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cydai.cncx.common.BaseActivity;
import com.cydai.cncx.common.Constants;
import com.cydai.cncx.util.DialogCreateFactory;
import com.cydai.cncx.widget.dialog.MaterialDialog;
import com.example.apple.cjyc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.tencent.bugly.crashreport.inner.InnerAPI.context;


/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public class BindCarActivity extends BaseActivity implements BindCarContract.IBindCarView{
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.iv_driving_license) ImageView mIvDrivingLicense;

    @BindView(R.id.et_license_plate_number) EditText mEtLicensePlateNumber;
    @BindView(R.id.et_vehicle_identification_number) EditText mEtVehicleIdentificationNumber;
    @BindView(R.id.et_car_owner) EditText mEtCarOwner;
    @BindView(R.id.et_car_type) EditText mEtCarType;
    @BindView(R.id.et_car_color) EditText mEtCarColor;

    private Dialog customProgressDialog;
    private BindCarPresenter mPresenter;

    private String mDriverLicenseImage;
    private String mDriverIdCardImage;
    private String mDriverName;
    private String mDriverLicense;
    private String mDriverDate;
    private String mDriverMobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_car);
        ButterKnife.bind(this);

        initVariables();
    }

    @Override
    public void initVariables() {
        Module module = new Module();
        mPresenter = new BindCarPresenter(this,module);
        module.setProgressListener(mPresenter);
        module.setRegisterDriverCallback(mPresenter);

        mTvTitle.setText("绑定车辆");

        Intent intent = getIntent();
        mDriverLicenseImage = intent.getStringExtra(Constants.INTENT_DRIVER_LICENSE_IMAGE);
        mDriverIdCardImage = intent.getStringExtra(Constants.INTENT_DRIVER_ID_CARD_IMAGE);
        mDriverName = intent.getStringExtra(Constants.INTENT_DRIVER_NAME);
        mDriverLicense = intent.getStringExtra(Constants.INTENT_DRIVER_LICENSE);
        mDriverDate = intent.getStringExtra(Constants.INTENT_DRIVER_DATE);
        mDriverMobile = intent.getStringExtra(Constants.INTENT_DRIVER_MOBILE);
    }

    @Override
    public void showSubmitSuccessDialog() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_register, null, false);
        DialogCreateFactory.createNoButtonDialog(this,"",contentView).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishAll();
                jump2Activity(LoginActivity.class,R.anim.anim_left_in,R.anim.anim_left_out);
            }
        },2000);
    }

    @Override
    public void showUploadPictureProgressDialog() {
        customProgressDialog = DialogCreateFactory.createCustomProgressDialog(this);
        customProgressDialog.show();
    }

    @Override
    public void hiddenUploadPictureProgressDialog() {
        if(customProgressDialog != null) {
            customProgressDialog.hide();
            customProgressDialog = null;
        }
    }

    @Override
    public void showMessageDialog(String msg) {
        DialogCreateFactory.createAlertDialog(this,"消息","msg :" + msg).show();
    }

    @Override
    public void setDrivingLicenseImage(Bitmap bitmap) {
        mIvDrivingLicense.setImageBitmap(bitmap);
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
                        case Constants.REQUEST_DRIVING_LICENSE:         //id card
                            mPresenter.uploadPicture(Constants.DRIVING_LICENSE,path);
                            break;
                    }
                    }
                });
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_upload_picture)
    public void uploadDrivingrLicense(View view){
        PhotoPicker.builder()
            .setPhotoCount(1)
            .setShowCamera(true)
            .setShowGif(true)
            .setPreviewEnabled(false)
            .start(this, Constants.REQUEST_DRIVING_LICENSE);
    }

    @OnClick(R.id.btn_register)
    public void register(View view){
        Map<String,String> params = new HashMap<>();
        params.put("vehicle_no",mEtLicensePlateNumber.getText().toString());
        params.put("mobile",mDriverMobile);
        params.put("vehicle_model",mEtCarType.getText().toString());
        params.put("vehicle_color",mEtCarColor.getText().toString());
        params.put("owner_name",mEtCarOwner.getText().toString());
//        params.put("uploadMessage","");
        params.put("licensingOfTime",mDriverDate);
        params.put("vin",mEtVehicleIdentificationNumber.getText().toString());
        params.put("drivers_license",mDriverLicense);
        params.put("username",mDriverName);

        mPresenter.register(params,mDriverLicenseImage,mDriverIdCardImage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnsubscription();
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
}