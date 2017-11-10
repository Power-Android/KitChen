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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.adapter.GridViewAddImgesAdpter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.AreaListsBean;
import com.power.kitchen.bean.CitysBean;
import com.power.kitchen.bean.CitysJsonBean;
import com.power.kitchen.bean.EditFaceBean;
import com.power.kitchen.bean.JsonBean;
import com.power.kitchen.bean.OrderInfoBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.bean.UpLoadImgBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.CommonPopupWindow;
import com.power.kitchen.utils.GetJsonDataUtil;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.TimeUtils;
import com.power.kitchen.utils.Urls;
import com.power.kitchen.view.MyGridView;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONArray;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备详情
 * power
 * 2017年10月18日 13:17:32
 */

public class DeviceDetailsActivity extends BaseActivity {

    @BindView(R.id.gridview) MyGridView gridview;@BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;@BindView(R.id.device_txm_et) EditText deviceTxmEt;
    @BindView(R.id.date_tv) TextView dateTv;@BindView(R.id.jump_date_iv) ImageView jumpDateIv;
    @BindView(R.id.bxqn_rbt) RadioButton bxqnRbt;@BindView(R.id.bxqw_rbt) RadioButton bxqwRbt;
    @BindView(R.id.pinpai_tv) TextView pinpaiTv;@BindView(R.id.jump_pinpai_iv) ImageView jumpPinpaiIv;
    @BindView(R.id.leixing_tv) TextView leixingTv;@BindView(R.id.jump_leixing_iv) ImageView jumpLeixingIv;
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
    String temp = "1";//默认，保修期内“1”，保修期外“0”

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;
    private OptionsPickerView pvOptions;
    private TimePickerView pvCustomLunar;
    private ArrayList<String> listExtra;//拍照报修传过来的图片路径
    private Intent intent;
    private String brandNme, typeNme, brandId, typeId, tx, tips;
    private List<AreaListsBean.DataBean> province_list = new ArrayList<>();
    private OrderInfoBean orderInfoBean;
    private List<String> srcList = new ArrayList<>();
    private int size = 0;

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
                Logger.e(srcList.toString());
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
    }

    private void initView() {
        contentTv.setText("设备详情");
        initListener();
        getIntentData();//上级拍照返回来的数据----图片的path路径
        initJsonData();//解析json数据----地址选择器
        initLunarPicker();//初始化时间选择器
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
            leixingTv.setText(orderInfoBean.getData().getInfo().getGoods_type_name());
            xinghaoEt.setText(orderInfoBean.getData().getInfo().getGoods_model());
            deviceNameEt.setText(orderInfoBean.getData().getInfo().getContact_name());
            devicePhoneEt.setText(orderInfoBean.getData().getInfo().getContact_mobile());
            contentTv.setText(orderInfoBean.getData().getInfo().getContact_company());
            deviceAdressTv.setText(orderInfoBean.getData().getInfo().getContact_sheng_name() + " " +
                    orderInfoBean.getData().getInfo().getContact_shi_name() + " " +
                    orderInfoBean.getData().getInfo().getContact_qu_name());
            String contact_sheng_id = orderInfoBean.getData().getInfo().getContact_sheng_id();
            String contact_shi_id = orderInfoBean.getData().getInfo().getContact_shi_id();
            String contact_qu_id = orderInfoBean.getData().getInfo().getContact_qu_id();
            detailAdressEt.setText(orderInfoBean.getData().getInfo().getContact_address());
            problemDeviceEt.setText(orderInfoBean.getData().getInfo().getGoods_describe());
            String goods_images = orderInfoBean.getData().getInfo().getGoods_images();
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
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.jump_date_iv://选择购买日期
                pvCustomLunar.show();
                break;
            case R.id.jump_pinpai_iv://选择品牌
                intent = new Intent();
                intent.setClass(DeviceDetailsActivity.this, BrandAndTypeActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.jump_leixing_iv://选择类型
                intent.setClass(DeviceDetailsActivity.this, BrandAndTypeActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.jump_adress_iv://选择所在区域
                ShowPickerView();
                break;
            case R.id.location_iv://定位
                ShowPickerView();
                break;
            case R.id.cancle_btn://取消报修
                showTips();
                break;
            case R.id.query_btn://确认报修
                if (validate()) {
                    requestUploadImg();
                }
                break;
        }
    }
    private String src;
    private void requestUploadImg() {
        if (list.size() != 0){
            int listSize = list.size();
            if (size <= listSize - 1){
                String path = list.get(size).getPath();
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
        if (TextUtils.isEmpty(tx)) {
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
        //TODO 省市区ID 记得更换
        map.put("sheng_id","2");
        map.put("shi_id","33");
        map.put("qu_id","378");
        map.put("address",detailAdressEt.getText().toString());
        map.put("bd_lat",SPUtils.getInstance().getString("latitude",""));
        map.put("bd_lng",SPUtils.getInstance().getString("longitude",""));
        map.put("code",deviceTxmEt.getText().toString());
        map.put("date",dateTv.getText().toString());
        map.put("is_warranty",temp);
        map.put("brand_id",brandId);
        map.put("type_id",typeId);
        map.put("model",xinghaoEt.getText().toString());
        map.put("desc",problemDeviceEt.getText().toString());

        String imgs1 = srcList.toString().substring(1, srcList.toString().length() - 1);
        Logger.e(imgs1);

        map.put("images",imgs1);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<ResultBean>post(Urls.order_add)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ResultBean>(this,ResultBean.class) {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {

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
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
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
                    list.addAll(selectList);
                    addImgesAdpter.setList(list);
                    addImgesAdpter.notifyDataSetChanged();
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
        startDate.set(2000, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2050, 11, 31);
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
                tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                deviceAdressTv.setText(tx);
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
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
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
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
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

    private ArrayList<CitysBean> options1Items1 = new ArrayList<>();
    private ArrayList<CitysJsonBean> options2Items1 = new ArrayList<>();
    private ArrayList<CitysJsonBean> options3Items1 = new ArrayList<>();


    /**
     * 解析数据
     */
    /*private void initJsonData1() {
        *//**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * *//*
        String JsonData = new GetJsonDataUtil().getJson(this, "citys.json");//获取assets目录下的json文件数据

        ArrayList<CitysBean> jsonBean = parseData1(JsonData);//用Gson 转成实体

        *//**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         *//*
        options1Items1 = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<CitysJsonBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<CitysJsonBean> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getSon_lists().size(); c++) {//遍历该省份的所有城市
                CitysJsonBean citysJsonBean = new CitysJsonBean();
                citysJsonBean.setName(jsonBean.get(i).getSon_lists().get(c).getName());
                citysJsonBean.setArea_id(jsonBean.get(i).getSon_lists().get(c).getArea_id());
                CityList.add(citysJsonBean);//添加城市

                ArrayList<CitysJsonBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                for (int d = 0; d < jsonBean.get(i).getSon_lists().get(c).getSon_lists().size(); d++) {//该城市对应地区所有数据
                    CitysJsonBean citysJsonBean1 = new CitysJsonBean();
                    citysJsonBean1.setName(jsonBean.get(i).getSon_lists().get(c).getSon_lists().get(d).getName());
                    citysJsonBean1.setArea_id(jsonBean.get(i).getSon_lists().get(c).getSon_lists().get(d).getArea_id());

                    City_AreaList.add(citysJsonBean1);//添加该城市所有地区数据
                }
                Province_AreaList.addAll(CityList);//添加该省所有地区数据
            }

            *//**
             * 添加城市数据
             *//*
            options2Items1.addAll(CityList);

            *//**
             * 添加地区数据
             *//*
            options3Items1.addAll(CityList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }*/

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

    /*public ArrayList<CitysBean> parseData1(String result) {//Gson 解析
        ArrayList<CitysBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CitysBean entity = gson.fromJson(data.optJSONObject(i).toString(), CitysBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }*/

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
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
                    Toast.makeText(getApplicationContext(), "地址选择器解析数据失败", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
}
