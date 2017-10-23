package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.adapter.AdressManageAdapter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.AdressManageBean;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdressManageActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.title_right_tv) TextView titleRightTv;
    @BindView(R.id.title_msg) ImageView titleMsg;
    @BindView(R.id.adress_list) ListView adressList;

    private UltimateBar ultimateBar;
    List<AdressManageBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);
        initViewAndListener();
        initAdapter();
    }

    private void initViewAndListener() {
        contentTv.setText("地址管理");
        titleRightTv.setText("添加地址");
        titleRightTv.setVisibility(View.VISIBLE);
        backIv.setOnClickListener(this);
        titleRightTv.setOnClickListener(this);
    }

    private void initAdapter() {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AdressManageBean adressManageBean = new AdressManageBean();
            adressManageBean.setName("收货人"+i);
            adressManageBean.setAddress("这是收货地址---------------"+i);
            adressManageBean.setTelephone("17611225393");
            list.add(adressManageBean);
        }
        AdressManageAdapter adapter = new AdressManageAdapter(AdressManageActivity.this,list);
        adressList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right_tv:
                startActivity(new Intent(this,AddAdressActivity.class));
                break;

        }
    }
}
