package com.sjsm.detectionmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.sjsm.detectionmanagement.common.Constant;
import com.sjsm.detectionmanagement.fragment.BaseFragment;
import com.sjsm.detectionmanagement.fragment.TabMessage;
import com.sjsm.detectionmanagement.utils.ShowMsgUtils;
import com.sjsm.detectionmanagement.utils.SpUtils;

public class MainActivity extends BaseActivity {
    public static String Pid = "";
    private Context         mContext;
    public  FragmentManager mFragmentManager;
    private       FragmentTransaction mFragmentTransaction = null;
    public static String              currFagTag           = "";
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mFragmentManager = getSupportFragmentManager();
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                String tag = TabMessage.get(tabId);
                isLogin(tag);

            }
        });
    }


    private void isLogin(String tag) {
//        if (tag.equals(Constant.STR_FRAGMENT_WORK)&&"".equals(SpUtils.get(mContext, "UserId", ""))) {
//            ShowMsgUtils.ShowToast(mContext,"请先登录");
////            Intent intent = new Intent(mContext, LoginActivity.class);
////            mContext.startActivity(intent);
//        }else{
//            setTabSection(tag);
//        }
    }

    public void setTabSection(String tag) {
        try {
            if (!TextUtils.equals(tag, currFagTag)) {
                ensureTransaction();
                if (currFagTag != null && !currFagTag.equals("")) {
                    detachFragment(getFragment(currFagTag));
                }
                attachFragment(R.id.fragment_panel, getFragment(tag), tag);
                commitTransaction();
            } else {
                return;
            }
        }catch (Exception e){
            Log.d("Tag",e.getMessage());
        }

    }

    private void detachFragment(Fragment f) {
        if (f != null && !f.isDetached()) {
            mFragmentTransaction.detach(f);
        }
    }

    private void attachFragment(int layoutId, Fragment f, String tag) {
        if (f != null) {
            if (f.isDetached()) {
                mFragmentTransaction.attach(f);
            } else if (!f.isAdded()) {
                mFragmentTransaction.add(layoutId, f, tag);
            }
        }
    }

    public Fragment getFragment(String tag) {
        BaseFragment fragment = (BaseFragment) mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = BaseFragment.newInstance(tag);
        }
        return fragment;
    }

    /**
     * 创建transaction
     */
    private void ensureTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            //mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
    }

    /**
     * 提交section
     */
    private void commitTransaction() {
        if (mFragmentTransaction != null) {
            mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        currFagTag = "";
    }
    public void setSelectedFragment(int position){
        bottomBar.selectTabAtPosition(position);
    }
}
