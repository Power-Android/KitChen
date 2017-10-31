package com.power.kitchen.fragment;

import android.content.Intent;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.activity.AboutUsActivity;
import com.power.kitchen.activity.ChangeFaceActivity;
import com.power.kitchen.activity.CommentActivity;
import com.power.kitchen.activity.MyMessageActivity;
import com.power.kitchen.activity.SetPwdActivity;
import com.power.kitchen.activity.SettingActivity;
import com.power.kitchen.bean.EditFaceBean;
import com.power.kitchen.bean.UserInfoBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.Urls;
import com.power.kitchen.view.CircleImageView;
import com.suke.widget.SwitchButton;
import org.json.JSONObject;
import org.zackratos.ultimatebar.UltimateBar;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

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
    private String cutPath,url,true_name,sheng_name,shi_name,qu_name;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /**
         * GitHub：导航栏
         * https://github.com/Zackratos/UltimateBar
         */
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(ContextCompat.getColor(getActivity(), R.color.green01));
        View view = inflater.inflate(R.layout.fragment_person_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        String face = SPUtils.getInstance().getString("face", "");
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                .skipMemoryCache( true );//跳过内存缓存
        Glide.with(PersonCenterFragment.this).load(face).apply(options).into(myHeadIv);

        setSwitchBtn();
        initListener();
        requestUserInfo();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void requestUserInfo() {
        Map<String , String> map = new HashMap<>();
        map.put("app_id",SPUtils.getInstance().getString("app_id",""));
        map.put("tokne",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<UserInfoBean>post(Urls.user_info)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<UserInfoBean>(getActivity(), UserInfoBean.class) {
                    @Override
                    public void onSuccess(Response<UserInfoBean> response) {
                        UserInfoBean userInfoBean = response.body();
                        true_name = userInfoBean.getData().getTrue_name();
                        sheng_name = userInfoBean.getData().getSheng_name();
                        shi_name = userInfoBean.getData().getShi_name();
                        qu_name = userInfoBean.getData().getQu_name();
                        myNameTv.setText(true_name);
                        SPUtils.getInstance().putString("true_name",true_name);
                        myContentTv.setText(userInfoBean.getData().getMobile());
                        myLocationTv.setText(sheng_name + " "+ shi_name + " "+ qu_name);
                    }
                });
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
                intent = new Intent(getActivity(),ChangeFaceActivity.class);
                startActivityForResult(intent,102);
                break;
            case R.id.into_set_iv://进入设置
                intent = new Intent(getActivity(),SettingActivity.class);
                intent.putExtra("true_name",true_name);
                intent.putExtra("sheng_name",sheng_name);
                intent.putExtra("shi_name",shi_name);
                intent.putExtra("qu_name",qu_name);
                startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102){
            if (data != null){
                cutPath = data.getStringExtra("cutPath");
                Bitmap bitmap = BitmapFactory.decodeFile(cutPath);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,bos);
                byte[] byteArray = bos.toByteArray();
                byte[] encode = it.sauronsoftware.base64.Base64.encode(byteArray);
                url = new String(encode);
                requestFace();//修改头像
            }
        }
    }

    private void requestFace() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("face","data:image/jpg;base64,"+ url);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<EditFaceBean>post(Urls.edit_face)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<EditFaceBean>(EditFaceBean.class) {
                    @Override
                    public void onSuccess(Response<EditFaceBean> response) {
                        EditFaceBean body = response.body();
                        if (!TextUtils.isEmpty(body.getData().getFace())){
                            Glide.with(getActivity())
                                    .load(cutPath)
                                    .into(myHeadIv);
                        }
                    }
                });
    }

}
