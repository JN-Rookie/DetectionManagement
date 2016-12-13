package com.sjsm.detectionmanagement.model;

/**
 * Created by Administrator on 2016/12/13 0013.
 */

public class ResObject {

    public String success; // 0:失败 1:成功
    public String message;
    public String state;
    public String data;

    public ResObject() {

    }

    public ResObject(String success, String message, String state, String data) {
        this.success = success;
        this.message = message;
        this.state = state;
        this.data = data;
    }
}
