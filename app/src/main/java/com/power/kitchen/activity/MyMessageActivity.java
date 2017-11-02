package com.power.kitchen.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.adapter.CommentTabAdapter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.fragment.RepaireMessageFragment;
import com.power.kitchen.fragment.SystemMessageFragment;
import com.power.kitchen.utils.NoScrollViewPager;

import org.zackratos.ultimatebar.UltimateBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 2017年10月23日 19:17:01
 * power
 * 消息
 */
public class MyMessageActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.message_tablayout) TabLayout messageTablayout;
    @BindView(R.id.message_viewpager) NoScrollViewPager messageViewpager;

    private UltimateBar ultimateBar;
    private List<Fragment> fragmentList;
    private List<String> tab_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorStatusBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        contentTv.setText("消息中心");
        backIv.setOnClickListener(this);

        messageTablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(messageTablayout, 20, 20);
            }
        });

        //初始化各fragment
        RepaireMessageFragment repaireMessageFragment = new RepaireMessageFragment();
        SystemMessageFragment systemMessageFragment = new SystemMessageFragment();

        //将fragment装进列表中
        fragmentList = new ArrayList<>();
        fragmentList.add(repaireMessageFragment);
        fragmentList.add(systemMessageFragment);

        tab_list = new ArrayList<>();
        tab_list.add("报修消息");
        tab_list.add("系统消息");

        messageTablayout.setTabMode(TabLayout.MODE_FIXED);
        messageTablayout.addTab(messageTablayout.newTab().setText(tab_list.get(0)));
        messageTablayout.addTab(messageTablayout.newTab().setText(tab_list.get(1)));

        CommentTabAdapter adapter = new CommentTabAdapter(this.getSupportFragmentManager(),fragmentList,tab_list);
        messageViewpager.setAdapter(adapter);
        messageTablayout.setupWithViewPager(messageViewpager);

    }

    //动态设置指示器下划线长度
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
