package com.power.kitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.kitchen.R;
import com.power.kitchen.bean.MessageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageBean> list;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<MessageBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_message_layout, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.contentTv.setText(list.get(position).getContent());
        viewHolder.timeTv.setText(list.get(position).getTime());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.if_look_iv)
        ImageView ifLookIv;
        @BindView(R.id.content_tv)
        TextView contentTv;
        @BindView(R.id.time_tv)
        TextView timeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
