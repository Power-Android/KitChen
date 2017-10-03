package com.power.kitchen.app;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzy.okgo.OkGo;
import com.power.kitchen.R;

/**
 * Created by power on 2017/9/19.
 */

public class BaseActivity extends FragmentActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().finishActivity(this);
        OkGo.getInstance().cancelTag(this);
    }

    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus() {
        setStatusBarTranslucent(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.title_layout_bg_color);// 状态栏的背景颜色(0表示无背景)
    }

    /**
     * 设置状态栏是否透明
     *
     * @param isTransparent
     */
    private void setStatusBarTranslucent(boolean isTransparent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && isTransparent) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            // 导航栏透明
            final int sBits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= sBits;
            win.setAttributes(winParams);
        }
    }
}
