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
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.activity.CancleRepaireDetailActivity;
import com.power.kitchen.adapter.CancleRepaireAdapter;
import com.power.kitchen.adapter.MyFooter;
import com.power.kitchen.adapter.MyHeader;
import com.power.kitchen.app.BaseFragment;
import com.power.kitchen.bean.OrderListBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.EmptyCallback;
import com.power.kitchen.callback.ErrorCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.callback.LoadingCallback;
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
 * 已取消
 */

public class CancleRepairTabFragment extends BaseFragment implements SpringView.OnFreshListener {

    @BindView(R.id.cancle_list) ListView cancleList;
    @BindView(R.id.springview) SpringView springView;
    Unbinder unbinder;
    private List<OrderListBean.DataBean.ListsBean> list = new ArrayList<>();
    private List<OrderListBean.DataBean.ListsBean> listAll = new ArrayList<>();
    private int p = 1;
    private View view;
    private LoadService loadService;
    private CancleRepaireAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cancle_repaire, container, false);
        unbinder = ButterKnife.bind(this, view);
        initLoad();
        initView();
        return loadService.getLoadLayout();
    }

    private void initLoad() {
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(SuccessCallback.class)
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

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        if (!listAll.isEmpty()){
            listAll.clear();
        }
        p = 1;
        requestOrderList();
    }

    private void requestOrderList() {
        Map<String,String> map = new HashMap<>();
        map.put("app_id","android-user_20170808");
        map.put("token", SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("p",p+"");
        map.put("type","4");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<OrderListBean>post(Urls.order_lists)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<OrderListBean>(getActivity(),OrderListBean.class) {
                    @Override
                    public void onSuccess(Response<OrderListBean> response) {
                        OrderListBean orderListBean = response.body();
                        if (TextUtils.equals("1",orderListBean.getStatus())){
                            list = orderListBean.getData().getLists();
                            listAll.addAll(list);
                            if (TextUtils.equals("0",listAll.size()+"")){
                                loadService.showCallback(EmptyCallback.class);
                            }else {
                                if (p == 1){
                                    adapter = new CancleRepaireAdapter(getActivity(),listAll);
                                    cancleList.setAdapter(adapter);
                                }else {
                                    adapter.notifyDataSetChanged();
                                }
//                                loadService.showSuccess();
                                p = p + 1;
                                cancleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String oid = listAll.get(position).getOid();
                                        Intent intent = new Intent(getActivity(),CancleRepaireDetailActivity.class);
                                        intent.putExtra("oid",oid);
                                        startActivity(intent);
                                    }
                                });
                                springView.onFinishFreshAndLoad();
                            }
                        }else {
                            TUtils.showShort(getActivity().getApplicationContext(),orderListBean.getInfo());
                            loadService.showCallback(ErrorCallback.class);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        springView.callFresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
                if (!listAll.isEmpty()){
                    listAll.clear();
                }
                p = 1;
                requestOrderList();
    }

    @Override
    public void onLoadmore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.equals(list.toString(),"[]")){
                    p = 1;
                    adapter.notifyDataSetChanged();
                }else {
                    requestOrderList();
                }
            }
        }, 1000);
    }
}
