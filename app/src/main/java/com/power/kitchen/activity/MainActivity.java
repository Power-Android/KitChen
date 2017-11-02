package com.power.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.app.MyApplication;
import com.power.kitchen.fragment.PersonCenterFragment;
import com.power.kitchen.fragment.RepairFragment;
import com.power.kitchen.fragment.RepairRecordsFragment;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import org.zackratos.ultimatebar.UltimateBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
