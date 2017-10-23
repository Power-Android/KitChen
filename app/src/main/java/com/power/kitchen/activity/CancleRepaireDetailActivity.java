package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.view.MyGridView;

import org.zackratos.ultimatebar.UltimateBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/20.
 */

public class CancleRepaireDetailActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.ggt_iv) ImageView ggtIv;
    @BindView(R.id.ggt_cancle_iv) ImageView ggtCancleIv;
    @BindView(R.id.title_reason_tv) TextView titleReasonTv;
    @BindView(R.id.qx_reason_tv) TextView qxReasonTv;
    @BindView(R.id.quxiao_view01) LinearLayout quxiaoView01;
    @BindView(R.id.device_txm_et) TextView deviceTxmEt;
    @BindView(R.id.weiwancheng_view01) LinearLayout weiwanchengView01;
    @BindView(R.id.date_tv) TextView dateTv;
    @BindView(R.id.weiwancheng_view02) LinearLayout weiwanchengView02;
    @BindView(R.id.problem_device_et) TextView problemDeviceEt;
    @BindView(R.id.gridview) MyGridView gridview;
    @BindView(R.id.query_btn) Button queryBtn;

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
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("已取消");
        weiwanchengView01.setVisibility(View.GONE);
        weiwanchengView02.setVisibility(View.GONE);
        quxiaoView01.setVisibility(View.VISIBLE);
        queryBtn.setText("重新报修");
        titleReasonTv.setText("保修已取消，若需要维修，请重新报修");
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
