package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
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
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistXieYiActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.xieyi_title_tv) TextView xieyiTitleTv;
    @BindView(R.id.xieyi_content_tv) TextView xieyiContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_regist_xie_yi);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("用户协议");
        backIv.setOnClickListener(this);
        requestXieYi();
    }

    private void requestXieYi() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id", ""));
        map.put("token", SPUtils.getInstance().getString("token", ""));
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data", values.toString());

        OkGo.<NoticeOrderInfoBean>post(Urls.user_xieyi)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<NoticeOrderInfoBean>(this, NoticeOrderInfoBean.class) {
                    @Override
                    public void onSuccess(Response<NoticeOrderInfoBean> response) {
                        NoticeOrderInfoBean resultBean = response.body();
                        if (TextUtils.equals("1", resultBean.getStatus())) {
                            xieyiTitleTv.setText(Html.fromHtml(resultBean.getData().getTitle()));
                            xieyiContentTv.setText(Html.fromHtml(resultBean.getData().getContent()));
                        }else {
                            TUtils.showShort(getApplicationContext(),resultBean.getInfo());
                        }
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
