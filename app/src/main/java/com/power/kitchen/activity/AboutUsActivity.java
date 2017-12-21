package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.NoticeOrderInfoBean;
import com.power.kitchen.bean.NoticeOrderListBean;
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

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
//    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.about_contrent_tv) TextView aboutContentTv;

    private UltimateBar ultimateBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        initViewAndListener();
    }

    private void initViewAndListener() {
        contentTv.setText("关于我们");
        backIv.setOnClickListener(this);
        requestAboutUs();
       /* String weburl = "https://www.baidu.com";

        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setDefaultTextEncodingName("utf-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(weburl);*/
    }

    private void requestAboutUs() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<NoticeOrderInfoBean>post(Urls.about_us)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<NoticeOrderInfoBean>(this,NoticeOrderInfoBean.class) {
                    @Override
                    public void onSuccess(Response<NoticeOrderInfoBean> response) {
                        NoticeOrderInfoBean infoBean = response.body();
                        if (TextUtils.equals("1",infoBean.getStatus())){
                            aboutContentTv.setText(Html.fromHtml(infoBean.getData().getContent()));
                        }else {
                            TUtils.showShort(getApplicationContext(),infoBean.getInfo());
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
