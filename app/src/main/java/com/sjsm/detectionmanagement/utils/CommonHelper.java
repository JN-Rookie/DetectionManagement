package com.sjsm.detectionmanagement.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhaohui on 16/8/17.
 */
public class CommonHelper {
    // 删除文件夹及里面的文件
    public static boolean deleteFoder(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                if (files != null) {
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        deleteFoder(files[i]); // 把每个文件 用这个方法进行迭代
                    }
                }
            }
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                return false;
            }
        }
        return true;
    }

    public static void refreshSystemMediaScanDataBase(Context context, String docPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(docPath));
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    // 获取当前系统时间
    public static String getCurTime() {
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return dateFormate.format(date);
    }

    // 获取当前系统日期
    public static String getDate() {
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return dateFormate.format(date);
    }

    /**
     * @param begin       开始时间
     * @param end         当前时间
     * @return 两个时间点之间的差，单位小时
     */
    public static long compareTime(String begin, String end) {
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            Date date1 = dateFormate.parse(begin);
            Date date2 = dateFormate.parse(end);
            diff = date2.getTime() - date1.getTime();
            // int min= diff/(1000 * 60 ); //分钟
            //hours = diff / (1000 * 60 * 60);// 小时
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }


    /***
     * 弹出日期对话框
     *
     * @param context
     * @param editText
     */
    public static DatePickerDialog pickDateDialog(Context context, final EditText editText) {
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog mDialog = new DatePickerDialog(context, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);
                editText.setText(dateFormatter.format(newDate.getTime()));
                System.out.println(year + "," + month + "," + day);
            }
        });
        //取消按钮，如果不需要直接不设置即可
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("BUTTON_NEGATIVE~~");
                editText.setText("");
                dialog.dismiss();
            }
        });

        mDialog.show();
        return mDialog;
    }

    /***
     * 禁止弹出键盘
     *
     * @param editText
     */
    public static void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static String getVersionName(Context mContext) {
        String verName = "1.0";
        try {
            verName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        verName = "委托验样：v" + verName;
        return verName;
    }

    public static void getCopyright(TextView copyright) {
        int birthdayYear = 2016;
        // 获取当前年份，如果和birthday相等不做处理 不等则 birthday year-current year
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        if (currentYear == birthdayYear) {
            copyright.setText("Copyright @ " + birthdayYear + " 济南水晶数码.");
        } else {
            copyright.setText("Copyright @ " + birthdayYear + "-" + currentYear + " 济南水晶数码.");
        }
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
