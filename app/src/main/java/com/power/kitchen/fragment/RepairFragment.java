package com.power.kitchen.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.power.kitchen.R;
import com.power.kitchen.utils.CommonPopupWindow;
import com.power.kitchen.utils.TUtils;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

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
    private CommonPopupWindow popupWindow;
    private static final int REQUECT_CODE_CAREMA = 2;

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
                showPopup();
                break;
            case R.id.txbxd_layout:
                TUtils.showShort(getActivity().getApplicationContext(), "22222222222");
                break;
        }
    }

    //全屏弹出
    public void showPopup() {
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_camera)//设置布局
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//设置宽高
                .setBackGroundLevel(0.5f)//设置背景颜色   取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)//设置动画
                .setOutsideTouchable(true)//设置外部是否可点击 默认是true
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        Button btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
                        Button btn_select_photo = (Button) view.findViewById(R.id.btn_select_photo);
                        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                        btn_take_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    MPermissions.requestPermissions(RepairFragment.this, 1,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        btn_select_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    TUtils.showShort(getActivity().getApplicationContext(),"相册");
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    TUtils.showShort(getActivity().getApplicationContext(),"取消");
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        view.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                                return true;
                            }
                        });
                    }
                })
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @PermissionGrant(1)
    public void requestCaremaSuccess() {
        TUtils.showShort(getActivity().getApplicationContext(),"请求权限成功");
    }

    @PermissionDenied(1)
    public void requestCaremaFailed() {
        TUtils.showShort(getActivity().getApplicationContext(),"请求权限失败");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 跳转到照相机
     */

}
