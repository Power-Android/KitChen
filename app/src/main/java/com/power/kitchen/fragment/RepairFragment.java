package com.power.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.power.kitchen.R;
import com.power.kitchen.utils.TUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by power on 2017/9/19.
 */

public class RepairFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.pzksbx_layout) LinearLayout pzksbxLayout;
    @BindView(R.id.txbxd_layout) LinearLayout txbxdLayout;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair, container, false);
        unbinder = ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        pzksbxLayout.setOnClickListener(this);
        txbxdLayout.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pzksbx_layout:
                TUtils.showShort(getActivity().getApplicationContext(), "11111111111");
                break;
            case R.id.txbxd_layout:
                TUtils.showShort(getActivity().getApplicationContext(), "22222222222");
                break;
        }
    }
}
