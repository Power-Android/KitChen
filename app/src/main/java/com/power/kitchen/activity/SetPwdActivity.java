package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 密码设置
 */
public class SetPwdActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.enter_password_et) EditText enterPasswordEt;
    @BindView(R.id.look_password01_iv) ImageView lookPassword01Iv;
    @BindView(R.id.new_password_et) EditText newPasswordEt;
    @BindView(R.id.look_password02_iv) ImageView lookPassword02Iv;
    @BindView(R.id.query_password_et) EditText queryPasswordEt;
    @BindView(R.id.look_password03_iv) ImageView lookPassword03Iv;
    @BindView(R.id.query_btn) Button queryBtn;
    private String tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_set_pwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("密码设置");
        backIv.setOnClickListener(this);
        lookPassword01Iv.setOnClickListener(this);
        lookPassword02Iv.setOnClickListener(this);
        lookPassword03Iv.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.look_password01_iv:
                pwdShow(enterPasswordEt,lookPassword01Iv);
                break;
            case R.id.look_password02_iv:
                pwdShow(newPasswordEt,lookPassword02Iv);
                break;
            case R.id.look_password03_iv:
                pwdShow(queryPasswordEt,lookPassword03Iv);
                break;
            case R.id.query_btn:
                if (validate()){
                    requestEditPwd();
                }
                break;
        }
    }

    private boolean validate() {
        tips = "";
        if (TextUtils.isEmpty(enterPasswordEt.getText().toString())) {
            tips = "请填写原密码！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(newPasswordEt.getText().toString())) {
            tips = "请填写新密码！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(queryPasswordEt.getText().toString())){
            tips = "请填写确认密码！";
            showTipsValidate(tips);
            return false;
        }
        if (!ispsd(newPasswordEt.getText().toString())){
            tips = "密码格式不正确！";
            showTipsValidate(tips);
            return false;
        }
        if (enterPasswordEt.getText().toString().trim().length() < 6 && enterPasswordEt.getText().toString().trim().length() > 20){
            tips = "请输入6-20位组合密码！";
            showTipsValidate(tips);
            return false;
        }
        if (newPasswordEt.getText().toString().trim().length() < 6 && newPasswordEt.getText().toString().trim().length() > 20){
            tips = "请输入6-20位组合密码！";
            showTipsValidate(tips);
            return false;
        }
        if (queryPasswordEt.getText().toString().trim().length() < 6 && queryPasswordEt.getText().toString().trim().length() > 20){
            tips = "请输入6-20位组合密码！";
            showTipsValidate(tips);
            return false;
        }
        if (!TextUtils.equals(newPasswordEt.getText().toString(),queryPasswordEt.getText().toString())){
            tips = "请填写相同的新密码！";
            showTipsValidate(tips);
            return false;
        }
        return true;
    }

    /**
     * 是否是纯数字或者纯英文
     * @param psd
     * @return
     */
    public static boolean ispsd(String psd) {
        Pattern p = Pattern
                .compile("^[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
        Matcher m = p.matcher(psd);

        return m.matches();
    }

    private void showTipsValidate(String tips) {
        new NormalAlertDialog.Builder(this).setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true).setTitleText("提示")
                .setTitleTextColor(R.color.text_color01)
                .setContentText(tips)
                .setContentTextColor(R.color.text_color02)
                .setSingleMode(true).setSingleButtonText("确定")
                .setSingleButtonTextColor(R.color.green01)
                .setCanceledOnTouchOutside(false)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    private void requestEditPwd() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("old_password",enterPasswordEt.getText().toString());
        map.put("password",newPasswordEt.getText().toString());
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<ResultBean>post(Urls.edit_password)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ResultBean>(this,ResultBean.class) {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {
                        ResultBean resultBean = response.body();
                        if (TextUtils.equals("1",resultBean.getStatus())){
                            TUtils.showShort(getApplicationContext(),resultBean.getInfo());
                            finish();
                        }else {
                            TUtils.showShort(getApplicationContext(),resultBean.getInfo());
                        }
                    }
                });
    }

    /**
     * @param editText
     * @param imageView
     * 设置隐藏/显示密码
     */
    public void pwdShow(EditText editText,ImageView imageView){

        int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        if(editText.getInputType() == type){//密码可见
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.kc_gone_pwd));
            editText.setSelection(editText.getText().length());     //把光标设置到当前文本末尾

        }else{
            editText.setInputType(type);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.kc_eye_gary));
            editText.setSelection(editText.getText().length());
        }
    }
}
