package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.CommetTypeBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * power
 * 2017年11月2日 16:27:03
 * 取消报修
 */
public class CancleRepaireActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.text) TextView text;
    @BindView(R.id.radio_group) RadioGroup radioGroup;
    @BindView(R.id.content_id) EditText contentId;
    @BindView(R.id.query_btn) Button queryBtn;
    private String oid;
    private CommetTypeBean commetTypeBean;
    private List<CommetTypeBean.DataBean> list;
    private String quxiaoId;
    private ResultBean resultBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_cancle_repaire);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("取消报修");
        backIv.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        oid = getIntent().getStringExtra("oid");
        requestQuxiaoTips();
    }

    private void requestQuxiaoTips() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id", ""));
        map.put("token", SPUtils.getInstance().getString("token", ""));
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data", values.toString());

        OkGo.<CommetTypeBean>post(Urls.quxiao_tips)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<CommetTypeBean>(CommetTypeBean.class) {
                    @Override
                    public void onSuccess(Response<CommetTypeBean> response) {
                        commetTypeBean = response.body();
                        if (TextUtils.equals("1", commetTypeBean.getStatus())){
                            list = commetTypeBean.getData();
                            initRadioButton();
                        }else {
                            TUtils.showShort(getApplicationContext(), commetTypeBean.getInfo());
                        }
                    }
                });
    }

    /**
     * 动态添加radiobutton和下划线
     */
    private void initRadioButton() {
        for (int i = 0; i < list.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(20, 20, 20, 20);
            radioButton.setId(Integer.parseInt(list.get(i).getId()));
            radioButton.setLayoutParams(params);
            radioButton.setButtonDrawable(R.drawable.radio_selector_style);
            radioButton.setPadding(20, 10, 10, 10);
            radioButton.setText(commetTypeBean.getData().get(i).getTitle());
            radioButton.setTextSize(16);
            radioButton.setTextColor(getResources().getColor(R.color.text_color01));
            radioGroup.addView(radioButton);

            View view = new View(this);
            RadioGroup.LayoutParams param1 = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, 1);
            param1.setMargins(20, 0, 20, 0);
            view.setLayoutParams(param1);
            view.setBackgroundColor(getResources().getColor(R.color.hint));
            radioGroup.addView(view);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //通过checkedId找到控件，通过getId方法拿到选中状态radiobutton的id进行判断
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                int id = radioButton.getId();
                quxiaoId = id + "";
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.query_btn:
                requestOrderClose();
                break;
        }
    }

    private void requestOrderClose() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id", ""));
        map.put("token", SPUtils.getInstance().getString("token", ""));
        map.put("id", SPUtils.getInstance().getString("id", ""));
        map.put("oid", oid);
        map.put("info",contentId.getText().toString().trim());
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data", values.toString());

        OkGo.<ResultBean>post(Urls.order_close)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ResultBean>(this,ResultBean.class) {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {
                        resultBean = response.body();
                        showTips();

                    }
                });
    }

    private void showTips() {
        new NormalAlertDialog.Builder(this).setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true).setTitleText("提交成功")
                .setTitleTextColor(R.color.text_color01)
                .setContentText(resultBean.getInfo())
                .setContentTextColor(R.color.text_color02)
                .setSingleMode(true).setSingleButtonText("确 认")
                .setSingleButtonTextColor(R.color.green01)
                .setCanceledOnTouchOutside(false)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .build()
                .show();
    }
}
