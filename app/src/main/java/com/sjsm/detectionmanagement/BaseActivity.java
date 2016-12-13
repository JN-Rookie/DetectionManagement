package com.sjsm.detectionmanagement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sjsm.detectionmanagement.utils.ColorHelper;
import com.sjsm.detectionmanagement.utils.UIHelper;


/**
 * Created by zhaohui on 16/7/15.
 */
abstract public class BaseActivity extends AppCompatActivity implements Thread.UncaughtExceptionHandler {

    Thread.UncaughtExceptionHandler androidExceptionHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.applyCurrentTheme(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        androidExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        setTranslucentStatus();
    }

    private void setTranslucentStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            //tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color.red);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.custom_blue);
            //getWindow().getDecorView().setFitsSystemWindows(true);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            //tintManager.setNavigationBarTintResource(R.color.blue_800);
            Log.i("ss","");
        }
    }

    protected void setupSupportActionBar(boolean homeButtonEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) return;

        TextView txtTitle = (TextView) findViewById(R.id.toolbar_title);
        if (txtTitle != null) {
            txtTitle.setText(getTitle());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(UIHelper.dpToPixels(this, 2));
        }

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayShowTitleEnabled(false);
        if (homeButtonEnabled)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void setupActionBarColor(int color) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;

        ColorDrawable drawable = new ColorDrawable(color);
        actionBar.setBackgroundDrawable(drawable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int darkerColor = ColorHelper.mixColors(color, Color.BLACK, 0.75f);
            getWindow().setStatusBarColor(darkerColor);
        }
    }

    protected void setupActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setTitle(title);
    }

    protected void setupActionBarNavigateionIcon(int resId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) return;

        toolbar.setNavigationIcon(resId);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (androidExceptionHandler != null) {
            androidExceptionHandler.uncaughtException(thread, ex);
        } else {
            System.exit(1);
        }
    }
}
