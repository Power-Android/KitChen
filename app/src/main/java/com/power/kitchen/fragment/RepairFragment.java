package com.power.kitchen.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.power.kitchen.R;
import com.power.kitchen.activity.DeviceDetailsActivity;
import com.power.kitchen.utils.CommonPopupWindow;
import com.power.kitchen.utils.TUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by power on 2017/9/19.
 */

public class RepairFragment extends Fragment implements View.OnClickListener,EasyPermissions.PermissionCallbacks {

    @BindView(R.id.pzksbx_layout) LinearLayout pzksbxLayout;
    @BindView(R.id.txbxd_layout) LinearLayout txbxdLayout;
    Unbinder unbinder;
    private CommonPopupWindow popupWindow;
    private static final int WRITE_SD_CODE = 1;
    private static final int READ_SD_CODE = 2;
    private String image_from_sd_paizhao_or_xianche__path;//图片sd路径
    private boolean isuploadImage;//标记用户是否上传图片
    private String path; //sd卡路径

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
                startActivity(new Intent(getActivity(),DeviceDetailsActivity.class));
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
                                    popupWindow.dismiss();
                                    requestCamera();
                                }
                            }
                        });
                        btn_select_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    TUtils.showShort(getActivity().getApplicationContext(),"相册");
                                    popupWindow.dismiss();
                                    requestPhoto();
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

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //这里需要重新设置Rationale和title，否则默认是英文格式
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(WRITE_SD_CODE)
    public void requestCamera(){
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            path = Environment.getExternalStorageDirectory().getPath() + "/";
            //将当前的拍照时间作为图片的文件名称
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String filename = simpleDateFormat.format(new Date()) + ".jpg";
            image_from_sd_paizhao_or_xianche__path = path + filename;
            File file = new File(image_from_sd_paizhao_or_xianche__path);
            //******************************************************************
            Uri photoURI;
            //解决三星7.x或其他7.x系列的手机拍照失败或应用崩溃的bug.解决4.2.2(oppo)/4.x系统拍照执行不到设置显示图片的bug
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){ //7.0以下的系统用 Uri.fromFile(file)
                photoURI = Uri.fromFile(file);
            }else {                                            //7.0以上的系统用下面这种方案
                photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName()
                        + ".provider",file);
            }
            //******************************************************************
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);//将图片文件转化为一个uri传入
            startActivityForResult(intent, 100);
        } else {
            // request for one permission
            EasyPermissions.requestPermissions(this, "需要读写本地权限",
                    WRITE_SD_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(READ_SD_CODE)
    public void requestPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case WRITE_SD_CODE:
                //TODO 跳转到设备详情页面
                if (resultCode == RESULT_OK){
                    jumpActivity();
                }
                break;
            case READ_SD_CODE:
                //TODO 跳转到选择图片页面

                break;
        }
    }

    private void jumpActivity() {

    }
   /* public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }*/
}
