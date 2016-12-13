package com.sjsm.detectionmanagement;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

/**
 * Created by zhaohui on 16/9/21.
 */
public class CrystalApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        context=null;
    }
}
