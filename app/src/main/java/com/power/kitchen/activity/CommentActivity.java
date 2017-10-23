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
import com.power.kitchen.fragment.CommonlyFragment;
import com.power.kitchen.fragment.DiscontentFragment;
import com.power.kitchen.fragment.SatisfactionFragment;
import com.power.kitchen.utils.NoScrollViewPager;

import org.zackratos.ultimatebar.UltimateBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 2017年10月23日 19:16:12
 * power
 * 评价
 */
public class CommentActivity extends BaseActivity {

    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.comment_tablayout) TabLayout commentTablayout;
    @BindView(R.id.comment_viewpager) NoScrollViewPager commentViewpager;

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
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        initViewAndListener();
    }

    private void initViewAndListener() {
        backIv.setVisibility(View.GONE);
        contentTv.setText("评价");

        commentTablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(commentTablayout, 40, 40);
            }
        });

        //初始化各fragment
        SatisfactionFragment satisfactionFragment = new SatisfactionFragment();
        CommonlyFragment commonlyFragment = new CommonlyFragment();
        DiscontentFragment discontentFragment = new DiscontentFragment();

        //将fragment装进列表中
        fragmentList = new ArrayList<>();
        fragmentList.add(satisfactionFragment);
        fragmentList.add(commonlyFragment);
        fragmentList.add(discontentFragment);

        tab_list = new ArrayList<>();
        tab_list.add("满意");
        tab_list.add("一般");
        tab_list.add("不满意");

        commentTablayout.setTabMode(TabLayout.MODE_FIXED);
        commentTablayout.addTab(commentTablayout.newTab().setText(tab_list.get(0)));
        commentTablayout.addTab(commentTablayout.newTab().setText(tab_list.get(1)));
        commentTablayout.addTab(commentTablayout.newTab().setText(tab_list.get(2)));

        CommentTabAdapter adapter = new CommentTabAdapter(this.getSupportFragmentManager(),fragmentList,tab_list);
        commentViewpager.setAdapter(adapter);
        commentTablayout.setupWithViewPager(commentViewpager);

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
}
