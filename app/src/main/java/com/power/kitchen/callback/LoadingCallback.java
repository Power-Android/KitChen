package com.power.kitchen.callback;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.power.kitchen.R;

/**
 * Created by Administrator on 2017/10/11.
 */

public class LoadingCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }
}
