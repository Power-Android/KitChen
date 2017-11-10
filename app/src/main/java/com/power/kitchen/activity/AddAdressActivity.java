package com.power.kitchen.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.power.kitchen.bean.JsonBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.utils.GetJsonDataUtil;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.Urls;
import com.suke.widget.SwitchButton;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAdressActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.title_right_tv) TextView titleRightTv;
    @BindView(R.id.username_tv) EditText usernameTv;
    @BindView(R.id.telnum_tv) EditText telnumTv;
    @BindView(R.id.adress_tv) TextView adressTv;
    @BindView(R.id.adress_iv) ImageView adressIv;
    @BindView(R.id.location_iv) ImageView locationIv;
    @BindView(R.id.detailadress_tv) EditText detailadressTv;
    @BindView(R.id.moren_switchBtn) SwitchButton morenSwitchBtn;
    @BindView(R.id.gsmc_tv) EditText gsmcTv;

    private UltimateBar ultimateBar;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;
    private OptionsPickerView pvOptions;
    private int temp = 0;
    private String tips;

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
    }

    private void initViewAndListener() {
        contentTv.setText("添加新地址");
        titleRightTv.setText("保存");
        initJsonData();
        titleRightTv.setVisibility(View.VISIBLE);
        backIv.setOnClickListener(this);
        titleRightTv.setOnClickListener(this);
        adressIv.setOnClickListener(this);
        locationIv.setOnClickListener(this);
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
            case R.id.adress_iv:
                ShowPickerView();
                break;
            case R.id.location_iv:
                ShowPickerView();
                break;
        }
    }

    private void requestAreaAdd() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("name",usernameTv.getText().toString());
        map.put("company_name",gsmcTv.getText().toString());
        map.put("tel",telnumTv.getText().toString());
        map.put("sheng_id","2");
        map.put("shi_id","33");
        map.put("qu_id","378");
        map.put("address",detailadressTv.getText().toString());
        map.put("is_def",temp + "");
        Logger.e(temp+"--------");
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


    /**
     * 地址选择器
     */
    private void ShowPickerView() {

        //返回的分别是三个级别的选中位置
        //ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
        //设置选中项文字颜色
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);
                adressTv.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_city_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.returnData();
                                pvOptions.dismiss();
                            }
                        });
                    }
                })
                .setDividerColor(getResources().getColor(R.color.hint))
                .setTextColorOut(getResources().getColor(R.color.hint))
                .setBgColor(getResources().getColor(R.color.white01))
                .setTextColorCenter(getResources().getColor(R.color.text_color01)) //设置选中项文字颜色
                .setContentTextSize(16)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }

    /**
     * 解析数据
     */
    private void initJsonData() {

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    Toast.makeText(getApplicationContext(),"地址选择器解析数据失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
}
