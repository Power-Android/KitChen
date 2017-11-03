package com.power.kitchen.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
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
 * 密码找回
 * power
 * 2017年10月18日 13:13:02
 */

public class PwdRetrievalActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.retrieval_phone_et) EditText retrievalPhoneEt;
    @BindView(R.id.retrieval_yzm_et) EditText retrievalYzmEt;
    @BindView(R.id.get_yzm_tv) TextView getYzmTv;
    @BindView(R.id.retrieval_pwd_et) EditText retrievalPwdEt;
    @BindView(R.id.look_retrievalpwd_iv) ImageView lookRetrievalpwdIv;
    @BindView(R.id.retrieval_querypwd_et) EditText retrievalQuerypwdEt;
    @BindView(R.id.look_querypwd_iv) ImageView lookQuerypwdIv;
    @BindView(R.id.retrieval_yzmiv_et) EditText retrievalYzmivEt;
    @BindView(R.id.retrieval_yzm_iv) ImageView retrievalYzmIv;
    @BindView(R.id.retrieval_btn) Button retrievalBtn;
    private UltimateBar ultimateBar;
    private String tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_pwd_retrieval);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("密码找回");

        backIv.setOnClickListener(this);
        getYzmTv.setOnClickListener(this);
        lookRetrievalpwdIv.setOnClickListener(this);
        lookQuerypwdIv.setOnClickListener(this);
        retrievalYzmIv.setOnClickListener(this);
        retrievalBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.get_yzm_tv://获取验证码
                timer.start();
                break;
            case R.id.look_retrievalpwd_iv://显示/隐藏密码
                pwdShow(retrievalPwdEt,lookRetrievalpwdIv);
                break;
            case R.id.look_querypwd_iv://显示/隐藏确认密码
                pwdShow(retrievalQuerypwdEt,lookQuerypwdIv);
                break;
            case R.id.retrieval_yzm_iv://请求验证码图片
                break;
            case R.id.retrieval_btn://修改密码
                if (validate()){
                    requestResetPwd();
                }
                break;
        }
    }

    private boolean validate() {
        tips = "";
        if (TextUtils.isEmpty(retrievalPhoneEt.getText().toString())) {
            tips = "请填写手机号！";
            showTipsValidate(tips);
            return false;
        }
        if (retrievalPhoneEt.getText().length() != 11){
            tips = "请填写正确手机号！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(retrievalPwdEt.getText().toString())) {
            tips = "请填写密码！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(retrievalQuerypwdEt.getText().toString())){
            tips = "请填写确认密码！";
            showTipsValidate(tips);
            return false;
        }
        if (!ispsd(retrievalPwdEt.getText().toString())){
            tips = "密码格式不正确！";
            showTipsValidate(tips);
            return false;
        }
        if (retrievalPwdEt.getText().toString().trim().length() < 6 && retrievalPwdEt.getText().toString().trim().length() > 20){
            tips = "请输入6-20位组合密码！";
            showTipsValidate(tips);
            return false;
        }
        if (retrievalQuerypwdEt.getText().toString().trim().length() < 6 && retrievalQuerypwdEt.getText().toString().trim().length() > 20){
            tips = "请输入6-20位组合密码！";
            showTipsValidate(tips);
            return false;
        }
        if (!TextUtils.equals(retrievalPwdEt.getText().toString(),retrievalQuerypwdEt.getText().toString())){
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

    private void requestResetPwd() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("mobile",SPUtils.getInstance().getString("mobile",""));
        map.put("password",retrievalPwdEt.getText().toString());
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<ResultBean>post(Urls.resetpass)
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
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.kc_gone_pwd));
            editText.setSelection(editText.getText().length());     //把光标设置到当前文本末尾

        }else{
            editText.setInputType(type);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.kc_eye_gary));
            editText.setSelection(editText.getText().length());
        }
    }

    /**
     * 倒计时类
     */
    private CountDownTimer timer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getYzmTv.setEnabled(false);
            getYzmTv.setText((millisUntilFinished / 1000)+"s后重新获取");
        }

        @Override
        public void onFinish() {
            getYzmTv.setEnabled(true);
            getYzmTv.setText("获取验证码");
        }
    };

}
