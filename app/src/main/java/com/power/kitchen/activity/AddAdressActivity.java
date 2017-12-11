package com.power.kitchen.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.power.kitchen.bean.CitysBean;
import com.power.kitchen.bean.JsonBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.GetJsonDataUtil;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.SelectedAreaPop;
import com.power.kitchen.utils.Urls;
import com.suke.widget.SwitchButton;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAdressActivity extends BaseActivity implements SelectedAreaPop.AreaClickListener {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.title_right_tv) TextView titleRightTv;
    @BindView(R.id.username_tv) EditText usernameTv;
    @BindView(R.id.telnum_tv) EditText telnumTv;
    @BindView(R.id.adress_tv) TextView adressTv;
    @BindView(R.id.adress_iv) ImageView adressIv;
    @BindView(R.id.detailadress_tv) EditText detailadressTv;
    @BindView(R.id.moren_switchBtn) SwitchButton morenSwitchBtn;
    @BindView(R.id.gsmc_tv) EditText gsmcTv;
    @BindView(R.id.adress_rl) RelativeLayout adress_rl;

    private UltimateBar ultimateBar;
    private int temp = 0;
    private String tips,sheng_id="",shi_id="",qu_id="";
    private SelectedAreaPop selectedAreaPop;
    private InputMethodManager imm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_add_adress);
        ButterKnife.bind(this);
        initViewAndListener();
        requestAreaProvice();
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

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

                            selectedAreaPop = new SelectedAreaPop(AddAdressActivity.this, R.layout.selected_area_pop,areas);
                            selectedAreaPop.setOnAreaClickListener(AddAdressActivity.this);
                            selectedAreaPop.setOnDismissListener(onDismissListener);
                        }
                    }
                });
    }

    private void initViewAndListener() {
        contentTv.setText("添加新地址");
        titleRightTv.setText("保存");
        titleRightTv.setVisibility(View.VISIBLE);
        backIv.setOnClickListener(this);
        titleRightTv.setOnClickListener(this);
        adress_rl.setOnClickListener(this);
        setSwitchBtn();
    }

    private void setSwitchBtn() {
        morenSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (morenSwitchBtn.isChecked()){
                    temp = 1;
                    Logger.e(temp+"");
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
            case R.id.title_right_tv:
                if (validate()){
                    requestAreaAdd();
                }
                break;
            case R.id.adress_rl:
                if (imm != null){
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                if(selectedAreaPop!=null){
                    setShowPop(selectedAreaPop, adressIv);
                }
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

    private void requestAreaAdd() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("name",usernameTv.getText().toString());
        map.put("company_name",gsmcTv.getText().toString());
        map.put("tel",telnumTv.getText().toString());
        map.put("sheng_id",sheng_id);
        map.put("shi_id",shi_id);
        map.put("qu_id",qu_id);
        map.put("address",detailadressTv.getText().toString());
        map.put("is_def",temp + "");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<ResultBean>post(Urls.area_add)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ResultBean>(this,ResultBean.class) {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {
                        ResultBean resultBean = response.body();
                        if (!TextUtils.equals("1",resultBean.getStatus())){
                            tips = resultBean.getInfo();
                            showTipsValidate(tips);
                        }else {
                            finish();
                        }
                    }
                });
    }

    private boolean validate() {
        tips = "";
        if (TextUtils.isEmpty(usernameTv.getText().toString())) {
            tips = "请填写姓名！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(telnumTv.getText().toString())) {
            tips = "请填写联系电话！";
            showTipsValidate(tips);
            return false;
        }
        if (telnumTv.getText().toString().length() != 11){
            tips = "请输入正确的手机号！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(gsmcTv.getText().toString())){
            tips = "请填写公司名称！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(detailadressTv.getText().toString())){
            tips = "请填写详细地址！";
            showTipsValidate(tips);
            return false;
        }
        return true;
    }

    private void showTipsValidate(String tips) {
        new NormalAlertDialog.Builder(this).setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true).setTitleText("提示")
                .setTitleTextColor(R.color.text_color01)
                .setContentText(tips)
                .setContentTextColor(R.color.text_color02)
                .setSingleMode(true).setSingleButtonText("确定")
                .setSingleButtonTextColor(R.color.green01)
                .setCanceledOnTouchOutside(false)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onAreaClickListener(String areaName, String sheng_id, String shi_id, String qu_id) {
        adressTv.setText(areaName);
        this.sheng_id=sheng_id;
        this.shi_id=shi_id;
        this.qu_id=qu_id;
        selectedAreaPop.dismiss();
    }

    @Override
    public void onAreaClearListener() {
        selectedAreaPop.dismiss();
    }
}
