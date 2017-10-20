package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;

import org.zackratos.ultimatebar.UltimateBar;

/**
 * Created by Administrator on 2017/10/20.
 */

public class CancleRepaireDetailActivity extends BaseActivity {

    private UltimateBar ultimateBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_not_repaire_detail);
    }
}
