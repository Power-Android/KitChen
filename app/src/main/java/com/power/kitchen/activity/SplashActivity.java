package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.bean.BackgroundBean;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.Urls;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.splash_iv)
    ImageView splashIv;
    private int time = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                time--;
                if (time == 0) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    Message message = mHandler.obtainMessage(1);
                    mHandler.sendMessageDelayed(message, 1000);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar(false);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

}
