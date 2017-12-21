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
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.activity.MessageDetailActivity;
import com.power.kitchen.adapter.MessageAdapter;
import com.power.kitchen.adapter.MyFooter;
import com.power.kitchen.adapter.MyHeader;
import com.power.kitchen.bean.MessageBean;
import com.power.kitchen.bean.NoticeOrderListBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.DialogCallback;
import com.power.kitchen.callback.EmptyCallback;
import com.power.kitchen.callback.ErrorCallback;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.callback.LoadingCallback;
import com.power.kitchen.callback.TimeoutCallback;
import com.power.kitchen.utils.SPUtils;
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
 * Created by Administrator on 2017/10/23.
 * 报修消息
 */

public class RepaireMessageFragment extends Fragment implements SpringView.OnFreshListener{

    @BindView(R.id.repaire_listView) ListView repaireListView;
    @BindView(R.id.springview) SpringView springView;
    Unbinder unbinder;
    private View view;
    private LoadService loadService;
    private List<NoticeOrderListBean.DataBean.ListsBean> list = new ArrayList<>();
    private List<NoticeOrderListBean.DataBean.ListsBean> listAll = new ArrayList<>();
    private int p = 1;
    private MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_repaire_message, container, false);
        unbinder = ButterKnife.bind(this, view);

        initLoad();
        initView();
        requestNoticeOrderList();
        return loadService.getLoadLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.callFresh();
            }
        }, 1000);
    }

    private void initView() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(this);
        springView.setHeader(new MyHeader(getActivity()));
        springView.setFooter(new MyFooter(getActivity()));
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
                .setDefaultCallback(SuccessCallback.class)
                .build();
        loadService = loadSir.register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                //重新加载逻辑
                loadService.showCallback(LoadingCallback.class);
                requestNoticeOrderList();
            }
        });
    }

    private void requestNoticeOrderList() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("p",p+"");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<NoticeOrderListBean>post(Urls.notice_order_list)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<NoticeOrderListBean>(getActivity(),NoticeOrderListBean.class) {
                    @Override
                    public void onSuccess(Response<NoticeOrderListBean> response) {
                        NoticeOrderListBean noticeOrderListBean = response.body();
                        if (TextUtils.equals("1",noticeOrderListBean.getStatus())){
                            list = noticeOrderListBean.getData().getLists();
                            listAll.addAll(list);
                            if (TextUtils.equals("0",listAll.size()+"")){
                                loadService.showCallback(EmptyCallback.class);
                            }else {
                                if (p == 1){
                                    adapter = new MessageAdapter(getActivity(),listAll);
                                    repaireListView.setAdapter(adapter);
                                }else {
                                    adapter.notifyDataSetChanged();
                                }
                                p = p + 1;
                                loadService.showSuccess();
                                repaireListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                                        intent.putExtra("notice_id",listAll.get(position).getNotice_id());
                                        intent.putExtra("flag","repaire");
                                        startActivity(intent);
                                    }
                                });
                                springView.onFinishFreshAndLoad();
                            }
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
                if (!listAll.isEmpty()){
                    listAll.clear();
                }
                p = 1;
                requestNoticeOrderList();
    }

    @Override
    public void onLoadmore() {
                requestNoticeOrderList();
    }
}
