package com.sjsm.detectionmanagement.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.sjsm.detectionmanagement.CrystalApplication;
import com.sjsm.detectionmanagement.R;


/**
 * Created by zhaohui on 16/7/15.
 * UIHelper
 */
public abstract class UIHelper {

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;

    private static Typeface fontAwesome;

    public static Typeface getFontAwesome(Context context) {
        if (fontAwesome == null)
            fontAwesome = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

        return fontAwesome;
    }

    // 应用当前主题
    public static void applyCurrentTheme(Activity activity) {
        switch (getCurrentTheme()) {
            case THEME_DARK: {
                activity.setTheme(R.style.AppBaseThemeDark);
                break;
            }
            case THEME_LIGHT:
            default:
                activity.setTheme(R.style.AppBaseTheme);
                break;
        }
    }

    // 获取当前主题
    private static int getCurrentTheme() {
        Context appContext = CrystalApplication.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        return prefs.getInt("pref_theme", THEME_LIGHT);
    }

    // 设置当前主题
    public static void setCurrentTheme(int theme) {
        Context appContext = CrystalApplication.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        prefs.edit().putInt("pref_theme", theme).apply();
    }

    // 判断是否为夜间模式
    public static boolean isNightMode() {
        return getCurrentTheme() == THEME_DARK;
    }

    public static float dpToPixels(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static float spToPixels(Context context, float sp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }


    private static TypedArray getTypedArray(Context context, int attrId) {
        int[] attrs = new int[]{attrId};
        return context.obtainStyledAttributes(attrs);
    }

    // resourceType:drawable
    public static int getResourceId(Context context, String resourceName, String resourceType) {
        Resources res = context.getResources();
        String packageName = context.getPackageName();
        int resourceId = res.getIdentifier(resourceName, resourceType, packageName);
        return resourceId;
    }

    public static int getStyledColor(Context context, int attrId) {
        TypedArray ta = getTypedArray(context, attrId);
        int color = ta.getColor(0, 0);
        ta.recycle();

        return color;
    }

    public static Drawable getStyledDrawable(Context context, int attrId) {
        TypedArray ta = getTypedArray(context, attrId);
        Drawable drawable = ta.getDrawable(0);
        ta.recycle();

        return drawable;
    }

    public static boolean getStyledBoolean(Context context, int attrId) {
        TypedArray ta = getTypedArray(context, attrId);
        boolean bool = ta.getBoolean(0, false);
        ta.recycle();

        return bool;
    }

    public static float getStyledFloat(Context context, int attrId) {
        TypedArray ta = getTypedArray(context, attrId);
        float f = ta.getFloat(0, 0);
        ta.recycle();

        return f;
    }

    public static int getStyleResource(Context context, int attrId) {
        TypedArray ta = getTypedArray(context, attrId);
        int resourceId = ta.getResourceId(0, -1);
        ta.recycle();

        return resourceId;
    }

}
