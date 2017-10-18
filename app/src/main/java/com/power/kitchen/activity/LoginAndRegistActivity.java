package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;

import org.zackratos.ultimatebar.UltimateBar;

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
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_login_and_regist);
        ButterKnife.bind(this);
        initListener();
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
                startActivity(new Intent(LoginAndRegistActivity.this,MainActivity.class));
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

}
