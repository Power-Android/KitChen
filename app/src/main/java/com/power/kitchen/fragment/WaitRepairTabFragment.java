package com.power.kitchen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.activity.WaitRepaireDetailActivity;
import com.power.kitchen.adapter.MyFooter;
import com.power.kitchen.adapter.MyHeader;
import com.power.kitchen.adapter.WaitRepairAdapter;
import com.power.kitchen.bean.OrderListBean;
import com.power.kitchen.callback.EmptyCallback;
import com.power.kitchen.callback.ErrorCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.callback.LoadingCallback;
import com.power.kitchen.callback.TimeoutCallback;
import com.power.kitchen.utils.SPUtils;
import com.power.kitchen.utils.TUtils;
import com.power.kitchen.utils.Urls;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/9/20.
 * 待维修
 */

public class WaitRepairTabFragment extends Fragment implements View.OnClickListener, SpringView.OnFreshListener {

    @BindView(R.id.wait_list) ListView waitList;
    @BindView(R.id.springview) SpringView springView;
    Unbinder unbinder;
    private List<OrderListBean.DataBean.ListsBean> list;
    private LoadService loadService;
    private View view;
    private int p = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wait_repaire, container, false);
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
//        springView.setHeader(new AliHeader(getActivity(),R.mipmap.app_logo,true));
        springView.setHeader(new MyHeader(getActivity()));
        springView.setFooter(new MyFooter(getActivity()));
    }

    private void requestOrderList() {
        Map<String,String> map = new HashMap<>();
        map.put("app_id","android-user_20170808");
        map.put("token", SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("p","1");
        map.put("type","1");
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
                                WaitRepairAdapter adapter = new WaitRepairAdapter(getActivity(),list);
                                waitList.setAdapter(adapter);
                                loadService.showSuccess();
                                waitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String oid = list.get(position).getOid();
                                        String order_accept_name = list.get(position).getOrder_accept_name();
                                        String status_accept = list.get(position).getStatus_accept();
                                        Intent intent = new Intent(getActivity(),WaitRepaireDetailActivity.class);
                                        intent.putExtra("oid",oid);
                                        intent.putExtra("order_accept_name",order_accept_name);
                                        intent.putExtra("status_accept",status_accept);
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
    public void onClick(View view) {

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
