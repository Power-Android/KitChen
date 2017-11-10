package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.adapter.AdressManageAdapter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.AdressManageBean;
import com.power.kitchen.bean.AreaListBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.EmptyCallback;
import com.power.kitchen.callback.ErrorCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.callback.LoadingCallback;
import com.power.kitchen.callback.TimeoutCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;
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

public class AdressManageActivity extends BaseActivity implements AdressManageAdapter.onItemDeleteListener{

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.title_right_tv) TextView titleRightTv;
    @BindView(R.id.title_msg) ImageView titleMsg;
    @BindView(R.id.adress_list) ListView adressList;

    private UltimateBar ultimateBar;
    List<AreaListBean.DataBean.ListsBean> list = new ArrayList<>();
    private LoadService loadService;
    private AdressManageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);
        initLoad();
        initViewAndListener();
    }

    /**
     * 加载状态页
     */
    private void initLoad() {
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        //重新加载逻辑
        loadService = loadSir.register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                //重新加载逻辑
                loadService.showCallback(LoadingCallback.class);
                requestAreaList();
            }
        });
    }

    private void initViewAndListener() {
        contentTv.setText("地址管理");
        titleRightTv.setText("添加地址");
        titleRightTv.setVisibility(View.VISIBLE);
        backIv.setOnClickListener(this);
        titleRightTv.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestAreaList();
    }

    private void requestAreaList() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("p","1");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<AreaListBean>post(Urls.area_list)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<AreaListBean>(AreaListBean.class) {
                    @Override
                    public void onSuccess(Response<AreaListBean> response) {
                        AreaListBean areaListBean = response.body();
                        if (TextUtils.equals("1",areaListBean.getStatus())){
                            list = areaListBean.getData().getLists();
                            if (TextUtils.equals("0",list.size()+"")){
                                loadService.showCallback(EmptyCallback.class);
                            }else {
                                adapter = new AdressManageAdapter(AdressManageActivity.this,list);
                                adressList.setAdapter(adapter);
                                loadService.showSuccess();
                                adapter.setOnItemDeleteClickListener(AdressManageActivity.this);
                            }
                        }else {
                            TUtils.showShort(getApplicationContext(),areaListBean.getInfo());
                            loadService.showCallback(ErrorCallback.class);
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
                startActivity(new Intent(this,AddAdressActivity.class));
                break;

        }
    }
    private int position;
    @Override
    public void onDeleteClick(int position) {
        this.position = position;
        showTips();
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

    private void requestAreaDel() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("area_id",list.get(position).getArea_id());
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
                            onResume();
                            TUtils.showShort(getApplicationContext(),"地址删除成功！");
                        }else {
                            TUtils.showShort(getApplicationContext(),resultBean.getInfo());
                        }
                    }
                });
    }


}
