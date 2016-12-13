package com.sjsm.detectionmanagement.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by zhaohui on 16/8/18.
 */
public class SystemUtils {
    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        String packName = getPackageName(context);
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(packName, 0).versionCode;
        } catch (Exception e) {
            Log.e("版本号获取异常", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String packName = getPackageName(context);
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(packName, 0).versionName;
        } catch (Exception e) {
            Log.e("版本号获取异常", e.getMessage());
        }
        return verName;
    }

    public static String getMenuFullPath(Context context, String folder, String activityName) {
        return getPackageName(context) + "." + folder + "." + activityName;
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


    public static String getSHA1(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
