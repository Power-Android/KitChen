package com.power.kitchen.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.adapter.BrandListAdapter;
import com.power.kitchen.adapter.TypeListAdapter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.bean.SortModel;
import com.power.kitchen.bean.TypeBean;
import com.power.kitchen.utils.PinyinComparator;
import com.power.kitchen.utils.PinyinUtils;
import com.power.kitchen.utils.TitleItemDecoration;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 品牌和类型
 * power
 * 2017年10月19日 16:14:13
 */
public class BrandAndTypeActivity extends BaseActivity {

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.title_right_tv) TextView titleRightTv;
    @BindView(R.id.title_msg) ImageView titleMsg;
    @BindView(R.id.brand_list) RecyclerView brandList;
    @BindView(R.id.type_list) ListView typeList;

    private UltimateBar ultimateBar;
    List<SortModel> leftList = new ArrayList<>();
    public static int POSITION_BRAND=0;
    private List<TypeBean> rightList = new ArrayList<>();
    private BrandListAdapter brandListAdapter;
    private TypeListAdapter typeListAdapter;
    private List<TypeBean> list = new ArrayList<>();

    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator mComparator;
    private TitleItemDecoration mDecoration;
    private LinearLayoutManager manager;
    private String brand_name;
    private String type_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.green01));
        setContentView(R.layout.activity_brand_and_type);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentTv.setText("品牌和类型");
        titleRightTv.setVisibility(View.VISIBLE);
        titleRightTv.setText("确认");
        backIv.setOnClickListener(this);
        titleRightTv.setOnClickListener(this);
        initData();
    }

    private void initData() {
        mComparator = new PinyinComparator();
        leftList = filledData(getResources().getStringArray(R.array.date));
        // 根据a-z进行排序源数据
        Collections.sort(leftList, mComparator);
        //RecyclerView设置manager
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        brandList.setLayoutManager(manager);
        brandListAdapter = new BrandListAdapter(this,leftList);
        brandList.setAdapter(brandListAdapter);

        mDecoration = new TitleItemDecoration(this, leftList);
        //如果add两个，那么按照先后顺序，依次渲染。
        brandList.addItemDecoration(mDecoration);

        for (int i = 0; i < 20; i++) {
            TypeBean typeBean = new TypeBean();
            typeBean.setName("空调"+i);
            rightList.add(typeBean);
        }
        typeListAdapter = new TypeListAdapter(this,rightList);
        typeList.setAdapter(typeListAdapter);

        brandListAdapter.setOnItemClickListener(new BrandListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                brandListAdapter.setSlectedPosition(position);
                brandListAdapter.notifyDataSetChanged();
                brand_name = leftList.get(position).getName();
                list = new ArrayList<TypeBean>();
                for (int i = 0; i <5; i++) {
                    TypeBean typeBean = new TypeBean();
                    typeBean.setName("抽油烟机"+i);
                    list.add(typeBean);
                }
                rightList.addAll(list);
                typeListAdapter.notifyDataSetChanged();
            }
        });
        typeListAdapter.setOnItemClickListener(new TypeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position;
                position = (Integer) view.getTag();
                typeListAdapter.setSlectedPosition(position);
                type_name = rightList.get(position).getName();
                typeListAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent();
                intent.putExtra("brandName",brand_name);
                intent.putExtra("typeName",type_name);
                Logger.e(brand_name+"-----"+type_name);
                setResult(101,intent);
                finish();
                break;
        }
    }

    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

}
