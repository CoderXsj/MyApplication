package com.cydai.cncx.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.cydai.cncx.widget.dialog.FoldingCirclesDrawable;
import com.cydai.cncx.widget.dialog.MaterialDialog;

public class DialogCreateFactory {

    public interface OnClickListener{
        void confirm(MaterialDialog dialog);

        void cancel(MaterialDialog dialog);
    }

    public static Dialog createCustomProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(context).build());
        progressDialog.setCancelable(false);

        return progressDialog;
    }

    public static MaterialDialog createChooseDialog(Context context, String title, String message, final OnClickListener listener){
        final MaterialDialog mMaterialDialog = new MaterialDialog(context)
                .setTitle(title)
                .setMessage(message);

        mMaterialDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.confirm(mMaterialDialog);
            }
        })
        .setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancel(mMaterialDialog);
            }
        });

        return mMaterialDialog;
    }


    public static MaterialDialog createAlertDialog(Context context,String title,String message){
        final MaterialDialog mMaterialDialog = new MaterialDialog(context)
                .setTitle(title)
                .setMessage(message);

        mMaterialDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mMaterialDialog.dismiss();
            }
        });

        return mMaterialDialog;
    }

    public static MaterialDialog createNoButtonDialog(Context context,String title,View contentView){
        final MaterialDialog mMaterialDialog = new MaterialDialog(context)
                .setContentView(contentView);

        return mMaterialDialog;
    }
}