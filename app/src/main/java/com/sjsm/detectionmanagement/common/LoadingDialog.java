package com.sjsm.detectionmanagement.common;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjsm.detectionmanagement.R;


/**
 * Created by zhaohui on 16/7/28.
 */
public class LoadingDialog {

    public static Dialog createLoadingDialog(Context context, String content) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.layout_loading_dialog, null);
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);  // 加载布局
            TextView loadText = (TextView) v.findViewById(R.id.text);
            loadText.setText(content);
            Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
            loadingDialog.setCancelable(false);
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            return loadingDialog;
    }

}
