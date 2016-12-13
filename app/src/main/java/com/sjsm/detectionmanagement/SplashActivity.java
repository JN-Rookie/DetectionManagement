package com.sjsm.detectionmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sjsm.detectionmanagement.utils.PhoneUtils;
import com.sjsm.detectionmanagement.utils.SpUtils;

public class SplashActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
        boolean isTablet = PhoneUtils.isTabletBySize(mContext);
        SpUtils.put(mContext, "IsTablet", isTablet);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                if (SpUtils.get(mContext, "ServerUrl", "").equals("")) {
                    intent.setClass(SplashActivity.this, SettingActivity.class);
                } else {
                    // 2. 判断用户名和密码是否存在，如果存在的话 直接进入main 页面
                    String userId = SpUtils.get(mContext, "UserId", "").toString();
                    String password = SpUtils.get(mContext, "Password", "").toString();
                    if (!userId.equals("") && !password.equals("")) {
                        intent.setClass(mContext, MainActivity.class);
                    } else {
                        intent.setClass(SplashActivity.this, LoginActivity.class);
                    }
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }


}
