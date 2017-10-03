package com.power.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.adapter.NotRepaireAdapter;
import com.power.kitchen.adapter.WaitRepairAdapter;
import com.power.kitchen.bean.WaiteRepairBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/20.
 */

public class NotRepairTabFragment extends Fragment {

    @BindView(R.id.not_list) ListView notList;
    Unbinder unbinder;
    private List<WaiteRepairBean> list = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_repaire, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        list = new ArrayList<>();
        list.clear();
        for (int i = 0;i < 10;i++){
            WaiteRepairBean waiteRepairBean = new WaiteRepairBean();
            waiteRepairBean.setName("美的（Midea）");
            waiteRepairBean.setBianhao("123456789");
            waiteRepairBean.setIs_baoxiu("保修期外"+i);
            waiteRepairBean.setIs_jiedan("未完成"+i);
            waiteRepairBean.setLeixing("空调"+i);
            waiteRepairBean.setShijian("2017年9月20日 19:53:09");
            waiteRepairBean.setXinghao("KDF-57SJSFLGH");
            list.add(waiteRepairBean);
        }
        NotRepaireAdapter adapter = new NotRepaireAdapter(getActivity(),list);
        notList.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
