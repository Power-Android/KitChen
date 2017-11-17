package com.power.kitchen.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.Area;
import com.power.kitchen.bean.CitysBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.SelectedAreaPop.AreaClickListener;
import com.power.kitchen.utils.SelectedAreaPop;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;
import com.suke.widget.SwitchButton;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdressBianJiActivity extends BaseActivity implements AreaClickListener {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.title_right_tv) TextView titleRightTv;
    @BindView(R.id.username_tv) EditText usernameEt;
    @BindView(R.id.telnum_tv) EditText telnumEt;
    @BindView(R.id.adress_tv) TextView adressTv;
    @BindView(R.id.adress_iv) ImageView adressIv;
    @BindView(R.id.location_iv) ImageView locationIv;
    @BindView(R.id.detailadress_tv) EditText detailadressEt;
    @BindView(R.id.moren_switchBtn) SwitchButton morenSwitchBtn;
    @BindView(R.id.delete_btn) Button deleteBtn;
    @BindView(R.id.gsmc_tv) EditText gsmcEt;

    private UltimateBar ultimateBar;
    private int temp = 0;
    private String area_id,sheng_id="",shi_id="",qu_id="";
    private String tips;
    private SelectedAreaPop selectedAreaPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_adress_bian_ji);
        ButterKnife.bind(this);
        initViewAndListener();
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

                            selectedAreaPop = new SelectedAreaPop(AdressBianJiActivity.this, R.layout.selected_area_pop,areas);
                            selectedAreaPop.setOnAreaClickListener(AdressBianJiActivity.this);
                            selectedAreaPop.setOnDismissListener(onDismissListener);
                        }
                    }
                });
    }

    private void initViewAndListener() {
        contentTv.setText("地址管理");
        titleRightTv.setText("保存");
        titleRightTv.setVisibility(View.VISIBLE);
        backIv.setOnClickListener(this);
        titleRightTv.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        adressIv.setOnClickListener(this);
        locationIv.setOnClickListener(this);
        area_id = getIntent().getStringExtra("area_id");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String company_name = getIntent().getStringExtra("company_name");
        String sheng_name = getIntent().getStringExtra("sheng_name");
        sheng_id = getIntent().getStringExtra("sheng_id");
        String shi_name = getIntent().getStringExtra("shi_name");
        shi_id = getIntent().getStringExtra("shi_id");
        String qu_name = getIntent().getStringExtra("qu_name");
        qu_id = getIntent().getStringExtra("qu_id");
        String adress = getIntent().getStringExtra("adress");
        String is_def = getIntent().getStringExtra("is_def");

        usernameEt.setText(name);
        telnumEt.setText(phone);
        gsmcEt.setText(company_name);
        adressTv.setText(sheng_name + " " + shi_name + " " + qu_name);
        detailadressEt.setText(adress);
        if (TextUtils.equals("1",is_def)){
            morenSwitchBtn.setChecked(true);
        }else {
            morenSwitchBtn.setChecked(false);
        }
        morenSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (morenSwitchBtn.isChecked()){
                    temp = 1;
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
                    requestAreaEdit();
                }
                break;
            case R.id.adress_iv:
                if(selectedAreaPop!=null){
                    setShowPop(selectedAreaPop, adressIv);
                }
                break;
            case R.id.location_iv:
                if(selectedAreaPop!=null){
                    setShowPop(selectedAreaPop, adressIv);
                }
                break;
            case R.id.delete_btn:
                showTips();
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

    private boolean validate() {
        tips = "";
        if (TextUtils.isEmpty(usernameEt.getText().toString())) {
            tips = "请填写姓名！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(telnumEt.getText().toString())) {
            tips = "请填写联系电话！";
            showTipsValidate(tips);
            return false;
        }
        if (telnumEt.getText().toString().length() != 11){
            tips = "请输入正确的手机号！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(gsmcEt.getText().toString())){
            tips = "请填写公司名称！";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(detailadressEt.getText().toString())){
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

    private void showTips() {
        new NormalAlertDialog.Builder(this)
                .setTitleVisible(true).setTitleText("提示")
                .setTitleTextColor(R.color.text_color01)
                .setContentText("确定要删除吗？")
                .setContentTextColor(R.color.text_color02)
                .setLeftButtonText("取消")
                .setLeftButtonTextColor(R.color.text_color01)
                .setRightButtonText("确定")
                .setRightButtonTextColor(R.color.green01)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                        requestAreaDel();
                    }
                })
                .build()
                .show();
    }

    private void requestAreaEdit() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("area_id",area_id);
        map.put("name",usernameEt.getText().toString());
        map.put("company_name",gsmcEt.getText().toString());
        map.put("tel",telnumEt.getText().toString());
        //TODO 省市区ID 没有传 记得加上
        map.put("sheng_id",sheng_id);
        map.put("shi_id",shi_id);
        map.put("qu_id",qu_id);
        map.put("address",detailadressEt.getText().toString());
        map.put("is_def",temp + "");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<ResultBean>post(Urls.area_edit)
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

    private void requestAreaDel() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("area_id",area_id);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<ResultBean>post(Urls.area_del)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ResultBean>(this,ResultBean.class) {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {
                        ResultBean resultBean = response.body();
                        if (TextUtils.equals("1",resultBean.getStatus())){
                            TUtils.showShort(getApplicationContext(),"地址删除成功！");
                            finish();
                        }else {
                            TUtils.showShort(getApplicationContext(),resultBean.getInfo());
                        }
                    }
                });
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
