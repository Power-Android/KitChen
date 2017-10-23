package com.power.kitchen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.activity.AdressBianJiActivity;
import com.power.kitchen.bean.AdressManageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */

public class AdressManageAdapter extends BaseAdapter {

    private List<AdressManageBean> list;
    private Context context;

    public AdressManageAdapter(Context context, List<AdressManageBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_adress_adapter, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.nameTv.setText(list.get(position).getName());
        viewHolder.phoneTv.setText(list.get(position).getTelephone());
        viewHolder.adressTv.setText(list.get(position).getAddress());

        viewHolder.bianjiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,AdressBianJiActivity.class);
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("phone",list.get(position).getTelephone());
                intent.putExtra("adress",list.get(position).getAddress());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.phone_tv)
        TextView phoneTv;
        @BindView(R.id.adress_tv)
        TextView adressTv;
        @BindView(R.id.moren_ll)
        LinearLayout morenLl;
        @BindView(R.id.bianji_tv)
        TextView bianjiTv;
        @BindView(R.id.delete_tv)
        TextView deleteTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
