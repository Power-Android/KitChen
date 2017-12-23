package com.power.kitchen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.adapter.GridViewAdapter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.AliPayAcceptBean;
import com.power.kitchen.bean.OrderInfoBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.bean.WeChatBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.AuthResult;
import com.power.kitchen.utils.PayResult;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.TimeUtils;
import com.power.kitchen.utils.Urls;
import com.power.kitchen.view.MyGridView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/27.
 */

public class AlreadyRepaireDetailActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;@BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.ggt_iv) RelativeLayout ggtIv;@BindView(R.id.ggt_cancle_iv) ImageView ggtCancleIv;
    @BindView(R.id.title_reason_tv) TextView titleReasonTv;
    @BindView(R.id.qx_view02) View qxView02;@BindView(R.id.quxiao_view01) LinearLayout quxiaoView01;
    @BindView(R.id.view01) View view01;@BindView(R.id.device_txm_et) TextView deviceTxmEt;
    @BindView(R.id.weiwancheng_view01) LinearLayout weiwanchengView01;@BindView(R.id.pay_money_tv) TextView payMoneyTv;
    @BindView(R.id.pay_fangshi_tv) TextView payFangshiTv;@BindView(R.id.pay_time_tv) TextView payTimeTv;
    @BindView(R.id.yiwancheng_view01) LinearLayout yiwanchengView01;@BindView(R.id.date_tv) TextView dateTv;
    @BindView(R.id.shifu_phone) TextView shifuPhone;@BindView(R.id.shifu_gs_tv) TextView shifuGsTv;
    @BindView(R.id.jiedan_time_tv) TextView jiedanTimeTv;@BindView(R.id.gs_juli_tv) TextView gsJuliTv;
    @BindView(R.id.weiwancheng_view02) LinearLayout weiwanchengView02;@BindView(R.id.bx_time) TextView bxTime;
    @BindView(R.id.textView) TextView textView;@BindView(R.id.txm_tv) TextView txmTv;
    @BindView(R.id.goumai_time_tv) TextView goumaiTimeTv;@BindView(R.id.pinpai_tv) TextView pinpaiTv;
    @BindView(R.id.bxq_tv) TextView bxqTv;@BindView(R.id.type_tv) TextView typeTv;
    @BindView(R.id.xinghao_tv) TextView xinghaoTv;@BindView(R.id.name_tv) TextView nameTv;
    @BindView(R.id.phone_tv) TextView phoneTv;@BindView(R.id.cantingname_tv) TextView cantingnameTv;
    @BindView(R.id.adress_tv) TextView adressTv;@BindView(R.id.detail_adress_tv) TextView detailAdressTv;
    @BindView(R.id.problem_device_et) TextView problemDeviceEt;@BindView(R.id.gridview) MyGridView gridview;
    @BindView(R.id.query_btn) Button queryBtn;@BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.shebeitupian_ll) LinearLayout shebeiTupianLL;@BindView(R.id.pingjia_ll) LinearLayout pingjiaView;
    @BindView(R.id.pingjia_chengdu_tv) TextView pingjiaChengduTv;@BindView(R.id.pingjia_miaoshu_tv) TextView pingjiaMiaoshuTv;
    @BindView(R.id.wentimiaoshu_ll) LinearLayout wentimiaoshuLl;@BindView(R.id.query_ll) LinearLayout queryLl;
    @BindView(R.id.yiweixiu_daizhifu_view) LinearLayout yiweixiuDaizhifuView;
    @BindView(R.id.weichat_rl) RelativeLayout weichatRl;@BindView(R.id.weichat_select_iv) ImageView weichatSelectIv;
    @BindView(R.id.albb_rl) RelativeLayout albbRl;@BindView(R.id.albb_select_iv) ImageView albbSelectIv;
    @BindView(R.id.yinlian_rl) RelativeLayout yinlianRl;@BindView(R.id.yinlian_select_iv) ImageView yinlianSelectIv;
    @BindView(R.id.pay_tv) TextView payTv;@BindView(R.id.yijiedan_view01) LinearLayout yijiedanView01;
    @BindView(R.id.pay_typeName) TextView payTypeNameTv;

    private UltimateBar ultimateBar;
    private String oid, status_pay, flag;
    private String orderInfo;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String goods_is_warranty;
    private IWXAPI api;
    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_not_repaire_detail);
        ButterKnife.bind(this);
        initView();
        api = WXAPIFactory.createWXAPI(this,"wx7556840f3ffa87a0");
    }

    private void initView() {
        status_pay = getIntent().getStringExtra("status_pay");
        goods_is_warranty = getIntent().getStringExtra("goods_is_warranty");
        if (TextUtils.equals("0", status_pay) && TextUtils.equals("0", goods_is_warranty)){
            contentTv.setText("待支付");
            yiweixiuDaizhifuView.setVisibility(View.VISIBLE);
            queryBtn.setText("去支付");
        }else {
            contentTv.setText("已完成");
            yiwanchengView01.setVisibility(View.VISIBLE);
        }
        quxiaoView01.setVisibility(View.GONE);
        yijiedanView01.setVisibility(View.GONE);
        titleReasonTv.setVisibility(View.VISIBLE);
        weiwanchengView01.setVisibility(View.VISIBLE);
        weiwanchengView02.setVisibility(View.VISIBLE);
        titleReasonTv.setText("您的报修已完成，感谢您的理解与支持。");
        backIv.setOnClickListener(this);
        ggtCancleIv.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        weichatRl.setOnClickListener(this);
        albbRl.setOnClickListener(this);
        yinlianRl.setOnClickListener(this);
        shifuPhone.setOnClickListener(this);
        phoneTv.setOnClickListener(this);
        String commentOid = getIntent().getStringExtra("comment_oid");
        if (!TextUtils.isEmpty(commentOid)){
            requestOrderInfo(commentOid);
        }else {
            oid = getIntent().getStringExtra("oid");
            requestOrderInfo(oid);
        }
    }

    private void requestOrderInfo(String oid) {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id", ""));
        map.put("token", SPUtils.getInstance().getString("token", ""));
        map.put("id", SPUtils.getInstance().getString("id", ""));
        map.put("oid", oid);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data", values.toString());

        OkGo.<OrderInfoBean>post(Urls.order_info)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<OrderInfoBean>(this, OrderInfoBean.class) {

                    private float peijian_privice;

                    @Override
                    public void onSuccess(Response<OrderInfoBean> response) {
                        OrderInfoBean orderInfoBean = response.body();
                        if (TextUtils.equals("1",goods_is_warranty)){
                            yiwanchengView01.setVisibility(View.GONE);
                        }
                        if (TextUtils.equals("1", orderInfoBean.getStatus())) {
                            String status_comment = orderInfoBean.getData().getInfo().getStatus_comment();
                            if (TextUtils.equals("1",status_comment)){
                                queryLl.setVisibility(View.GONE);
                                pingjiaView.setVisibility(View.VISIBLE);
                                pingjiaChengduTv.setText(orderInfoBean.getData().getComment().getLevel_name());
                                if (TextUtils.isEmpty(orderInfoBean.getData().getComment().getContent())){
                                    pingjiaMiaoshuTv.setVisibility(View.GONE);
                                }else {
                                    pingjiaMiaoshuTv.setText(orderInfoBean.getData().getComment().getContent());
                                }
                            } else if (TextUtils.equals("0",status_comment) && TextUtils.equals("0",status_pay) && TextUtils.equals("0", goods_is_warranty)){
                                queryBtn.setText("去支付");
                                queryLl.setVisibility(View.VISIBLE);
                            }else {
                                queryBtn.setText("去评价");
                                queryLl.setVisibility(View.VISIBLE);
                            }
                            float price = Float.parseFloat(orderInfoBean.getData().getInfo().getPrice());
                            if (!TextUtils.isEmpty(orderInfoBean.getData().getInfo().getPeijian_price())){
                                peijian_privice = Float.parseFloat(orderInfoBean.getData().getInfo().getPeijian_price());
                            }
                            float money = price + peijian_privice;
                            payMoneyTv.setText("￥"+price);
                            payTv.setText("￥"+price);
                            payFangshiTv.setText(orderInfoBean.getData().getInfo().getPay_type_name());
                            payTimeTv.setText(TimeUtils.getStrTimeYMD(orderInfoBean.getData().getInfo().getPay_time()));
                            deviceTxmEt.setText(orderInfoBean.getData().getInfo().getOid());
                            dateTv.setText(orderInfoBean.getData().getM_w_info().getName());
                            shifuPhone.setText(orderInfoBean.getData().getM_w_info().getMobile());
                            shifuGsTv.setText(orderInfoBean.getData().getM_d_info().getCompany_name());
                            jiedanTimeTv.setText(TimeUtils.getStrTimeYMD(orderInfoBean.getData().getInfo().getAccept_time()));
                            String getKm = orderInfoBean.getData().getInfo().getContact_juli();
                            double m_double = Double.parseDouble(getKm);
                            double km_double = m_double/1000;
                            DecimalFormat df = new DecimalFormat(".0");
                            String km = df.format(km_double);
                            gsJuliTv.setText(km + "KM");
                            bxTime.setText(TimeUtils.getStrTimeYMD(orderInfoBean.getData().getInfo().getCreate_time()));
                            txmTv.setText(orderInfoBean.getData().getInfo().getGoods_code());
                            goumaiTimeTv.setText(orderInfoBean.getData().getInfo().getGoods_date());
                            pinpaiTv.setText(orderInfoBean.getData().getInfo().getGoods_brand_name());

                            String goods_is_warranty = orderInfoBean.getData().getInfo().getGoods_is_warranty();
                            if (TextUtils.equals("1", goods_is_warranty)) {
                                bxqTv.setText("保修期内");
                            } else {
                                bxqTv.setText("保修期外");
                            }

                            String goods_images = orderInfoBean.getData().getInfo().getGoods_images();
                            if (!TextUtils.equals("[]",goods_images)){
                                try {
                                    JSONArray good_images = new JSONArray(goods_images);
                                    List<String> list = new ArrayList<String>();
                                    for (int i = 0; i < good_images.length(); i++) {
                                        String s = (String) good_images.get(i);
                                        list.add(s);
                                    }
                                    GridViewAdapter adapter = new GridViewAdapter(AlreadyRepaireDetailActivity.this, list);
                                    gridview.setAdapter(adapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                shebeiTupianLL.setVisibility(View.GONE);
                            }



                            typeTv.setText(orderInfoBean.getData().getInfo().getGoods_type_name());
                            xinghaoTv.setText(orderInfoBean.getData().getInfo().getGoods_model());
                            nameTv.setText(orderInfoBean.getData().getInfo().getContact_name());
                            phoneTv.setText(orderInfoBean.getData().getInfo().getContact_mobile());
                            cantingnameTv.setText(orderInfoBean.getData().getInfo().getContact_company());
                            adressTv.setText(orderInfoBean.getData().getInfo().getContact_sheng_name() + " " +
                                    orderInfoBean.getData().getInfo().getContact_shi_name() + " " +
                                    orderInfoBean.getData().getInfo().getContact_qu_name());
                            detailAdressTv.setText(orderInfoBean.getData().getInfo().getContact_address());
                            if (TextUtils.isEmpty(orderInfoBean.getData().getInfo().getGoods_describe())){
                                wentimiaoshuLl.setVisibility(View.GONE);
                            }else {
                                problemDeviceEt.setText(orderInfoBean.getData().getInfo().getGoods_describe());
                            }

                            scrollView.smoothScrollBy(0, 0);
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
            case R.id.query_btn:
                if (TextUtils.equals("去支付",queryBtn.getText().toString())){
                    if (TextUtils.equals("0",status_pay) && TextUtils.equals("0",goods_is_warranty)){
                        if (TextUtils.equals("1",flag)){
                            if (isWeixinAvilible(this)){
                                requestWeChatPay();
                            }
                        }
                        if (TextUtils.equals("2",flag)){
                            requestAliPay();
                        }
                        if (TextUtils.equals("3",flag)){
                            TUtils.showShort(getApplicationContext(),"银联支付");
                        }
                    }
                }else {
                    Intent intent = new Intent(this,RepaireCommentActivity.class);
                    intent.putExtra("oid",oid);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.ggt_cancle_iv:
                ggtIv.setVisibility(View.GONE);
                break;
            case R.id.weichat_rl:
                albbSelectIv.setVisibility(View.GONE);
                yinlianSelectIv.setVisibility(View.GONE);
                weichatSelectIv.setVisibility(View.VISIBLE);
                flag = "1";
                break;
            case R.id.albb_rl:
                weichatSelectIv.setVisibility(View.GONE);
                yinlianSelectIv.setVisibility(View.GONE);
                albbSelectIv.setVisibility(View.VISIBLE);
                flag = "2";
                break;
            case R.id.yinlian_rl:
                weichatSelectIv.setVisibility(View.GONE);
                albbSelectIv.setVisibility(View.GONE);
                yinlianSelectIv.setVisibility(View.VISIBLE);
                flag = "3";
                break;
            case R.id.shifu_phone:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+shifuPhone.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.phone_tv:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneTv.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    //判断用户是否安装微信
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void requestWeChatPay() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id",SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("type","weixiu");
        map.put("oid",oid);
        map.put("ip","127.0.0.0");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<WeChatBean>post(Urls.wechat_pay)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<WeChatBean>(WeChatBean.class) {
                    @Override
                    public void onSuccess(Response<WeChatBean> response) {
                        WeChatBean weChatBean = response.body();
                        if (TextUtils.equals("1",weChatBean.getStatus())){

                            PayReq request = new PayReq();
                            request.appId = weChatBean.getData().getAppid();
                            request.partnerId = weChatBean.getData().getPartnerid();
                            request.prepayId= weChatBean.getData().getPrepayid();
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr= weChatBean.getData().getNoncestr();
                            request.timeStamp= weChatBean.getData().getTimestamp();
                            request.sign= weChatBean.getData().getSign();
                            api.sendReq(request);
                            finish();
                        }
                    }
                });
    }

    private void requestAliPay() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id",SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("type","weixiu");
        map.put("oid",oid);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<AliPayAcceptBean>post(Urls.alipay_data)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<AliPayAcceptBean>(AliPayAcceptBean.class) {
                    @Override
                    public void onSuccess(Response<AliPayAcceptBean> response) {
                        AliPayAcceptBean aliPayAcceptBean = response.body();
                        if (TextUtils.equals("1",aliPayAcceptBean.getStatus())){
                            orderInfo = aliPayAcceptBean.getData().getText();
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(AlreadyRepaireDetailActivity.this);
                                    Map<String, String> result = alipay.payV2(orderInfo,true);

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();

                        }else {
                            showTipsValidate(aliPayAcceptBean.getInfo());
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(AlreadyRepaireDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AlreadyRepaireDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

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
                        finish();
                    }
                })
                .build()
                .show();
    }

}
