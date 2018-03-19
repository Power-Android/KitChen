package com.power.kitchen.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.activity.MyMessageActivity;
import com.power.kitchen.adapter.RepaireTabAdapter;
import com.power.kitchen.bean.NoticeNumBean;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.NoScrollViewPager;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.Urls;

import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by power on 2017/9/19.
 * 报修记录Fragment
 */

public class RepairRecordsFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.bxjl_tablayout) TabLayout bxjlTablayout;
    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.title_msg) ImageView titleMsg;
    @BindView(R.id.bxjl_viewpager) NoScrollViewPager bxjlViewpager;
    @BindView(R.id.message_tv) TextView messageTv;

    Unbinder unbinder;

    private List<Fragment> fragmentList;
    private List<String> tab_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair_records, container, false);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorStatusBar(ContextCompat.getColor(getActivity(),R.color.green01));
        unbinder = ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    private void initView() {

        backIv.setVisibility(View.GONE);
        titleMsg.setVisibility(View.VISIBLE);
        contentTv.setText("报修记录");
        //解决页面滑动时，闪烁的问题
        bxjlTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bxjlViewpager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        bxjlTablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(bxjlTablayout, 20, 20);
            }
        });

        //初始化各fragment
        WaitRepairTabFragment waitRepairTabFragment = new WaitRepairTabFragment();
        AlreadyRepairTabFragment alreadyRepairTabFragment = new AlreadyRepairTabFragment();
        NotRepairTabFragment notRepairTabFragment = new NotRepairTabFragment();
        CancleRepairTabFragment cancleRepairTabFragment = new CancleRepairTabFragment();

        //将fragment装进列表中
        fragmentList = new ArrayList<>();
        fragmentList.add(waitRepairTabFragment);
        fragmentList.add(alreadyRepairTabFragment);
        fragmentList.add(notRepairTabFragment);
        fragmentList.add(cancleRepairTabFragment);

        tab_list = new ArrayList<>();
        tab_list.add("待维修");
        tab_list.add("已维修");
        tab_list.add("未完成");
        tab_list.add("已取消");

        bxjlTablayout.setTabMode(TabLayout.MODE_FIXED);

        bxjlTablayout.addTab(bxjlTablayout.newTab().setText(tab_list.get(0)));
        bxjlTablayout.addTab(bxjlTablayout.newTab().setText(tab_list.get(1)));
        bxjlTablayout.addTab(bxjlTablayout.newTab().setText(tab_list.get(2)));
        bxjlTablayout.addTab(bxjlTablayout.newTab().setText(tab_list.get(3)));

        RepaireTabAdapter adapter = new RepaireTabAdapter(getActivity().getSupportFragmentManager(),
                fragmentList, tab_list);

        bxjlViewpager.setAdapter(adapter);
        bxjlTablayout.setupWithViewPager(bxjlViewpager);

    }

    private void initListener() {
        titleMsg.setOnClickListener(this);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_msg:
                startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;
            default:
                break;
        }
    }

    private void requestNotice() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("tokne",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<NoticeNumBean>post(Urls.notice_num)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<NoticeNumBean>(NoticeNumBean.class) {
                    @Override
                    public void onSuccess(Response<NoticeNumBean> response) {
                        NoticeNumBean noticeNumBean = response.body();
                        if (TextUtils.equals("1",noticeNumBean.getStatus()) && !noticeNumBean.getData().getCount().equals("")){
                            messageTv.setVisibility(View.VISIBLE);
                            messageTv.setText(noticeNumBean.getData().getCount());
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNotice();
        Logger.e("--------消息---------");
    }
}
