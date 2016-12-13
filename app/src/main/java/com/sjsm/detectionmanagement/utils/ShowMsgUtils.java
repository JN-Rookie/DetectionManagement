package com.sjsm.detectionmanagement.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by zhaohui on 16/7/28.
 */
public class ShowMsgUtils {
    static Toast toast;
    public static void ShowToast(Context mContext, String msg) {
        if (toast == null){
            toast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
        }
        toast.setText(msg);
        toast.show();
    }

    public static void ShowMessage(Context mContext, String msg) {
        //可以使用自定义 R.style.MyDialogTheme
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setTitle("提示");
        alertBuilder.setMessage(msg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.show();
    }

    public static void ShowMessage(Context mContext, String msg, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setTitle("提示");
        alertBuilder.setMessage(msg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("确定", listener);
        alertBuilder.show();
    }

    public static void ShowMessageWithCancelBtn(Context mContext, String msg,
                                                DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setTitle("提示");
        alertBuilder.setMessage(msg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("确定", listener);
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.show();
    }


}
