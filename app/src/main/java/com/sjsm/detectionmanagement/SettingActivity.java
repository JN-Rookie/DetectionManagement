package com.sjsm.detectionmanagement;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;

import com.sjsm.detectionmanagement.common.Constant;
import com.sjsm.detectionmanagement.utils.ShowMsgUtils;
import com.sjsm.detectionmanagement.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingActivity extends BaseActivity {

    @BindView(R.id.txtServerUrl)
    EditText mTxtServerUrl;
    @BindView(R.id.txtShouFeiUrl)
    EditText mTxtShouFeiUrl;
    @BindView(R.id.txtWtyyServiceUrl)
    EditText mTxtWtyyServiceUrl;
    @BindView(R.id.btnSave)
    Button   mBtnSave;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mContext = this;
        setupSupportActionBar(false);
        initData();
    }

    private void initData() {
        if(SpUtils.contains(mContext, "ServerUrl")){
            mTxtServerUrl.setText(SpUtils.get(mContext, "ServerUrl", "").toString());
        }else{
            mTxtServerUrl.setText("http://172.16.88.1:83/jcms/jky.asmx");
        }
        if(SpUtils.contains(mContext, "ShouFeiUrl")){
            mTxtShouFeiUrl.setText(SpUtils.get(mContext, "ShouFeiUrl", "").toString());
        }else{
            mTxtShouFeiUrl.setText("http://172.16.88.1:83/jky/dfmsservice.asmx");
        }
        if(SpUtils.contains(mContext, "WtyyServiceUrl")){
            mTxtWtyyServiceUrl.setText(SpUtils.get(mContext, "WtyyServiceUrl", "").toString());
        }else{
            mTxtWtyyServiceUrl.setText("http://172.16.88.56");
        }
    }

    @OnClick(R.id.btnSave)
    public void onClick() {
        Message msg = new Message();
        msg.what = Constant.SHOWTOAST;
        if (mTxtServerUrl.getText().toString().equals("")) {
            msg.obj = "请输入服务地址！";
            handler.sendMessage(msg);
            return;
        }
        if (mTxtShouFeiUrl.getText().toString().equals("")) {
            msg.obj = "请输入收费服务地址！";
            handler.sendMessage(msg);
            return;
        }
        if(mTxtWtyyServiceUrl.getText().toString().equals("")){
            msg.obj = "请输入新服务地址！";
            handler.sendMessage(msg);
            return;
        }
        SpUtils.put(mContext, "ServerUrl", mTxtServerUrl.getText().toString());
        SpUtils.put(mContext, "ShouFeiUrl", mTxtShouFeiUrl.getText().toString());
        SpUtils.put(mContext, "WtyyServiceUrl", mTxtWtyyServiceUrl.getText().toString());

        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        SettingActivity.this.finish();
    }

    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.SHOWTOAST:
                    ShowMsgUtils.ShowToast(mContext, (String) msg.obj);
            }
        }
    };
}
