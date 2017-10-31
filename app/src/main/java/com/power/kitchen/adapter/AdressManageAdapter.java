package com.power.kitchen.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.activity.AdressBianJiActivity;
import com.power.kitchen.bean.AdressManageBean;
import com.power.kitchen.bean.AreaListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */

public class AdressManageAdapter extends BaseAdapter {

    private List<AreaListBean.DataBean.ListsBean> list;
    private Context context;

    public AdressManageAdapter(Context context, List<AreaListBean.DataBean.ListsBean> list) {
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
        viewHolder.phoneTv.setText(list.get(position).getTel());
        String detailAdress = list.get(position).getSheng_name()+list.get(position).getShi_name()+list.get(position).getQu_name()+
                list.get(position).getAddress();
        viewHolder.adressTv.setText(detailAdress);
        if (TextUtils.equals("1",list.get(position).getIs_def())){
            viewHolder.morenLl.setVisibility(View.VISIBLE);
        }else {
            viewHolder.morenLl.setVisibility(View.GONE);
        }

        viewHolder.bianjiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,AdressBianJiActivity.class);
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("phone",list.get(position).getTel());
                intent.putExtra("sheng_name",list.get(position).getSheng_name());
                intent.putExtra("shi_name",list.get(position).getShi_name());
                intent.putExtra("qu_name",list.get(position).getQu_name());
                intent.putExtra("adress",list.get(position).getAddress());
                intent.putExtra("is_def",list.get(position).getIs_def());
                context.startActivity(intent);
            }
        });

        viewHolder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.name_tv) TextView nameTv;
        @BindView(R.id.phone_tv) TextView phoneTv;
        @BindView(R.id.adress_tv) TextView adressTv;
        @BindView(R.id.moren_ll) LinearLayout morenLl;
        @BindView(R.id.bianji_tv) TextView bianjiTv;
        @BindView(R.id.delete_tv) TextView deleteTv;
        @BindView(R.id.moren_iv) ImageView morenIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
