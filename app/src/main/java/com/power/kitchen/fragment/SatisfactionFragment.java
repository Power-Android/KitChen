package com.power.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.adapter.SatisfactionAdapter;
import com.power.kitchen.bean.CommentListBean;
import com.power.kitchen.bean.LoginBean;
import com.power.kitchen.bean.TokenBean;
import com.power.kitchen.bean.WaiteRepairBean;
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

public class SatisfactionFragment extends Fragment {

    @BindView(R.id.satisfaction_listView) ListView satisfactionListView;
    Unbinder unbinder;
    private LoadService loadService;
    private View view;
    List<CommentListBean.DataBean.ListsBean> list;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_satisfaction, container, false);
        unbinder = ButterKnife.bind(this, view);

        initLoad();
        initView();
        requestCommentList();

        return loadService.getLoadLayout();
    }

    private void requestCommentList() {
        Map<String, String> map = new HashMap<>();
        map.put("app_id", SPUtils.getInstance().getString("app_id",""));
        map.put("token",SPUtils.getInstance().getString("token",""));
        map.put("id",SPUtils.getInstance().getString("id",""));
        map.put("p","1");
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
                            if (TextUtils.equals("0",list.size()+"")){
                                loadService.showCallback(EmptyCallback.class);
                            }else {
                                SatisfactionAdapter adapter = new SatisfactionAdapter(getActivity(),list);
                                satisfactionListView.setAdapter(adapter);
                                loadService.showSuccess();
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
                loadService.showSuccess();
            }
        });
    }


    private void initView() {

        SatisfactionAdapter adapter = new SatisfactionAdapter(getActivity(),list);
        satisfactionListView.setAdapter(adapter);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
