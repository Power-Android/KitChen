package com.power.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.power.kitchen.R;
import com.power.kitchen.adapter.MessageAdapter;
import com.power.kitchen.bean.MessageBean;
import com.power.kitchen.callback.EmptyCallback;
import com.power.kitchen.callback.ErrorCallback;
import com.power.kitchen.callback.LoadingCallback;
import com.power.kitchen.callback.TimeoutCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/23.
 * 系统消息
 */

public class SystemMessageFragment extends Fragment {

    @BindView(R.id.system_listView)
    ListView systemListView;
    Unbinder unbinder;
    private View view;
    private LoadService loadService;
    private List<MessageBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_system_message, container, false);
        unbinder = ButterKnife.bind(this, view);

        initLoad();
        initView();
        return view;
    }

    /**
     * 加载状态页
     */
    private void initLoad() {
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        loadService = loadSir.register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                //重新加载逻辑
                loadService.showSuccess();
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageBean messageBean = new MessageBean();
            messageBean.setContent("商厨联盟，商厨联盟，商厨联盟，商厨联盟，商厨联盟，商厨联盟，商厨联盟," +
                    "商厨联盟，商厨联盟，商厨联盟，商厨联盟，商厨联盟，商厨联盟，商厨联盟");
            messageBean.setTime("2017年10月23日 19:01:49");
            list.add(messageBean);
        }
        MessageAdapter adapter = new MessageAdapter(getActivity(),list);
        systemListView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
