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
import com.power.kitchen.bean.CommentListBean;
import com.power.kitchen.bean.WaiteRepairBean;
import com.power.kitchen.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 * 一般
 */

public class SatisfactionAdapter extends BaseAdapter {

    private Context context;
    private List<CommentListBean.DataBean.ListsBean> list;
    private LayoutInflater inflater;

    public SatisfactionAdapter(Context context, List<CommentListBean.DataBean.ListsBean> list){
        this.context =context;
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

        viewHolder.bianhaoTv.setText(list.get(position).getOid());
        viewHolder.leixingTv.setText(list.get(position).getGoods_type_name());
        viewHolder.nameTv.setText(list.get(position).getGoods_brand_name());
        viewHolder.timeTv.setText(TimeUtils.getStrTimeYMD(list.get(position).getCreate_time()));
        viewHolder.nameTv01.setText(list.get(position).getGoods_code());
        viewHolder.leixingTv.setText(list.get(position).getGoods_model());
        viewHolder.isJiedanTv.setText("已完成");
        String goods_is_warranty = list.get(position).getGoods_is_warranty();
        if (TextUtils.equals("1",goods_is_warranty)){
            viewHolder.isBaoxiuTv.setText("保修期内");
        }else {
            viewHolder.isBaoxiuTv.setText("保修期外");
        }
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
        @BindView(R.id.gone_rl) RelativeLayout goneRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
