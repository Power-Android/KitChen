package com.power.kitchen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.username_tv) TextView usernameTv;
    @BindView(R.id.username_rl) RelativeLayout usernameRl;
    @BindView(R.id.canting_tv) TextView cantingTv;
    @BindView(R.id.canting_rl) RelativeLayout cantingRl;
    @BindView(R.id.telnum_tv) TextView telnumTv;
    @BindView(R.id.telnum_rl) RelativeLayout telnumRl;
    @BindView(R.id.adress_tv) TextView adressTv;
    @BindView(R.id.adress_rl) RelativeLayout adressRl;
    @BindView(R.id.detailadress_tv) TextView detailadressTv;
    @BindView(R.id.detailadress_rl) RelativeLayout detailadressRl;
    @BindView(R.id.adressmanage_rl) RelativeLayout adressmanageRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("个人资料");
        backIv.setOnClickListener(this);
        usernameRl.setOnClickListener(this);
        cantingRl.setOnClickListener(this);
        telnumRl.setOnClickListener(this);
        adressRl.setOnClickListener(this);
        detailadressRl.setOnClickListener(this);
        adressmanageRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.username_rl:
                break;
            case R.id.canting_rl:
                break;
            case R.id.telnum_rl:
                break;
            case R.id.adress_rl:
                break;
            case R.id.detailadress_rl:
                break;
            case R.id.adressmanage_rl:
                break;
        }
    }
}
