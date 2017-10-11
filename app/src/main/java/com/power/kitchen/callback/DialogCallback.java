package com.power.kitchen.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.request.base.Request;
import com.power.kitchen.R;
import com.power.kitchen.utils.LoadingDialog;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/10/11.
 */

public abstract class DialogCallback<T> extends JsonCallback<T> {

    private LoadingDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new LoadingDialog(activity, R.style.dialog);
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        /*request.headers("header1", "HeaderValue1")//
                .params("params1", "ParamsValue1")//
                .params("token", "3215sdf13ad1f65asd4f3ads1f");*/
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}