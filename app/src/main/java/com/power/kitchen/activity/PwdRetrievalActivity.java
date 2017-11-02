package com.power.kitchen.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.utils.TUtils;

import org.zackratos.ultimatebar.UltimateBar;

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
                TUtils.showShort(getApplicationContext(),"修改成功");
                finish();
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
