package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.app.MyApplication;
import com.power.kitchen.bean.BackgroundBean;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.fragment.PersonCenterFragment;
import com.power.kitchen.fragment.RepairFragment;
import com.power.kitchen.fragment.RepairRecordsFragment;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_content) LinearLayout tabContent;
    @BindView(R.id.tab_bxjl_iv) ImageView tabBxjlIv;
    @BindView(R.id.tab_bxjl_tv) TextView tabBxjlTv;
    @BindView(R.id.tab_bxjl_layout) LinearLayout tabBxjlLayout;
    @BindView(R.id.tab_baoxiu_iv) ImageView tabBaoxiuIv;
    @BindView(R.id.tab_baoxiu_tv) TextView tabBaoxiuTv;
    @BindView(R.id.tab_baoxiu_layout) LinearLayout tabBaoxiuLayout;
    @BindView(R.id.tab_grzx_iv) ImageView tabGrzxIv;
    @BindView(R.id.tab_grzx_tv) TextView tabGrzxTv;
    @BindView(R.id.tab_grzx_layout) LinearLayout tabGrzxLayout;
    @BindView(R.id.layout_tab) LinearLayout layoutTab;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private RepairRecordsFragment repairRecordsFragment;
    private RepairFragment repairFragment;
    private PersonCenterFragment personCenterFragment;
    private long exitTime = 0;
    private UltimateBar ultimateBar;

    public LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean if_login = SPUtils.getInstance().getBoolean("if_login", false);
        if (!if_login){
            startActivity(new Intent(this,LoginAndRegistActivity.class));
            finish();
            return;
        }
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListener();
        fragmentManager = getSupportFragmentManager();
        setTabSelection(1);
        initLocation();//百度地图定位
        mLocationClient.start();
    }

    private void initListener() {
        tabBxjlLayout.setOnClickListener(this);
        tabBaoxiuLayout.setOnClickListener(this);
        tabGrzxLayout.setOnClickListener(this);
    }

    //处理tab
    @SuppressWarnings("deprecation")
    private void setTabSelection(int index){
        clearSelection();// 每次加载前先清理上次的选中状
        transaction = fragmentManager.beginTransaction();// 开启一个Fragment事务
        hideFragments(transaction);// 隐藏所有fragment,防止多个fragment同时出现

        switch (index){
            case 0:
                tabBxjlIv.setImageResource(R.mipmap.kc_wxjl_click);
                tabBxjlTv.setTextColor(getResources().getColor(R.color.green01));
                if (repairRecordsFragment == null){
                    repairRecordsFragment = new RepairRecordsFragment();
                    transaction.add(R.id.tab_content,repairRecordsFragment);
                }else {
                    transaction.show(repairRecordsFragment);
                }
                break;
            case 1:
                if (repairFragment == null){
                    repairFragment = new RepairFragment();
                    transaction.add(R.id.tab_content,repairFragment);
                }else {
                    transaction.show(repairFragment);
                }
                break;
            case 2:
                tabGrzxIv.setImageResource(R.mipmap.kc_mine_click);
                tabGrzxTv.setTextColor(getResources().getColor(R.color.green01));
                if (personCenterFragment == null){
                    personCenterFragment = new PersonCenterFragment();
                    transaction.add(R.id.tab_content,personCenterFragment);
                }else {
                    transaction.show(personCenterFragment);
                }
                break;
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void clearSelection() {
        tabBxjlIv.setImageResource(R.mipmap.kc_wxjl);
        tabBxjlTv.setTextColor(getResources().getColor(R.color.text_color01));
        tabBaoxiuIv.setImageResource(R.mipmap.kc_baoxiu);
        tabBaoxiuTv.setTextColor(getResources().getColor(R.color.text_color01));
        tabGrzxIv.setImageResource(R.mipmap.kc_mine);
        tabGrzxTv.setTextColor(getResources().getColor(R.color.text_color01));
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (repairRecordsFragment != null){
            transaction.hide(repairRecordsFragment);
        }
        if (repairFragment != null){
            transaction.hide(repairFragment);
        }
        if (personCenterFragment != null){
            transaction.hide(personCenterFragment);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_bxjl_layout:
                setTabSelection(0);
                break;
            case R.id.tab_baoxiu_layout:
//                setTabSelection(1);
                startActivity(new Intent(MainActivity.this,DeviceDetailsActivity.class));
                break;
            case R.id.tab_grzx_layout:
                setTabSelection(2);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis() - exitTime > 2000) {
                TUtils.showShort(getApplicationContext(),"再按一次退出程序");
                exitTime = System.currentTimeMillis();
            }else{
                MyApplication.getInstance().AppExit(MainActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static int al = 0;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        al = getWindowManager().getDefaultDisplay().getWidth()/3 - 20;
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
            String city = location.getCity();
            mLocationClient.stop();
            if (errorCode == 61 || errorCode == 66 || errorCode == 161){
                SPUtils.getInstance().putString("latitude",String.valueOf(latitude));
                SPUtils.getInstance().putString("longitude",String.valueOf(longitude));
                SPUtils.getInstance().putString("BDCity",city);
                Logger.e("纬度："+latitude+"\n经度："+longitude+"\n定位城市："+city);
            }else {
                TUtils.showShort(getApplicationContext(),"百度地图定位失败，请检查网络操作后重试。");
            }
        }
    }
}
