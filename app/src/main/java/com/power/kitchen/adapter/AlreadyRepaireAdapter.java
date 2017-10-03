package com.power.kitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.bean.WaiteRepairBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by power on 2017/10/3.
 */

public class AlreadyRepaireAdapter extends BaseAdapter {

    private Context context;
    private List<WaiteRepairBean> list;
    private LayoutInflater inflater;

    public AlreadyRepaireAdapter(Context context, List<WaiteRepairBean> list) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_wait_repaire_layout, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.bianhaoTv.setText(list.get(position).getBianhao());
        viewHolder.leixingTv.setText(list.get(position).getLeixing());
        viewHolder.nameTv.setText(list.get(position).getName());
        viewHolder.isJiedanTv.setText(list.get(position).getIs_jiedan());
        viewHolder.isBaoxiuTv.setText(list.get(position).getIs_baoxiu());
        viewHolder.nameTv01.setText(list.get(position).getBianhao());
        viewHolder.timeTv.setText(list.get(position).getShijian());
        viewHolder.xinghaoTv.setText(list.get(position).getXinghao());
        viewHolder.moneyTv.setVisibility(View.VISIBLE);
        viewHolder.moneyTv.setText(list.get(position).getMoney());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.bianhao_tv) TextView bianhaoTv;
        @BindView(R.id.is_jiedan_tv) TextView isJiedanTv;
        @BindView(R.id.layout_01) RelativeLayout layout01;
        @BindView(R.id.name_tv) TextView nameTv;
        @BindView(R.id.leixing_tv) TextView leixingTv;
        @BindView(R.id.is_baoxiu_tv) TextView isBaoxiuTv;
        @BindView(R.id.xinghao_tv) TextView xinghaoTv;
        @BindView(R.id.name_tv_01) TextView nameTv01;
        @BindView(R.id.time_tv) TextView timeTv;
        @BindView(R.id.money_tv) TextView moneyTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}