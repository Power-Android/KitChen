package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.NoticeOrderInfoBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.Urls;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.message_content_tv) TextView messageContentTv;
    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    private String notice_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("报修消息");
        backIv.setOnClickListener(this);
        notice_id = getIntent().getStringExtra("notice_id");
        requestNoticeOrderInfo();
    }

    private void requestNoticeOrderInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("notice_id",notice_id);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<NoticeOrderInfoBean>post(Urls.notice_order_info)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<NoticeOrderInfoBean>(this,NoticeOrderInfoBean.class) {
                    @Override
                    public void onSuccess(Response<NoticeOrderInfoBean> response) {

                    }
                });
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
