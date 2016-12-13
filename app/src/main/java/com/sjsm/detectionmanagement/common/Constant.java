package com.sjsm.detectionmanagement.common;

/**
 * Created by zhaohui on 16/11/1.
 */

public class Constant {
    public static final int REQUEST_SCAN = 999;
    public static final int REQUEST_CHANGE = 998;

    public static final int SHOW_DATAPICK = 1;
    public static final int DATE_DIALOG_ID = 3;

    public static final String STR_FRAGMENT_INDEX = "我的应用";
    public static final String STR_FRAGMENT_ME = "我";

    // 刷新加载成功
    public static final int SUCCEED = 0;
    // 刷新加载失败
    public static final int FAIL = 1;
    // 刷新加载完成
    public static final int FINISHED = 2;

    // frament
    public static final int SIGN_FRAGMENT_APPS = 0x01 << 1;
    public static final int SIGN_FRAGMENT_SET = 0x01 << 2;

    // 消息提醒
    public static final int SHOWTOAST = 0x01 << 3;
    public static final int SHOWMESSAGE = 0x01 << 4;

    public static final int SHOWPAGE = 0x01 << 5;
    public static final int BINDLISTVIEW = 0x01 << 6;
    public static final int CLEARLISTVIEW = 0x01 << 7;

    public static final int CALLBACKCODE = 0x01 << 8;
    public static final int GOBACK = 0x01 << 9;

    public static final int APPENDVIEW = 0x01 << 10;

    public static final int BINDSPINNER = 0x01 << 11;

    // 加载更多
    public static final int LOADMORE = 0x01 << 12;
    // 下拉刷新
    public static final int REFRESHDATA = 0x01 << 17;

    public static final int REFRESH = 0x01 << 13;
    public static final int CANCEL = 0x01 << 14;
    public static final int UPDATEACCOUNT = 0x01 << 15;
    public static final int DEPTSELECTCALLBACK = 0x01 << 16;
    public static final int INITPAGE = 0x01 << 18;
}
