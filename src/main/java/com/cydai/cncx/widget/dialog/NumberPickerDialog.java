package com.cydai.cncx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cydai.cncx.widget.NumberPickerView;
import com.example.apple.cjyc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberPickerDialog extends Dialog implements View.OnClickListener,NumberPickerView.OnValueChangeListener{
    private Context mContext;
    @BindView(R.id.npv_year) NumberPickerView mYearNumberView;
    @BindView(R.id.npv_month) NumberPickerView mMonthNumberView;
    @BindView(R.id.npv_day) NumberPickerView mDayNumberView;

    private List<String> mYears = new ArrayList<>();
    private List<String> mMonths = new ArrayList<>();
    private List<String> mDays = new ArrayList<>();

    public NumberPickerDialog(Context context){
        super(context, R.style.number_picker_dialog);
        this.mContext = context;
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }
    private OnConfirmListener onConfirmListener;

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        String year = mYearNumberView.getContentByCurrValue();
        String month = picker.getContentByCurrValue();

        year = year.substring(0,year.length() - 1);
        month = month.substring(0,month.length() - 1);

        String[] day = createDay(Integer.valueOf(year), Integer.valueOf(month));
        mDayNumberView.refreshByNewDisplayedValues(day);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_number_pciker);
        ButterKnife.bind(this);

        initDialog();
        initListener();

        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);

        String[] years = createYear();
        mYearNumberView.refreshByNewDisplayedValues(years);
        mMonthNumberView.refreshByNewDisplayedValues(createMonth());
        mDayNumberView.refreshByNewDisplayedValues(createDay(year,month));
        mYearNumberView.setValue(years.length - 1);
        mMonthNumberView.setValue(month);
        mDayNumberView.setValue(day - 1);
    }

    private void initListener() {
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        mMonthNumberView.setOnValueChangedListener(this);
    }

    public interface OnConfirmListener{
        void onConfirm(String year, String month,String day);
    }

    private void initDialog() {
        //初始化Dialog的位置
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        window.setAttributes(lp);
        setCancelable(true);
    }

    private String[] createYear(){
        mYears.clear();
        Calendar instance = Calendar.getInstance();
        int nowYear = instance.get(Calendar.YEAR);

        for(int i = 1970;i <= nowYear;i++){
            mYears.add(i + "年");
        }

        String[] years = mYears.toArray(new String[]{});
        return years;
    }

    private String[] createMonth(){
        mMonths.clear();
        for(int i = 1;i <= 12;i++){
            mMonths.add(i + "月");
        }

        String[] months = mMonths.toArray(new String[]{});
        return months;
    }

    private String[] createDay(int year,int month){
        DecimalFormat decimalFormat = new DecimalFormat("00");

        mDays.clear();
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR,year);
        instance.set(Calendar.MONTH,month - 1);

        int monthDay = instance.getActualMaximum(Calendar.DATE);
        for(int i = 1;i <= monthDay;i++){
            mDays.add(decimalFormat.format(i) + "日");
        }

        String[] days = mDays.toArray(new String[]{});
        return days;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                if(onConfirmListener != null){
                    onConfirmListener.onConfirm(mYearNumberView.getContentByCurrValue(),mMonthNumberView.getContentByCurrValue(),mDayNumberView.getContentByCurrValue());
                }
                dismiss();
                break;
        }
    }
}