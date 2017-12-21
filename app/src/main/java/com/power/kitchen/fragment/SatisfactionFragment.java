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
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.activity.AlreadyRepaireDetailActivity;
import com.power.kitchen.adapter.MyFooter;
import com.power.kitchen.adapter.MyHeader;
import com.power.kitchen.adapter.SatisfactionAdapter;
import com.power.kitchen.bean.CommentInfoBean;
import com.power.kitchen.bean.CommentListBean;
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
 * Created by Administrator on 2017/10/23.
 * 满意
 */

public class SatisfactionFragment extends Fragment implements SpringView.OnFreshListener,AdapterView.OnItemClickListener{

    @BindView(R.id.satisfaction_listView) ListView satisfactionListView;
    @BindView(R.id.springview) SpringView springView;
    Unbinder unbinder;
    private LoadService loadService;
    private View view;
    List<CommentListBean.DataBean.ListsBean> list = new ArrayList<>();
    List<CommentListBean.DataBean.ListsBean> listAll = new ArrayList<>();
    private int p = 1;
    private SatisfactionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_satisfaction, container, false);
        unbinder = ButterKnife.bind(this, view);

        initLoad();
        initView();
        if (!listAll.isEmpty()){
            listAll.clear();
        }
        p = 1;
        requestCommentList();

        return loadService.getLoadLayout();
    }

    private void initView() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(this);
        springView.setHeader(new MyHeader(getActivity()));
        springView.setFooter(new MyFooter(getActivity()));
    }

    private void requestCommentList() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("p",p+"");
        map.put("level","1");
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<CommentListBean>post(Urls.comment_lists)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<CommentListBean>(CommentListBean.class) {
                    @Override
                    public void onSuccess(Response<CommentListBean> response) {
                        CommentListBean commentListBean = response.body();
                        if (TextUtils.equals("1",commentListBean.getStatus())){
                            list = commentListBean.getData().getLists();
                            listAll.addAll(list);
                            if (TextUtils.equals("0",listAll.size()+"")){
                                loadService.showCallback(EmptyCallback.class);
                            }else {
                                if (p == 1){
                                    adapter = new SatisfactionAdapter(getActivity(),listAll);
                                    satisfactionListView.setAdapter(adapter);
                                }else {
                                    adapter.notifyDataSetChanged();
                                }
                                p = p + 1;
                                loadService.showSuccess();
                                satisfactionListView.setOnItemClickListener(SatisfactionFragment.this);
                            }
                        }else {
                            TUtils.showShort(getActivity().getApplicationContext(),commentListBean.getInfo());
                            loadService.showCallback(ErrorCallback.class);
                        }
                    }
                });
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
                loadService.showCallback(LoadingCallback.class);
                p = 1;
                requestCommentList();
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
                if (!listAll.isEmpty()){
                    listAll.clear();
                }
                p = 1;
                requestCommentList();
                springView.onFinishFreshAndLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadmore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestCommentList();
                springView.onFinishFreshAndLoad();
            }
        }, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String coment_id = listAll.get(position).getComent_id();
        requestCommentInfo(coment_id);
    }

    private void requestCommentInfo(String coment_id) {
        Map<String, String> map = new HashMap<>();
        map.put("app_id",SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("coment_id",coment_id);
        JSONObject values = new JSONObject(map);
        HttpParams params = new HttpParams();
        params.put("data",values.toString());

        OkGo.<CommentInfoBean>post(Urls.comment_info)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<CommentInfoBean>(getActivity(),CommentInfoBean.class) {
                    @Override
                    public void onSuccess(Response<CommentInfoBean> response) {
                        CommentInfoBean commentInfoBean = response.body();
                        String oid = commentInfoBean.getData().getOid();
                        Intent intent = new Intent(getActivity(), AlreadyRepaireDetailActivity.class);
                        intent.putExtra("comment_oid",oid);
                        startActivity(intent);
                    }
                });
    }
}
