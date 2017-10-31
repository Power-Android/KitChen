package com.power.kitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.power.kitchen.R;
import com.power.kitchen.activity.NotRepaireDetailActivity;
import com.power.kitchen.adapter.AlreadyRepaireAdapter;
import com.power.kitchen.adapter.MyFooter;
import com.power.kitchen.adapter.MyHeader;
import com.power.kitchen.adapter.NotRepaireAdapter;
import com.power.kitchen.adapter.WaitRepairAdapter;
import com.power.kitchen.bean.OrderListBean;
import com.power.kitchen.bean.WaiteRepairBean;
import com.power.kitchen.callback.EmptyCallback;
import com.power.kitchen.callback.ErrorCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.callback.LoadingCallback;
import com.power.kitchen.callback.PostUtil;
import com.power.kitchen.callback.TimeoutCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/20.
 * 未完成
 */

public class NotRepairTabFragment extends Fragment implements SpringView.OnFreshListener{

    @BindView(R.id.not_list) ListView notList;
    @BindView(R.id.springview) SpringView springView;
    Unbinder unbinder;
    private List<OrderListBean.DataBean.ListsBean> list;
    private View view;
    private LoadService loadService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_not_repaire, container, false);
        unbinder = ButterKnife.bind(this, view);
        initLoad();
        initView();
        requestOrderList();
        return loadService.getLoadLayout();
    }

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
                loadService.showCallback(LoadingCallback.class);
                requestOrderList();
            }

        });
    }

    private void initView() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(this);
        springView.setHeader(new MyHeader(getActivity()));
        springView.setFooter(new MyFooter(getActivity()));
    }

    private void requestOrderList() {
        Map<String,String> map = new HashMap<>();
        map.put("app_id","android-user_20170808");
        map.put("token", SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("p","1");
        map.put("type","3");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<OrderListBean>post(Urls.order_lists)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<OrderListBean>(OrderListBean.class) {
                    @Override
                    public void onSuccess(Response<OrderListBean> response) {
                        OrderListBean orderListBean = response.body();
                        if (TextUtils.equals("1",orderListBean.getStatus())){
                            list = orderListBean.getData().getLists();
                            if (TextUtils.equals("0",list.size()+"")){
                                loadService.showCallback(EmptyCallback.class);
                            }else {
                                NotRepaireAdapter adapter = new NotRepaireAdapter(getActivity(),list);
                                notList.setAdapter(adapter);
                                loadService.showSuccess();
                                notList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String oid = list.get(position).getOid();
                                        Intent intent = new Intent(getActivity(),NotRepaireDetailActivity.class);
                                        intent.putExtra("oid",oid);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }else {
                            TUtils.showShort(getActivity().getApplicationContext(),orderListBean.getInfo());
                            loadService.showCallback(ErrorCallback.class);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadmore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 1000);
    }
}
