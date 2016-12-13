package com.sjsm.detectionmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.sjsm.detectionmanagement.CrystalApplication;

import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * 使用AsyncHttpClient调用服务端WebApi工具类
 */
public class AsynHttpUtils {
    private static final String TAG = "TEST";
    private static String BASE_URL = "http://192.168.88.100:82";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void doPostWithJsonParams(Context context, String url, String strJson, ResponseHandlerInterface responseHandler) throws Exception {
        ByteArrayEntity entity = null;
        entity = new ByteArrayEntity(strJson.getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public static void doPostWithRequestParams(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Context context = CrystalApplication.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String strUrl = prefs.getString("pref_servel_url", "");
        BASE_URL = strUrl+"/api/";
        return BASE_URL + relativeUrl;
    }
}
