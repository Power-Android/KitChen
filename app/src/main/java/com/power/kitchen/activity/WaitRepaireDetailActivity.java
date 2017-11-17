package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.adapter.GridViewAdapter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.OrderInfoBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TimeUtils;
import com.power.kitchen.utils.Urls;
import com.power.kitchen.view.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

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

public class WaitRepaireDetailActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.ggt_iv) RelativeLayout ggtIv;
    @BindView(R.id.ggt_cancle_iv) ImageView ggtCancleIv;
    @BindView(R.id.title_reason_tv) TextView titleReasonTv;
    @BindView(R.id.yijiedan_view01) LinearLayout yijiedanView01;
    @BindView(R.id.qx_reason_tv) TextView qxReasonTv;
    @BindView(R.id.qx_view02) View qxView02;
    @BindView(R.id.quxiao_view01) LinearLayout quxiaoView01;
    @BindView(R.id.view01) View view01;
    @BindView(R.id.device_txm_et) TextView deviceTxmEt;
    @BindView(R.id.weiwancheng_view01) LinearLayout weiwanchengView01;
    @BindView(R.id.pay_money_tv) TextView payMoneyTv;
    @BindView(R.id.pay_fangshi_tv) TextView payFangshiTv;
    @BindView(R.id.pay_time_tv) TextView payTimeTv;
    @BindView(R.id.yiwancheng_view01) LinearLayout yiwanchengView01;
    @BindView(R.id.date_tv) TextView dateTv;
    @BindView(R.id.shifu_phone) TextView shifuPhone;
    @BindView(R.id.shifu_gs_tv) TextView shifuGsTv;
    @BindView(R.id.jiedan_time_tv) TextView jiedanTimeTv;
    @BindView(R.id.gs_juli_tv) TextView gsJuliTv;
    @BindView(R.id.weiwancheng_view02) LinearLayout weiwanchengView02;
    @BindView(R.id.yijiedan_view) View yijiedanView;
    @BindView(R.id.bx_time) TextView bxTime;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.txm_tv) TextView txmTv;
    @BindView(R.id.goumai_time_tv) TextView goumaiTimeTv;
    @BindView(R.id.pinpai_tv) TextView pinpaiTv;
    @BindView(R.id.bxq_tv) TextView bxqTv;
    @BindView(R.id.type_tv) TextView typeTv;
    @BindView(R.id.xinghao_tv) TextView xinghaoTv;
    @BindView(R.id.name_tv) TextView nameTv;
    @BindView(R.id.phone_tv) TextView phoneTv;
    @BindView(R.id.cantingname_tv) TextView cantingnameTv;
    @BindView(R.id.adress_tv) TextView adressTv;
    @BindView(R.id.detail_adress_tv) TextView detailAdressTv;
    @BindView(R.id.problem_device_et) TextView problemDeviceEt;
    @BindView(R.id.gridview) MyGridView gridview;
    @BindView(R.id.shebeitupian_ll) LinearLayout shebeitupianLl;
    @BindView(R.id.pingjia_chengdu_tv) TextView pingjiaChengduTv;
    @BindView(R.id.pingjia_miaoshu_tv) TextView pingjiaMiaoshuTv;
    @BindView(R.id.pingjia_ll) LinearLayout pingjiaLl;
    @BindView(R.id.query_btn) Button queryBtn;
    @BindView(R.id.query_ll) LinearLayout queryLl;
    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.wentimiaoshu_ll) LinearLayout wentimiaoshuLl;
    @BindView(R.id.if_jiedan_tv) TextView ifJieDanTv;
    @BindView(R.id.if_jiedan_content_tv) TextView ifJieDanContentTv;
    private UltimateBar ultimateBar;
    private String oid;

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
    }

    private void initView() {
        backIv.setOnClickListener(this);
        ggtCancleIv.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        yijiedanView.setVisibility(View.GONE);
        yijiedanView01.setVisibility(View.VISIBLE);
        weiwanchengView01.setVisibility(View.VISIBLE);
        weiwanchengView02.setVisibility(View.VISIBLE);
        quxiaoView01.setVisibility(View.GONE);
        yiwanchengView01.setVisibility(View.GONE);
        oid = getIntent().getStringExtra("oid");
        String order_accept_name = getIntent().getStringExtra("order_accept_name");
        contentTv.setText(order_accept_name);
        String status_accept = getIntent().getStringExtra("status_accept");
        requestOrderInfo(status_accept);
    }

    private void requestOrderInfo(final String status_accept) {
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
                    @Override
                    public void onSuccess(Response<OrderInfoBean> response) {
                        OrderInfoBean orderInfoBean = response.body();
                        if (TextUtils.equals("1", orderInfoBean.getStatus())) {
                            //未接单
                            if (TextUtils.equals("0",status_accept)){
                                queryBtn.setText("取消报修");
                                queryLl.setVisibility(View.VISIBLE);
                                weiwanchengView02.setVisibility(View.GONE);
                            }else {
                                ifJieDanTv.setText("您的报修已被接单");
                                ifJieDanContentTv.setText("维修工作人员将会与您电话取得联系");
                                ifJieDanContentTv.setGravity(Gravity.CENTER_HORIZONTAL);
                            }

                            payMoneyTv.setText(orderInfoBean.getData().getInfo().getPrice());
                            payFangshiTv.setText(orderInfoBean.getData().getInfo().getPay_type_name());
                            payTimeTv.setText(TimeUtils.getStrTimeYMD(orderInfoBean.getData().getInfo().getPay_time()));
                            titleReasonTv.setText(orderInfoBean.getData().getInfo().getNo_weixiu_info());
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
                                    GridViewAdapter adapter = new GridViewAdapter(WaitRepaireDetailActivity.this, list);
                                    gridview.setAdapter(adapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                shebeitupianLl.setVisibility(View.GONE);
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
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.ggt_cancle_iv:
                ggtIv.setVisibility(View.GONE);
                break;
            case R.id.query_btn:
                Intent intent = new Intent(this,CancleRepaireActivity.class);
                intent.putExtra("oid",oid);
                startActivity(intent);
                finish();
                break;
        }
    }
}
