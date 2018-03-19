package com.power.kitchen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.adapter.GridViewAddImgesAdpter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.Area;
import com.power.kitchen.bean.AreaListsBean;
import com.power.kitchen.bean.CitysBean;
import com.power.kitchen.bean.OrderAdressBean;
import com.power.kitchen.bean.OrderInfoBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.bean.UpLoadImgBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.CommonPopupWindow;
import com.power.kitchen.utils.ProgressDialogUtils;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.SelectedAreaPop;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.TimeUtils;
import com.power.kitchen.utils.Urls;
import com.power.kitchen.view.MyGridView;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备详情
 * power
 * 2017年10月18日 13:17:32
 */

public class DeviceDetailsActivity extends BaseActivity implements SelectedAreaPop.AreaClickListener {

    @BindView(R.id.gridview) MyGridView gridview;@BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;@BindView(R.id.device_txm_et) EditText deviceTxmEt;
    @BindView(R.id.date_tv) TextView dateTv;@BindView(R.id.jump_date_iv) RelativeLayout jumpDateIv;
    @BindView(R.id.bxqn_rbt) RadioButton bxqnRbt;@BindView(R.id.bxqw_rbt) RadioButton bxqwRbt;
    @BindView(R.id.pinpai_tv) TextView pinpaiTv;@BindView(R.id.jump_pinpai_iv) RelativeLayout jumpPinpaiIv;
    @BindView(R.id.leixing_tv) TextView leixingTv;@BindView(R.id.jump_leixing_iv) RelativeLayout jumpLeixingIv;
    @BindView(R.id.xinghao_et) TextView xinghaoEt;@BindView(R.id.device_name_et) EditText deviceNameEt;
    @BindView(R.id.device_phone_et) EditText devicePhoneEt;@BindView(R.id.device_ctmc_et) EditText deviceCtmcEt;
    @BindView(R.id.device_adress_tv) TextView deviceAdressTv;@BindView(R.id.jump_adress_iv) ImageView jumpAdressIv;
    @BindView(R.id.location_iv) ImageView locationIv;@BindView(R.id.detail_adress_et) EditText detailAdressEt;
    @BindView(R.id.problem_device_et) EditText problemDeviceEt;@BindView(R.id.cancle_btn) Button cancleBtn;
    @BindView(R.id.query_btn) Button queryBtn;@BindView(R.id.radiogroup) RadioGroup radiogroup;

    private UltimateBar ultimateBar;
    private GridViewAddImgesAdpter addImgesAdpter;
    private CommonPopupWindow popupWindow;
    private List<LocalMedia> selectList = new ArrayList<>();
    List<LocalMedia> list = new ArrayList<>();
    List<LocalMedia> list1 = new ArrayList<>();
    List<LocalMedia> list2 = new ArrayList<>();


    String temp = "1";//默认，保修期内“1”，保修期外“0”
    private SelectedAreaPop selectedAreaPop;

    private TimePickerView pvCustomLunar;
    private ArrayList<String> listExtra;//拍照报修传过来的图片路径
    private Intent intent;
    private String brandNme, typeNme, brandId, typeId = "", tx, tips,sheng_id="",shi_id="",qu_id="",bd_lat,bd_lng;
    private List<AreaListsBean.DataBean> province_list = new ArrayList<>();
    private OrderInfoBean orderInfoBean;
    private List<String> srcList = new ArrayList<>();
    private int size = 0;
    private InputMethodManager imm;

    public LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private GeoCoder geoCoder;

    private ProgressDialogUtils pd;
    private String first_areaName;
    private boolean if_baoxiu;


    public static Intent newIntent(Context context, OrderInfoBean orderInfoBean) {
        Intent mIntent = new Intent(context, DeviceDetailsActivity.class);
        mIntent.putExtra("bean", orderInfoBean);
        return mIntent;
    }

    Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                size = size + 1;
                requestUploadImg();
            } else{
                requestOrderAdd();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_device_detail);
        ButterKnife.bind(this);
        initView();
        initLocation();//百度地图定位
    }

    private void initView() {
        pd = ProgressDialogUtils.show(this, "");

        contentTv.setText("报修详情");
        initListener();
        getIntentData();//上级拍照返回来的数据----图片的path路径
        initLunarPicker();//初始化时间选择器
        if_baoxiu = SPUtils.getInstance().getBoolean("if_baoxiu", true);
        if (if_baoxiu){
            deviceNameEt.setText(SPUtils.getInstance().getString("first_name",""));
            devicePhoneEt.setText(SPUtils.getInstance().getString("first_phone",""));
            deviceCtmcEt.setText(SPUtils.getInstance().getString("first_company",""));
            deviceAdressTv.setText(SPUtils.getInstance().getString("first_shengName",""));
            detailAdressEt.setText(SPUtils.getInstance().getString("first_address",""));
            sheng_id = SPUtils.getInstance().getString("first_shengId","");
            shi_id = SPUtils.getInstance().getString("first_shiId","");
            qu_id = SPUtils.getInstance().getString("first_quId","");
        }

        /**
         * 添加照片adapter
         */
        addImgesAdpter = new GridViewAddImgesAdpter(list, this);
        gridview.setAdapter(addImgesAdpter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopup();
            }
        });
        /**
         * 是否保修期判断
         */
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.bxqn_rbt:
                        temp = "1";
                        break;
                    case R.id.bxqw_rbt:
                        temp = "0";
                }
            }
        });
        getData();
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 报修详情页传递过来的bean类
     */
    private void getData() {
        if (NotRepaireDetailActivity.flag == true || CancleRepaireDetailActivity.flag == true){
            orderInfoBean = (OrderInfoBean) getIntent().getExtras().getSerializable("bean");
            deviceTxmEt.setText(orderInfoBean.getData().getInfo().getGoods_code());
            dateTv.setText(orderInfoBean.getData().getInfo().getGoods_date());
            String goods_is_warranty = orderInfoBean.getData().getInfo().getGoods_is_warranty();
            if (TextUtils.equals("1", goods_is_warranty)) {
                bxqnRbt.setChecked(true);
            } else {
                bxqwRbt.setChecked(true);
            }
            pinpaiTv.setText(orderInfoBean.getData().getInfo().getGoods_brand_name());
            brandId = orderInfoBean.getData().getInfo().getGoods_brand();
            leixingTv.setText(orderInfoBean.getData().getInfo().getGoods_type_name());
            xinghaoEt.setText(orderInfoBean.getData().getInfo().getGoods_model());
            deviceNameEt.setText(orderInfoBean.getData().getInfo().getContact_name());
            devicePhoneEt.setText(orderInfoBean.getData().getInfo().getContact_mobile());
            deviceCtmcEt.setText(orderInfoBean.getData().getInfo().getContact_company());
            deviceAdressTv.setText(orderInfoBean.getData().getInfo().getContact_sheng_name() + " " +
                    orderInfoBean.getData().getInfo().getContact_shi_name() + " " +
                    orderInfoBean.getData().getInfo().getContact_qu_name());
            sheng_id = orderInfoBean.getData().getInfo().getContact_sheng_id();
            shi_id = orderInfoBean.getData().getInfo().getContact_shi_id();
            qu_id = orderInfoBean.getData().getInfo().getContact_qu_id();
            detailAdressEt.setText(orderInfoBean.getData().getInfo().getContact_address());
            problemDeviceEt.setText(orderInfoBean.getData().getInfo().getGoods_describe());
            String goods_images = orderInfoBean.getData().getInfo().getGoods_images();
            try {
                JSONArray good_images = new JSONArray(goods_images);
                srcList = new ArrayList<String>();
                for (int i = 0; i < good_images.length(); i++) {
                    String s = (String) good_images.get(i);
                    srcList.add(s);
                }
                Logger.e(srcList.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            String imgs = goods_images.toString().substring(1, goods_images.toString().length() - 1);
//            srcList = Arrays.asList(imgs.split(","));

            for (int i = 0; i < srcList.size(); i++) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(srcList.get(i));
                Logger.e(srcList.get(i));
                list1.add(localMedia);
            }
            list.addAll(list1);
            addImgesAdpter = new GridViewAddImgesAdpter(list, this);
            gridview.setAdapter(addImgesAdpter);
            NotRepaireDetailActivity.flag = false;
            CancleRepaireDetailActivity.flag = false;
        }
    }

    /**
     * 上级拍照页面返回的数据---图片的path路径
     */
    private void getIntentData() {
        listExtra = getIntent().getStringArrayListExtra("list");
        LocalMedia localMedia = new LocalMedia();
        if (listExtra != null) {
            for (int i = 0; i < listExtra.size(); i++) {
                String path = listExtra.get(i);
                localMedia.setPath(path);
                list.add(localMedia);
            }
            addImgesAdpter = new GridViewAddImgesAdpter(list, this);
            gridview.setAdapter(addImgesAdpter);
        }
    }

    private void initListener() {
        backIv.setOnClickListener(this);
        jumpDateIv.setOnClickListener(this);
        jumpPinpaiIv.setOnClickListener(this);
        jumpLeixingIv.setOnClickListener(this);
        jumpAdressIv.setOnClickListener(this);
        locationIv.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        deviceAdressTv.setOnClickListener(this);
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

                            selectedAreaPop = new SelectedAreaPop(DeviceDetailsActivity.this, R.layout.selected_area_pop,areas);
                            selectedAreaPop.setOnAreaClickListener(DeviceDetailsActivity.this);
                            selectedAreaPop.setOnDismissListener(onDismissListener);
                        }
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
            case R.id.jump_date_iv://选择购买日期
                if (imm != null){
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                pvCustomLunar.show();
                break;
            case R.id.jump_pinpai_iv://选择品牌
                intent = new Intent();
                intent.setClass(DeviceDetailsActivity.this, BrandAndTypeActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.jump_leixing_iv://选择类型
                intent = new Intent();
                intent.setClass(DeviceDetailsActivity.this, BrandAndTypeActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.jump_adress_iv://选择所在区域
                if (imm != null){
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                if(selectedAreaPop!=null){
                    setShowPop(selectedAreaPop, jumpAdressIv);
                }
                break;
            case R.id.device_adress_tv:
                if (imm != null){
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                if(selectedAreaPop!=null){
                    setShowPop(selectedAreaPop, jumpAdressIv);
                }
            case R.id.location_iv://定位
                mLocationClient.start();
                break;
            case R.id.cancle_btn://取消报修
                showTips();
                break;
            case R.id.query_btn://确认报修
                if (validate()){
                    pd.show();
                    geoCoder = GeoCoder.newInstance();
                    String address = deviceAdressTv.getText().toString().trim()+detailAdressEt.getText().toString().trim();
                    Logger.e(address);

                    geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                        @Override
                        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                            if (geoCodeResult != null && geoCodeResult.getLocation() != null){
                                Logger.e("地理编码查询结果" + geoCodeResult.getLocation());
                                double latitude = geoCodeResult.getLocation().latitude;
                                double longitude = geoCodeResult.getLocation().longitude;
                                bd_lat = latitude+"";
                                bd_lng = longitude+"";
                            }else {
                                bd_lat = SPUtils.getInstance().getString("latitude","");
                                bd_lng = SPUtils.getInstance().getString("longitude","");
                            }
                            if (validate()) {
                                if (list2.size() != 0){
                                    requestUploadImg();
                                }else {
                                    requestOrderAdd();
                                }
                            }
                        }

                        @Override
                        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

                        }
                    });
                    GeoCodeOption GeoOption = new GeoCodeOption().city("").address(address);
                    geoCoder.geocode(GeoOption);
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

    private String src;
    private void requestUploadImg() {
//        if (list1.size() == 0){
            if (list2.size() != 0){
                int listSize = list2.size();
                if (size <= listSize - 1){
                    String path = list2.get(size).getPath();
                    Logger.e(path);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,80,bos);
                    byte[] byteArray = bos.toByteArray();
                    byte[] encode = it.sauronsoftware.base64.Base64.encode(byteArray);
                    String url = new String(encode);
                    Map<String, String> map = new HashMap<>();
                    map.put("app_id",SPUtils.getInstance().getString("app_id",""));
                    map.put("token",SPUtils.getInstance().getString("token",""));
                    map.put("id",SPUtils.getInstance().getString("id",""));
                    map.put("img","data:image/jpg;base64," + url);
                    JSONObject values = new JSONObject(map);
                    HttpParams params = new HttpParams();
                    params.put("data",values.toString());

                    OkGo.<UpLoadImgBean>post(Urls.uploads_img)
                            .tag(this)
                            .params(params)
                            .execute(new JsonCallback<UpLoadImgBean>(UpLoadImgBean.class) {
                                @Override
                                public void onSuccess(Response<UpLoadImgBean> response) {
                                    UpLoadImgBean upLoadImgBean = response.body();
                                    if (TextUtils.equals("1",upLoadImgBean.getStatus())){
                                        src = upLoadImgBean.getData().getSrc();
                                        srcList.add(src);
                                        handler.sendEmptyMessage(1);
                                    }
                                }
                            });
                }else {
                    handler.sendEmptyMessage(2);
                }
            }
//        }

    }

    private boolean validate() {
        tips = "";
        if (TextUtils.isEmpty(brandId)) {
            tips = "请填写设备品牌";
            showTipsValidate(tips);
            return false;
        }

        if (TextUtils.isEmpty(deviceNameEt.getText().toString())) {
            tips = "请填写姓名";
            showTipsValidate(tips);
            return false;
        }

        if (TextUtils.isEmpty(deviceCtmcEt.getText().toString())) {
            tips = "请填写公司名称";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(deviceAdressTv.getText().toString())) {
            tips = "请填写所在区域";
            showTipsValidate(tips);
            return false;
        }
        if (TextUtils.isEmpty(detailAdressEt.getText().toString())) {
            tips = "请填写详细地址";
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
                .setContentText("保修单未提交，是否退出？")
                .setContentTextColor(R.color.text_color02)
                .setLeftButtonText("退出")
                .setLeftButtonTextColor(R.color.text_color01)
                .setRightButtonText("不退出")
                .setRightButtonTextColor(R.color.green01)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    /**
     * 确认报修
     */
    private void requestOrderAdd() {
        Map<String,String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("name",deviceNameEt.getText().toString());
        map.put("company",deviceCtmcEt.getText().toString());
        map.put("mobile",devicePhoneEt.getText().toString());
        map.put("sheng_id",sheng_id);
        map.put("shi_id",shi_id);
        map.put("qu_id",qu_id);
        map.put("address",detailAdressEt.getText().toString());
        map.put("bd_lat",bd_lat);
        Logger.e(bd_lat);
        map.put("bd_lng",bd_lng);
        Logger.e(bd_lng);
        map.put("code",deviceTxmEt.getText().toString());
        map.put("date",dateTv.getText().toString());
        map.put("is_warranty",temp);
        map.put("brand_id",brandId);
        map.put("type_id",typeId);
        map.put("model",xinghaoEt.getText().toString());
        map.put("desc",problemDeviceEt.getText().toString());
        if (srcList != null){
            String imgs = srcList.toString().substring(1, srcList.toString().length() - 1);
            Logger.e(imgs);
            map.put("images",imgs);
        }

        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());
        Logger.e("报修提交的参数："+values.toString());

        OkGo.<ResultBean>post(Urls.order_add)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ResultBean>(this,ResultBean.class) {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {
                        ResultBean body = response.body();
                        if (TextUtils.equals("1",body.getStatus())){
                            TUtils.showShort(getApplicationContext(),body.getInfo());
                            SPUtils.getInstance().putBoolean("if_baoxiu",true);
                            SPUtils.getInstance().putString("first_name",deviceNameEt.getText().toString());
                            SPUtils.getInstance().putString("first_phone",devicePhoneEt.getText().toString());
                            SPUtils.getInstance().putString("first_company",deviceCtmcEt.getText().toString());
                            SPUtils.getInstance().putString("first_shengName",first_areaName);
                            SPUtils.getInstance().putString("first_shengId",sheng_id);
                            SPUtils.getInstance().putString("first_shiId",shi_id);
                            SPUtils.getInstance().putString("first_quId",qu_id);
                            SPUtils.getInstance().putString("first_address",detailAdressEt.getText().toString());
                            pd.cancel();
                            finish();
                        }else {
                            showTipsValidate(body.getInfo());
                        }
                    }
                });
    }

    private void showPopup() {
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_camera)//设置布局
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//设置宽高
                .setBackGroundLevel(0.5f)//设置背景颜色   取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)//设置动画
                .setOutsideTouchable(true)//设置外部是否可点击 默认是true
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        Button btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
                        Button btn_select_photo = (Button) view.findViewById(R.id.btn_select_photo);
                        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                        btn_take_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                    requestCamera();
                                }
                            }
                        });
                        btn_select_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                    requestPhoto();
                                }
                            }
                        });
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        view.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                                return true;
                            }
                        });
                    }
                })
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    private void requestPhoto() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(DeviceDetailsActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style1)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(200, 200)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
//                .selectionMedia(list)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void requestCamera() {
        PictureSelector.create(DeviceDetailsActivity.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .selectionMedia(list)// 是否传入已选图片
                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    list2.addAll(selectList);
                    selectList.clear();
                    if (list1.size() != 0){
                        list1.addAll(list2);
                        list2.clear();
                        addImgesAdpter.setList(list1);
                        addImgesAdpter.notifyDataSetChanged();
                    }else {
                        addImgesAdpter.setList(list2);
                        addImgesAdpter.notifyDataSetChanged();
                    }

                    break;
            }
        }
        /**
         * 选择品牌和类型返回的数据
         */
        if (requestCode == 101) {
            if (data != null) {
                brandNme = data.getStringExtra("brandName");
                typeNme = data.getStringExtra("typeName");
                brandId = data.getStringExtra("brandId");
                typeId = data.getStringExtra("typeId");
                pinpaiTv.setText(brandNme);
                leixingTv.setText(typeNme);
            }

        }
    }

    /**
     * 时间选择器
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1980, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030, 11, 31);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Date nowDate = TimeUtils.getNowDate();
                if (date.getTime() > nowDate.getTime()) {
                    TUtils.showShort(getApplicationContext(), "只能选择当前日期之前的日期");
                    return;
                }
                dateTv.setText(getTime(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_date_layout, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                    }
                })
                .isCyclic(true)//是否循环滚动
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.hint))
                .setTextColorOut(getResources().getColor(R.color.hint))
                .setBgColor(getResources().getColor(R.color.white01))
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                .setTextColorCenter(getResources().getColor(R.color.text_color01)) //设置选中项文字颜色
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    public void onAreaClickListener(String areaName, String sheng_id, String shi_id, String qu_id) {
        deviceAdressTv.setText(areaName);
        first_areaName = areaName;
        this.sheng_id=sheng_id;
        this.shi_id=shi_id;
        this.qu_id=qu_id;
        selectedAreaPop.dismiss();
    }

    @Override
    public void onAreaClearListener() {
        selectedAreaPop.dismiss();
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener);//注册监听函数

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            int errorCode = location.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            Logger.e(errorCode+"");
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            mLocationClient.stop();
            if (errorCode == 61 || errorCode == 66 || errorCode == 161){
                SPUtils.getInstance().putString("latitude",String.valueOf(latitude));
                SPUtils.getInstance().putString("longitude",String.valueOf(longitude));
                Logger.e("纬度："+latitude+"\n经度："+longitude);
                requestOrderAdress();
            }else {
                TUtils.showShort(getApplicationContext(),"百度地图定位失败，请检查网络操作后重试。");
            }
        }
    }

    private void requestOrderAdress() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id",SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("lat",SPUtils.getInstance().getString("latitude",""));
        map.put("lng",SPUtils.getInstance().getString("longitude",""));
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());
        Logger.e("详细地址提交的参数："+values.toString());
        OkGo.<OrderAdressBean>post(Urls.order_adress)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<OrderAdressBean>(DeviceDetailsActivity.this,OrderAdressBean.class) {
                    @Override
                    public void onSuccess(Response<OrderAdressBean> response) {
                        OrderAdressBean orderAdressBean = response.body();
                        if (TextUtils.equals("1",orderAdressBean.getStatus())){
                            String sheng_name = orderAdressBean.getData().getSheng_name();
                            sheng_id = orderAdressBean.getData().getSheng_id();
                            String shi_name = orderAdressBean.getData().getShi_name();
                            shi_id = orderAdressBean.getData().getShi_id();
                            String qu_name = orderAdressBean.getData().getQu_name();
                            qu_id = orderAdressBean.getData().getQu_id();
                            deviceAdressTv.setText(sheng_name+" "+shi_name+" "+qu_name);
                        }else {
                            TUtils.showShort(getApplicationContext(),orderAdressBean.getInfo());
                        }
                    }
                });
    }


}
