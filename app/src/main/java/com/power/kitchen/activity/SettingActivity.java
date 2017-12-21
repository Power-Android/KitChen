package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.Area;
import com.power.kitchen.bean.AreaAllListsBean;
import com.power.kitchen.bean.CitysBean;
import com.power.kitchen.bean.JsonBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.GetJsonDataUtil;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.SelectedAreaPop;
import com.power.kitchen.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人资料
 * power
 */

public class SettingActivity extends BaseActivity implements SelectedAreaPop.AreaClickListener {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.username_tv) TextView usernameTv;
    @BindView(R.id.username_rl) RelativeLayout usernameRl;
    @BindView(R.id.telnum_tv) TextView telnumTv;
    @BindView(R.id.telnum_rl) RelativeLayout telnumRl;
    @BindView(R.id.adress_tv) TextView adressTv;
    @BindView(R.id.adress_rl) RelativeLayout adressRl;
    @BindView(R.id.adressmanage_rl) RelativeLayout adressmanageRl;
    @BindView(R.id.adress_iv) ImageView adressIv;

    private UltimateBar ultimateBar;
    private String true_name;
    private SelectedAreaPop selectedAreaPop;
    private String sheng_name;
    private String shi_name;
    private String qu_name;
    private String tips,sheng_id="",shi_id="",qu_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        requestAreaProvice();
    }

    private void requestAreaProvice() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id",SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("area_id","1");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<CitysBean>post(Urls.area_lists)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<CitysBean>(CitysBean.class) {
                    @Override
                    public void onSuccess(Response<CitysBean> response) {
                        CitysBean citysBean = response.body();
                        if (TextUtils.equals("1",citysBean.getStatus())){
                            List<Area> areas = new ArrayList<Area>();
                            for (int i = 0; i < citysBean.getData().size(); i++) {
                                Area area = new Area();
                                area.area_id = citysBean.getData().get(i).getArea_id();
                                area.name = citysBean.getData().get(i).getName();
                                area.up_id = citysBean.getData().get(i).getUp_id();
                                areas.add(area);
                            }

                            selectedAreaPop = new SelectedAreaPop(SettingActivity.this, R.layout.selected_area_pop,areas);
                            selectedAreaPop.setOnAreaClickListener(SettingActivity.this);
                            selectedAreaPop.setOnDismissListener(onDismissListener);
                        }
                    }
                });
    }

    private void initView() {
        contentTv.setText("个人资料");
        true_name = getIntent().getStringExtra("true_name");
        sheng_name = getIntent().getStringExtra("sheng_name");
        shi_name = getIntent().getStringExtra("shi_name");
        qu_name = getIntent().getStringExtra("qu_name");
        String mobile = SPUtils.getInstance().getString("mobile", "");

        usernameTv.setText(true_name);
        telnumTv.setText(mobile);
        adressTv.setText(sheng_name+" "+shi_name+" "+qu_name);

        backIv.setOnClickListener(this);
        usernameRl.setOnClickListener(this);
        telnumRl.setOnClickListener(this);
        adressRl.setOnClickListener(this);
        adressmanageRl.setOnClickListener(this);
        adressIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.username_rl:
                Intent intent = new Intent(this,EditNameActivity.class);
                intent.putExtra("true_name",true_name);
                startActivityForResult(intent,103);
                break;
            case R.id.telnum_rl:
                break;
            case R.id.adress_rl:
                if(selectedAreaPop!=null){
                    setShowPop(selectedAreaPop, adressIv);
                }
                break;
            case R.id.adressmanage_rl:
                startActivity(new Intent(SettingActivity.this,AdressManageActivity.class));
                break;
        }
    }

    public void setShowPop(PopupWindow popupWindow, View view){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            setWindowTranslucence(0.3);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    //设置Window窗口的透明度
    public void setWindowTranslucence(double d){

        Window window = getWindow();

        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=(float) d;
        window.setAttributes(attributes);

    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 103){
            if (data != null){
                String true_name = data.getStringExtra("true_name");
                usernameTv.setText(true_name);
            }
        }
    }

    @Override
    public void onAreaClickListener(String areaName, String sheng_id, String shi_id, String qu_id) {
        adressTv.setText(areaName);
        this.sheng_id=sheng_id;
        this.shi_id=shi_id;
        this.qu_id=qu_id;
        requestEditInfo();
        selectedAreaPop.dismiss();
    }

    @Override
    public void onAreaClearListener() {
        selectedAreaPop.dismiss();
    }

    private void requestEditInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("sheng_id",sheng_id);
        map.put("shi_id",shi_id);
        map.put("qu_id",qu_id);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<ResultBean>post(Urls.edit_info)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ResultBean>(this,ResultBean.class) {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {

                    }
                });
    }
}
