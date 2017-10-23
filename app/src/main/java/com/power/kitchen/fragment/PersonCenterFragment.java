package com.power.kitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.activity.AboutUsActivity;
import com.power.kitchen.activity.CommentActivity;
import com.power.kitchen.activity.MyMessageActivity;
import com.power.kitchen.activity.SetPwdActivity;
import com.power.kitchen.activity.SettingActivity;
import com.power.kitchen.view.CircleImageView;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by power on 2017/9/19.
 */

public class PersonCenterFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.my_head_iv) CircleImageView myHeadIv;
    @BindView(R.id.my_name_tv) TextView myNameTv;
    @BindView(R.id.my_content_tv) TextView myContentTv;
    @BindView(R.id.my_msg_iv) ImageView myMsgIv;
    @BindView(R.id.into_set_iv) ImageView intoSetIv;
    @BindView(R.id.my_setpwd_layout) LinearLayout mySetpwdLayout;
    @BindView(R.id.my_pj_layout) LinearLayout myPjLayout;
    @BindView(R.id.voice_switchBtn) SwitchButton voiceSwitchBtn;
    @BindView(R.id.zd_switchBtn) SwitchButton zdSwitchBtn;
    @BindView(R.id.my_location_tv) TextView myLocationTv;
    @BindView(R.id.my_location_iv) ImageView myLocationIv;
    @BindView(R.id.my_gywm_layout) RelativeLayout myGywmLayout;
    @BindView(R.id.my_fwrx_layout) RelativeLayout myFwrxLayout;
    @BindView(R.id.exit_login_layout) LinearLayout exitLoginLayout;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        setSwitchBtn();
        initListener();
        return view;
    }

    private void setSwitchBtn() {
        voiceSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do job
            }
        });
        zdSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do job
            }
        });

    }

    private void initListener() {
        myHeadIv.setOnClickListener(this);
        intoSetIv.setOnClickListener(this);
        myMsgIv.setOnClickListener(this);
        mySetpwdLayout.setOnClickListener(this);
        myPjLayout.setOnClickListener(this);
        myLocationIv.setOnClickListener(this);
        myGywmLayout.setOnClickListener(this);
        exitLoginLayout.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_head_iv://头像
                break;
            case R.id.into_set_iv://进入设置
                startActivity(new Intent(getActivity(),SettingActivity.class));
                break;
            case R.id.my_msg_iv://消息页面
                startActivity(new Intent(getActivity(),MyMessageActivity.class));
                break;
            case R.id.my_setpwd_layout://密码设置
                startActivity(new Intent(getActivity(),SetPwdActivity.class));
                break;
            case R.id.my_pj_layout://评价页面
                startActivity(new Intent(getActivity(),CommentActivity.class));
                break;
            case R.id.my_location_iv://定位
                break;
            case R.id.my_gywm_layout://关于我们
                startActivity(new Intent(getActivity(),AboutUsActivity.class));
                break;
            case R.id.exit_login_layout://退出
                break;
        }
    }
}
