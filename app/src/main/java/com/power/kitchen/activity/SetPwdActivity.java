package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.utils.TUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                TUtils.showShort(getApplicationContext(),"修改成功！");
                finish();
                break;
        }
    }

    /**
     *
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
}
