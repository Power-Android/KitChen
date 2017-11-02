package com.power.kitchen.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.LoginBean;
import com.power.kitchen.bean.TokenBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *登录注册
 * power
 * 2017年10月18日 13:12:37
 */

public class LoginAndRegistActivity extends BaseActivity {

    @BindView(R.id.login_iv) ImageView loginIv;
    @BindView(R.id.rigist_iv) ImageView registIv;
    @BindView(R.id.login_tv) TextView loginTv;
    @BindView(R.id.regist_tv) TextView registTv;
    @BindView(R.id.login_phone_et) EditText loginPhoneEt;
    @BindView(R.id.login_pwd_et) EditText loginPwdEt;
    @BindView(R.id.look_login_iv) ImageView lookLoginIv;
    @BindView(R.id.login_yzm_et) EditText loginYzmEt;
    @BindView(R.id.login_yzm_iv) ImageView loginYzmIv;
    @BindView(R.id.login_btn) Button loginBtn;
    @BindView(R.id.foget_pwd_tv) TextView fogetPwdTv;
    @BindView(R.id.login_view) LinearLayout loginView;
    @BindView(R.id.rigist_phone_et) EditText registPhoneEt;
    @BindView(R.id.regist_yzm_et) EditText registYzmEt;
    @BindView(R.id.get_yzm_tv) TextView getYzmTv;
    @BindView(R.id.rigist_pwd_et) EditText registPwdEt;
    @BindView(R.id.look_rigitspwd_iv) ImageView lookRigitspwdIv;
    @BindView(R.id.rigist_querypwd_et) EditText registQuerypwdEt;
    @BindView(R.id.look_querypwd_iv) ImageView lookQuerypwdIv;
    @BindView(R.id.regist_yzmiv_et) EditText registYzmivEt;
    @BindView(R.id.regist_yzm_iv) ImageView registYzmIv;
    @BindView(R.id.regist_xieyi_ll) LinearLayout registXieyiLl;
    @BindView(R.id.regist_btn) Button registBtn;
    @BindView(R.id.regist_view) LinearLayout registView;
    private UltimateBar ultimateBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_regist);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar(false);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }*/
        ButterKnife.bind(this);
        SPUtils.getInstance().putString("app_id","android-user_20170808");
        initListener();
        requestToken();
    }

    private void initListener() {
        loginTv.setOnClickListener(this);
        registTv.setOnClickListener(this);
        lookLoginIv.setOnClickListener(this);
        loginYzmIv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        fogetPwdTv.setOnClickListener(this);
        loginView.setOnClickListener(this);
        getYzmTv.setOnClickListener(this);
        lookRigitspwdIv.setOnClickListener(this);
        lookQuerypwdIv.setOnClickListener(this);
        registYzmIv.setOnClickListener(this);
        registXieyiLl.setOnClickListener(this);
        registBtn.setOnClickListener(this);
        registView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.login_tv://切换登录View
                registView.setVisibility(View.GONE);
                registIv.setVisibility(View.INVISIBLE);
                loginView.setVisibility(View.VISIBLE);
                loginIv.setVisibility(View.VISIBLE);
                registTv.setBackgroundColor(getResources().getColor(R.color.gary02));
                registTv.setTextColor(getResources().getColor(R.color.black));
                loginTv.setBackgroundColor(getResources().getColor(R.color.white));
                loginTv.setTextColor(getResources().getColor(R.color.green01));
                break;
            case R.id.regist_tv://切换注册View
                loginView.setVisibility(View.GONE);
                loginIv.setVisibility(View.INVISIBLE);
                registIv.setVisibility(View.VISIBLE);
                registView.setVisibility(View.VISIBLE);
                loginTv.setBackgroundColor(getResources().getColor(R.color.gary02));
                loginTv.setTextColor(getResources().getColor(R.color.black));
                registTv.setBackgroundColor(getResources().getColor(R.color.white));
                registTv.setTextColor(getResources().getColor(R.color.green01));
                break;
            case R.id.look_login_iv://显示/隐藏登录密码
                pwdShow(loginPwdEt,lookLoginIv);
                break;
            case R.id.login_yzm_iv://请求登录验证码图片
                break;
            case R.id.login_btn://登录
                //TODO 记得打开注释
//                if (validate()){
                    requestLogin();
//                }
                break;
            case R.id.foget_pwd_tv://忘记密码
                startActivity(new Intent(LoginAndRegistActivity.this,PwdRetrievalActivity.class));
                break;
            case R.id.get_yzm_tv://获取验证码
                timer.start();
                break;
            case R.id.look_rigitspwd_iv://注册密码
                pwdShow(registPwdEt,lookRigitspwdIv);
                break;
            case R.id.look_querypwd_iv://注册密码确认
                pwdShow(registQuerypwdEt,lookQuerypwdIv);
                break;
            case R.id.regist_yzm_iv://请求注册验证码图片
                break;
            case R.id.regist_xieyi_ll://注册协议
                break;
            case R.id.regist_btn://注册
                startActivity(new Intent(LoginAndRegistActivity.this,MainActivity.class));
                break;
        }
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

    private boolean validate() {
        if (TextUtils.isEmpty(loginPhoneEt.getText().toString()) && TextUtils.isEmpty(loginPwdEt.getText().toString())) {
            TUtils.showShort(getApplicationContext(), "手机号或密码不能为空！");
            return false;
        }
        if (loginPhoneEt.getText().toString().trim().length() != 11){
            TUtils.showShort(getApplicationContext(), "手机号码格式不正确！");
            return false;
        }
        if (TextUtils.isEmpty(loginYzmEt.getText().toString())){
            TUtils.showShort(getApplicationContext(), "验证码不能为空！");
            return false;
        }
        if (ispsd(loginPwdEt.getText().toString()) == false){
            TUtils.showShort(getApplicationContext(), "密码格式不正确！");
            return false;
        }
        if (loginPwdEt.getText().toString().trim().length() < 6 && loginPwdEt.getText().toString().trim().length() > 20){
            TUtils.showShort(getApplicationContext(), "请输入6-20位组合密码！");
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

    private void requestToken() {
        Map<String,String> map = new HashMap<>();
        map.put("app_id","android-user_20170808");
        map.put("app_key","enet360");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<TokenBean>post(Urls.get_token)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<TokenBean>(TokenBean.class) {
                    @Override
                    public void onSuccess(Response<TokenBean> response) {
                        TokenBean tokenBean = response.body();
                        String status = tokenBean.getStatus();
                        if (TextUtils.equals("1",status)){
                            String token = tokenBean.getData().getToken();
                            SPUtils.getInstance().putString("token",token);
                        }else {
                            TUtils.showShort(getApplicationContext(),response.body().getInfo());
                        }
                    }
                });
    }

    private void requestLogin() {
        Map<String,String> map = new HashMap<>();
        map.put("app_id","android-user_20170808");
        map.put("token",SPUtils.getInstance().getString("token",""));
//        map.put("mobile",loginPhoneEt.getText().toString().trim());
//        map.put("password",loginPwdEt.getText().toString().trim());
        map.put("mobile","18515885055");
        map.put("password","a123456");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<LoginBean>post(Urls.login)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<LoginBean>(this,LoginBean.class) {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        LoginBean loginBean = response.body();
                        if (TextUtils.equals("1",loginBean.getStatus())){
                            SPUtils.getInstance().putString("id",loginBean.getData().getId());
                            SPUtils.getInstance().putString("face",loginBean.getData().getFace());
                            SPUtils.getInstance().putString("mobile",loginBean.getData().getMobile());
                            startActivity(new Intent(LoginAndRegistActivity.this,MainActivity.class));
                        }else {
                            TUtils.showShort(getApplicationContext(),loginBean.getInfo());
                        }
                    }
                });
    }
}
