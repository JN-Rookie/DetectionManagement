package com.sjsm.detectionmanagement.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.sjsm.detectionmanagement.common.Constant;


/**
 * Created by zhaohui on 16/11/9.
 */

public abstract class BaseFragment extends Fragment {
    public Context mContext;

    public static BaseFragment newInstance(String tag) {
        return newInstance(tag, null);
    }

    public static BaseFragment newInstance(String tag, Bundle bundle) {
        BaseFragment fragment = null;
        if (TextUtils.equals(tag, Constant.STR_FRAGMENT_INDEX)) {
            fragment = new IndexFragment();
        } else if (TextUtils.equals(tag, Constant.STR_FRAGMENT_ME)) {
            fragment = new MeFragment();
        }
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
