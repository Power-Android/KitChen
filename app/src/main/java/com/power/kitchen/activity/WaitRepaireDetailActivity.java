package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;

import org.zackratos.ultimatebar.UltimateBar;

/**
 * Created by Administrator on 2017/10/27.
 */

public class WaitRepaireDetailActivity extends BaseActivity {

    private UltimateBar ultimateBar;
    private String oid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_not_repaire_detail);
        initView();
    }

    private void initView() {
        oid = getIntent().getStringExtra("oid");
    }
}
