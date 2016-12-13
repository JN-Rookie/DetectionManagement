package com.sjsm.detectionmanagement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sjsm.detectionmanagement.common.Constant;
import com.sjsm.detectionmanagement.common.LoadingDialog;
import com.sjsm.detectionmanagement.model.ResObject;
import com.sjsm.detectionmanagement.utils.Ksoap2Utils;
import com.sjsm.detectionmanagement.utils.PhoneUtils;
import com.sjsm.detectionmanagement.utils.ShowMsgUtils;
import com.sjsm.detectionmanagement.utils.SpUtils;
import com.sjsm.detectionmanagement.utils.XmlUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtUserId)
    EditText mTxtUserId;
    @BindView(R.id.txtPassword)
    EditText mTxtPassword;
    @BindView(R.id.btnlogin)
    Button   mBtnlogin;
    @BindView(R.id.cbRember)
    CheckBox mCbRember;
    @BindView(R.id.btnset)
    Button   mBtnset;
    public Dialog mLoading;
    @BindView(R.id.txtVersionCode)
    TextView mTxtVersionCode;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidget();
        initLogin();
    }

    private void initWidget() {
        mContext = this;
        ButterKnife.bind(this);
        mTxtVersionCode.setText(PhoneUtils.getVersionName(mContext));
    }

    private void initLogin() {
        if (SpUtils.contains(mContext, "IsRemember")) {
            mCbRember.setChecked(false);
            mTxtUserId.setText((String) SpUtils.get(mContext, "UserCode", ""));
            boolean isRemember = (Boolean) SpUtils.get(mContext, "IsRemember", false);
            if (isRemember) {
                mTxtPassword.setText((String) SpUtils.get(mContext, "Password", ""));
                mCbRember.setChecked(true);
            }
        }
    }

    @OnClick({R.id.btnlogin, R.id.btnset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                getLogin();
                break;
            case R.id.btnset:
                Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                break;
        }
    }

    private void getLogin() {
        final String userId = String.valueOf(mTxtUserId.getText());
        final String password = String.valueOf(mTxtPassword.getText());
        final Message msg = new Message();
        msg.what = Constant.SHOWTOAST;
        if (userId.equals("")) {
            msg.obj = "用户名不能为空!";
            handler.sendMessage(msg);
            return;
        }
        if (password.equals("")) {
            msg.obj = "密码不能为空!";
            handler.sendMessage(msg);
            return;
        }
        mLoading = LoadingDialog.createLoadingDialog(mContext, "正在登陆...");
        ;
        mLoading.show();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SIM_USERCODE", userId);
        params.put("SIM_PASSWORD", password);
        String serviceUrl = SpUtils.get(mContext, "WtyyServiceUrl", "").toString();
        serviceUrl += "/cwbase/service/SJSM/SFService.asmx";
        Ksoap2Utils.doRequestAsync(serviceUrl,
                "IF_LoginAuthentication", params, new Ksoap2Utils.ResponseHandlerInterface() {

                    @Override
                    public void onSuccess(String responseString) {
                        Log.i("Zhaohui", responseString);
                        Message message = new Message();
                        message.what = Constant.SHOWTOAST;
                        try {
                            ResObject resObject = XmlUtils.parseResponseXML(responseString);
                            Log.i("zhaohui", resObject.success);
                            if (resObject.success.equals("1")) {
                                JSONObject data = new JSONObject(resObject.data);
                                SpUtils.put(mContext, "UserName", data.get("USERNAME"));
                                SpUtils.put(mContext, "UserCode", data.get("USERCODE"));
                                SpUtils.put(mContext, "Password", password);
                                SpUtils.put(mContext, "IsRemember", mCbRember.isChecked());
                                message.what = Constant.SHOWPAGE;
                            } else {
                                message.what = Constant.SHOWMESSAGE;
                                message.obj = resObject.message;
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(String responseString) {
                        ShowMsgUtils.ShowMessage(mContext,"登录失败！");
                    }

                }
        );
    }

    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mLoading != null) {
                if (mLoading.isShowing()) {
                    mLoading.dismiss();
                }
            }
            switch (msg.what) {
                case Constant.SHOWPAGE:
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    break;
                case Constant.SHOWTOAST:
                    ShowMsgUtils.ShowToast(mContext, (String) msg.obj);
                    break;
                case Constant.SHOWMESSAGE:
                    ShowMsgUtils.ShowMessage(mContext, (String) msg.obj);
                    break;
            }
        }
    };
}
