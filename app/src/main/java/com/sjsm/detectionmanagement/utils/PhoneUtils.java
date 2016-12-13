package com.sjsm.detectionmanagement.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by zhaohui on 16/8/18.
 */
public class PhoneUtils {
    /**
     * 获取版本名称
     *
     * @param mContext 数据上下文
     * @return 版本号：v1.0
     */
    public static String getVersionName(Context mContext) {
        String verName = "1.0";
        try {
            verName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        verName = "版本号：v" + verName;
        return verName;
    }

    /**
     * 获取版权信息
     *
     * @param copyright
     */
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

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getMenuFullPath(Context context, String activityName) {
        return getPackageName(context) + ".activitys." + activityName;
    }

    /**
     * 获取设备IMEI唯一标识
     *
     * @param context
     * @return
     */
    public static String getDeviceIMEI(Context context) {
        String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return imei;
    }

    /**
     * 判断设备是手机还是平板
     *
     * @param context
     * @return 平板返回true，手机返回false
     */
    public static boolean isTablet(Context context) {
        if (isTabletBySize(context) && isTabletByCall(context)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前设备是手机还是平板，代码来自Google I/O App for Android
     *
     * @param context
     * @return 平板返回true，手机返回false
     */
    public static boolean isTabletBySize(Context context) {
        boolean result = (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        return result;
    }

    /**
     * @param context
     * @return 平板返回true，手机返回false
     */
    public static boolean isTabletByCall(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephony.getPhoneType();
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            return true;
        } else {
            return false;
        }
    }

}
