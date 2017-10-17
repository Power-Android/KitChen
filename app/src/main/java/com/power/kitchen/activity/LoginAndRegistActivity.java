package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.app.SystemBarTintManager;

import org.zackratos.ultimatebar.UltimateBar;

public class LoginAndRegistActivity extends BaseActivity {

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
        Button login = (Button) findViewById(R.id.login_regist_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAndRegistActivity.this,MainActivity.class));
            }
        });
    }
}
