package com.power.kitchen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.bean.OrderListBean;
import com.power.kitchen.bean.WaiteRepairBean;
import com.power.kitchen.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/20.
 */

public class WaitRepairAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    List<OrderListBean.DataBean.ListsBean> list = new ArrayList<>();

    public WaitRepairAdapter(Context context, List<OrderListBean.DataBean.ListsBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_wait_repaire_layout, null, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.bianhaoTv.setText(list.get(position).getOid());
            viewHolder.leixingTv.setText(list.get(position).getGoods_type_name());
            viewHolder.nameTv.setText(list.get(position).getGoods_brand_name());
            viewHolder.isJiedanTv.setText(list.get(position).getOrder_accept_name());
            if (TextUtils.equals("0",list.get(position).getStatus_accept())){
                viewHolder.isJiedanTv.setTextColor(context.getResources().getColor(R.color.gary));
            }else {
                viewHolder.isJiedanTv.setTextColor(context.getResources().getColor(R.color.yellow01));
            }
            String goods_is_warranty = list.get(position).getGoods_is_warranty();
            if (TextUtils.equals("1",goods_is_warranty)){
                viewHolder.isBaoxiuTv.setText("保修期内");
            }else {
                viewHolder.isBaoxiuTv.setText("保修期外");
            }
            viewHolder.nameTv01.setText(list.get(position).getGoods_code());
            viewHolder.timeTv.setText(TimeUtils.getStrTimeYMD(list.get(position).getCreate_time()));
            viewHolder.xinghaoTv.setText(list.get(position).getGoods_model());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.bianhao_tv) TextView bianhaoTv;
        @BindView(R.id.is_jiedan_tv) TextView isJiedanTv;
        @BindView(R.id.name_tv) TextView nameTv;
        @BindView(R.id.leixing_tv) TextView leixingTv;
        @BindView(R.id.is_baoxiu_tv) TextView isBaoxiuTv;
        @BindView(R.id.xinghao_tv) TextView xinghaoTv;
        @BindView(R.id.name_tv_01) TextView nameTv01;
        @BindView(R.id.time_tv) TextView timeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
