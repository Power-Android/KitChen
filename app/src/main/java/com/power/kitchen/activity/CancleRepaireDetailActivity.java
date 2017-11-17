package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/20.
 */

public class CancleRepaireDetailActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.ggt_iv) RelativeLayout ggtIv;
    @BindView(R.id.ggt_cancle_iv) ImageView ggtCancleIv;
    @BindView(R.id.title_reason_tv) TextView titleReasonTv;
    @BindView(R.id.qx_reason_tv) TextView qxReasonTv;
    @BindView(R.id.quxiao_view01) LinearLayout quxiaoView01;
    @BindView(R.id.device_txm_et) TextView deviceTxmEt;
    @BindView(R.id.weiwancheng_view01) LinearLayout weiwanchengView01;
    @BindView(R.id.date_tv) TextView dateTv;
    @BindView(R.id.weiwancheng_view02) LinearLayout weiwanchengView02;
    @BindView(R.id.problem_device_et) TextView problemDeviceEt;
    @BindView(R.id.gridview) MyGridView gridview;
    @BindView(R.id.query_btn) Button queryBtn;
    @BindView(R.id.bx_time) TextView bxTimeTv;
    @BindView(R.id.txm_tv) TextView txmTv;
    @BindView(R.id.goumai_time_tv)  TextView goumaiTimeTv;
    @BindView(R.id.pinpai_tv) TextView pinpaiTv;
    @BindView(R.id.bxq_tv) TextView bxqTv;
    @BindView(R.id.type_tv) TextView typeTv;
    @BindView(R.id.xinghao_tv) TextView xinghaoTv;
    @BindView(R.id.name_tv) TextView nameTv;
    @BindView(R.id.phone_tv) TextView phoneTv;
    @BindView(R.id.cantingname_tv) TextView cantingnameTv;
    @BindView(R.id.adress_tv) TextView adressTv;
    @BindView(R.id.detail_adress_tv) TextView detaileAdressTv;
    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.shebeitupian_ll) LinearLayout shebeiTupianLL;
    @BindView(R.id.wentimiaoshu_ll) LinearLayout wentimiaoshuLl;
    @BindView(R.id.query_ll) LinearLayout queryLl;


    private UltimateBar ultimateBar;
    private String oid;
    private OrderInfoBean orderInfoBean;
    public static boolean flag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        oid = getIntent().getStringExtra("oid");
        requestOrderInfo();
        contentTv.setText("已取消");
        weiwanchengView01.setVisibility(View.GONE);
        weiwanchengView02.setVisibility(View.GONE);
        quxiaoView01.setVisibility(View.VISIBLE);
        queryBtn.setText("重新报修");
        titleReasonTv.setText("报修已取消，若需要维修，请重新报修");
        backIv.setOnClickListener(this);
        ggtCancleIv.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        scrollView.smoothScrollBy(0,0);
    }

    private void requestOrderInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token", SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("oid",oid);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<OrderInfoBean>post(Urls.order_info)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<OrderInfoBean>(this,OrderInfoBean.class) {
                    @Override
                    public void onSuccess(Response<OrderInfoBean> response) {
                        orderInfoBean = response.body();
                        if (TextUtils.equals("1", orderInfoBean.getStatus())){
                            qxReasonTv.setText(orderInfoBean.getData().getInfo().getQuxiao_info());
                            bxTimeTv.setText(TimeUtils.getStrTimeYMD(orderInfoBean.getData().getInfo().getCreate_time()));
                            txmTv.setText(orderInfoBean.getData().getInfo().getGoods_code());
                            goumaiTimeTv.setText(orderInfoBean.getData().getInfo().getGoods_date());
                            pinpaiTv.setText(orderInfoBean.getData().getInfo().getGoods_brand_name());

                            String goods_is_warranty = orderInfoBean.getData().getInfo().getGoods_is_warranty();
                            if (TextUtils.equals("1",goods_is_warranty)){
                                bxqTv.setText("保修期内");
                            }else {
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
                                    Logger.e(list.toString());
                                    GridViewAdapter adapter = new GridViewAdapter(CancleRepaireDetailActivity.this,list);
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
                                orderInfoBean.getData().getInfo().getContact_shi_name()+ " " +
                                    orderInfoBean.getData().getInfo().getContact_qu_name());
                            detaileAdressTv.setText(orderInfoBean.getData().getInfo().getContact_address());
                            if (TextUtils.isEmpty(orderInfoBean.getData().getInfo().getGoods_describe())){
                                wentimiaoshuLl.setVisibility(View.GONE);
                            }else {
                                problemDeviceEt.setText(orderInfoBean.getData().getInfo().getGoods_describe());
                            }
                            queryBtn.setText("重新报修");
                            queryLl.setVisibility(View.VISIBLE);
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
            case R.id.query_btn:
                flag = true;
                Intent mIntent = DeviceDetailsActivity.newIntent(CancleRepaireDetailActivity.this,orderInfoBean);
                startActivity(mIntent);
                finish();
                break;
            case R.id.ggt_cancle_iv:
                ggtIv.setVisibility(View.GONE);
                break;
        }
    }
}
