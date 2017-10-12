package com.power.kitchen.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.adapter.GridViewAddImgesAdpter;
import com.power.kitchen.app.BaseActivity;
import com.power.kitchen.utils.CommonPopupWindow;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.view.MyGridView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class DeviceDetailActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.gridview)
    MyGridView gridview;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter addImgesAdpter;
    private CommonPopupWindow popupWindow;
    private static final int WRITE_SD_CODE = 3;
    private static final int READ_SD_CODE = 4;
//    private String image_from_sd_paizhao_or_xianche__path;//图片sd路径
//    private boolean isuploadImage;//标记用户是否上传图片
//    private String path; //sd卡路径
    private File tempFile;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    /* 头像名称 */
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
//        addImgesAdpter = new GridViewAddImgesAdpter(,this);
        gridview.setAdapter(addImgesAdpter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopup();
            }
        });
    }

    private void showPopup() {
        popupWindow = new CommonPopupWindow.Builder(this)
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
                                    popupWindow.dismiss();
                                    requestPhoto();
                                }
                            }
                        });
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    TUtils.showShort(getApplicationContext(),"取消");
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
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    @AfterPermissionGranted(WRITE_SD_CODE)
    public void requestCamera(){
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            path = Environment.getExternalStorageDirectory().getPath() + "/";
//            //将当前的拍照时间作为图片的文件名称
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
//            String filename = simpleDateFormat.format(new Date()) + ".jpg";
//            image_from_sd_paizhao_or_xianche__path = path + filename;
//            file = new File(image_from_sd_paizhao_or_xianche__path);
            // 判断存储卡是否可以用，可用进行存储
            if (hasSdcard()) {

                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                tempFile = new File(dir,
                        System.currentTimeMillis() + "_" + PHOTO_FILE_NAME);
                //******************************************************************
                Uri photoURI;
                //解决三星7.x或其他7.x系列的手机拍照失败或应用崩溃的bug.解决4.2.2(oppo)/4.x系统拍照执行不到设置显示图片的bug
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) { //7.0以下的系统用 Uri.fromFile(file)
                    photoURI = Uri.fromFile(tempFile);
                } else {                                            //7.0以上的系统用下面这种方案
                    photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()
                            + ".provider", tempFile);
                }
                //******************************************************************
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);//将图片文件转化为一个uri传入
                startActivityForResult(intent, WRITE_SD_CODE);
            }
        } else {
            // request for one permission
            EasyPermissions.requestPermissions(this, "需要读写本地权限",
                    WRITE_SD_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(READ_SD_CODE)
    public void requestPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == READ_SD_CODE) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);


                    uploadImage(path);
                }

            } else if (requestCode == WRITE_SD_CODE) {
                if (resultCode != RESULT_CANCELED) {
                    // 从相机返回的数据
                    if (tempFile != null) {
                        uploadImage(tempFile.getPath());
                    } else {
                        TUtils.showShort(getApplicationContext(),"相机异常请稍后再试！");
                    }
                    Logger.e("拿到照片path=" + tempFile.getPath());

                }
            }
        }
    }

    /**
     * 上传图片
     *
     * @param path
     */
    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir + "/temp_photo" + System.currentTimeMillis() + ".jpg");
//                NativeUtil.compressBitmap(path, file.getAbsolutePath(), 50);

                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);

            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }

        }
    };

    public void photoPath(String path) {
        Map<String,Object> map = new HashMap<>();
        map.put("path",path);
        datas.add(map);
        addImgesAdpter.notifyDataSetChanged();
    }
}
