package com.power.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.power.kitchen.R;
import com.power.kitchen.adapter.CancleRepaireAdapter;
import com.power.kitchen.bean.WaiteRepairBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/20.
 */

public class CancleRepairTabFragment extends Fragment {

    @BindView(R.id.cancle_list) ListView cancleList;
    Unbinder unbinder;
    private List<WaiteRepairBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancle_repaire, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WaiteRepairBean waiteRepairBean = new WaiteRepairBean();
            waiteRepairBean.setName("美的（Midea）");
            waiteRepairBean.setBianhao("123456789");
            waiteRepairBean.setIs_baoxiu("保修期外"+i);
            waiteRepairBean.setIs_jiedan("已取消"+i);
            waiteRepairBean.setLeixing("空调"+i);
            waiteRepairBean.setShijian("2017年9月20日 19:53:09");
            waiteRepairBean.setXinghao("KDF-57SJSFLGH");
            list.add(waiteRepairBean);
        }
        CancleRepaireAdapter adapter = new CancleRepaireAdapter(getActivity(),list);
        cancleList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
