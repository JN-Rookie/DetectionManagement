package com.sjsm.detectionmanagement.fragment;


import com.sjsm.detectionmanagement.R;
import com.sjsm.detectionmanagement.common.Constant;

/**
 * Created by zhaohui on 16/11/9.
 */

public class TabMessage {
    public static String get(int menuItemId) {
        String message = "";
        switch (menuItemId) {
            case R.id.tab_index:
                message = Constant.STR_FRAGMENT_INDEX;
                break;
            case R.id.tab_me:
                message = Constant.STR_FRAGMENT_ME;
                break;
        }
        return message;
    }
}
