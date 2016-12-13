package com.sjsm.detectionmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohui on 16/8/19.
 */
public class UploadImageUtils extends Thread {
    private List<File> mImageFile;
    // private String serverURL = "http://172.16.88.17:8088/ImageUpload.ashx";
    private String serverURL = "http://172.16.88.17:8088/api/Record/UploadFiles";
    private UploadImageListener mUploadImageListener;
    private Map<String, String> paras = null;// post的StringBody

    public UploadImageUtils(Context context, String method, List<File> imageFiles, Map<String, String> paras) {
        mImageFile = imageFiles;
        this.paras = paras;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String strUrl = prefs.getString("pref_servel_url", "");
        serverURL = strUrl + "/api/Record/" + method;
    }

    public void setUploadImageListener(UploadImageListener uploadImageListener) {
        mUploadImageListener = uploadImageListener;
    }

    public interface UploadImageListener {
        public void uploadImageFail(String result);

        public void uploadImageSuccess(String result);
    }

    @Override
    public void run() {
        try {
            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            URL url = new URL(serverURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            for (Map.Entry<String, String> entry : paras.entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), "utf-8");
                String val = URLEncoder.encode(entry.getValue(), "utf-8");
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + end);
                ds.writeBytes(end);
                ds.writeBytes(val);
                ds.writeBytes(end);
            }

            for (int i = 0; i < mImageFile.size(); i++) {
                File file = mImageFile.get(i);
                String fileName = file.getName();
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; name=\"" + fileName + "\";filename=\"" + fileName + "\""
                        + end);

                ds.writeBytes(end);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                FileInputStream fStream = new FileInputStream(file);
                while ((length = fStream.read(buffer)) != -1) {
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                fStream.close();
                ds.flush();
            }
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            InputStream inputStream = con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            String str = buffer.toString();
            // int ch;
            // StringBuffer b = new StringBuffer();
            // while ((ch = inputStream.read()) != -1) {
            // b.append((char) ch);
            // }
            // String string = b.toString();
            // System.out.println("---> Response Data:=" + b.toString());
            // Log.i("zhaohui", string);

            // str = str.replace("\\\"", "\"");
            // str = str.substring(1, str.length() - 1);
            JSONObject jsonObj = new JSONObject(str);

            if (jsonObj.getString("code").equals("0")) {
                mUploadImageListener.uploadImageSuccess(jsonObj.getString("message"));
            } else {
                mUploadImageListener.uploadImageFail(jsonObj.getString("message"));
            }
        } catch (Exception e) {
            Log.d("allenj", e.toString());
            mUploadImageListener.uploadImageFail(e.toString());
        }

    }
}
