package com.sjsm.detectionmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.preference.PreferenceManager;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaohui on 16/7/25.
 */
public class HttpUtils {

    public static HttpCallBack mHttpCallBack;

    public interface HttpCallBack {
        void onRequestComplete(int requestCode, String requestResult);
    }

    public interface  CallBack{
        void onRequestComplete(String requestResult);
    }

    public static void setHttpCallBack(HttpCallBack httpCallBack) {
        HttpUtils.mHttpCallBack = httpCallBack;
    }

    public static void doPostAsync(final Context mContext, final int requestCode, final String mController,
                                   final String mMethod, final Map<String, Object> mParams) {
        new Thread(new Runnable() {
            public void run() {
                String strResult = "";
                try {
                    strResult = doPost(mContext, mController, mMethod, mParams);
                } catch (Exception e) {
                    e.printStackTrace();
                    strResult = returnError(e.getMessage().toString());
                }
                if (mHttpCallBack != null) {
                    mHttpCallBack.onRequestComplete(requestCode, strResult);
                }
            }
        }).start();
    }

    public static void doPostAsync(final Context mContext, final String mController,
                                   final String mMethod, final Map<String, Object> mParams, final CallBack callback) {
        new Thread(new Runnable() {
            public void run() {
                String strResult = "";
                try {
                    strResult = doPost(mContext, mController, mMethod, mParams);
                } catch (Exception e) {
                    e.printStackTrace();
                    strResult = returnError(e.getMessage().toString());
                }
                if (callback != null) {
                    callback.onRequestComplete(strResult);
                }
            }
        }).start();
    }

    public static String doPost(Context mContext, String mController, String mMethod, Map<String, Object> mParams)
            throws Exception {
        if (!NetworkUtils.isConnected(mContext)) {
            return returnError("网络异常!");
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String strUrl = prefs.getString("pref_servel_url", "");
        //String strUrl ="http://192.168.88.100:82";
        if (strUrl.equals("")) {
            return returnError("请配置服务器地址!");
        }
        strUrl += "/api/" + mController + "/" + mMethod;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            String strParams = "";
            if (mParams != null) {
                strParams = new GsonBuilder().serializeNulls().create().toJson(mParams);
                connection.setRequestProperty("Content-Length", String.valueOf(strParams.getBytes().length));
            }
            connection.setDoOutput(true); // 发送POST请求必须设置允许输出
            connection.setDoInput(true); // 发送POST请求必须设置允许输入
            // 获取输出流
            OutputStream os = connection.getOutputStream();
            os.write(strParams.getBytes());
            os.flush();
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream is = connection.getInputStream();
                String strResult = StreamUtils.streamToString(is);
                is.close();
                return strResult;
            } else {
                return returnError("Http请求失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnError(e.getMessage().toString());
        }

    }

    public static String returnError(String error) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "9");
        map.put("message", error);
        return new GsonBuilder().create().toJson(map);
    }

    public static Bitmap getImageUrl(String path, File cache) throws Exception {
        File file = new File(cache, path.substring(path.lastIndexOf("/")));
        // 如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            //FileInputStream fileInputStream = new FileInputStream(new File(cache, file.getName()));
            //Bitmap bitmap=BitmapFactory.decodeStream(fileInputStream);
            Bitmap bitmap= BitmapFactory.decodeFile(file.getPath());
            return bitmap;
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                Bitmap bitmap= BitmapFactory.decodeStream(is);
                FileOutputStream fOut=new FileOutputStream(file);
                if(bitmap.compress(Bitmap.CompressFormat.JPEG,100,fOut)){
                    fOut.flush();
                    fOut.close();
                }
//				FileOutputStream fos = new FileOutputStream(file);
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = is.read(buffer)) != -1) {
//					fos.write(buffer, 0, len);
//				}
//				is.close();
//				fos.close();
                // 返回一个URI对象
                return bitmap;
            }
        }
        return null;
    }

}
