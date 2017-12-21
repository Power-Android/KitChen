package com.power.kitchen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.power.kitchen.R;
import com.power.kitchen.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/19.
 */

public class TypeListAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<TypeBean> list = new ArrayList<TypeBean>();
    private LayoutInflater inflater;

    private int selectedPosition = 0;

    public void setSlectedPosition(int position){
        selectedPosition = position;
    }


    public TypeListAdapter(Context context, List<TypeBean> list) {
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
            convertView = inflater.inflate(R.layout.item_type_adapter, null,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvId.setText(list.get(position).getName());
        if (selectedPosition == position){
            viewHolder.tvId.setTextColor(Color.rgb(75, 204, 86));
        }else {
            viewHolder.tvId.setTextColor(Color.parseColor("#333333"));
        }
        viewHolder.tvId.setOnClickListener(this);
        viewHolder.tvId.setTag(position);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_id)
        TextView tvId;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener OnItemClickListener) {
        this.mOnItemClickListener = OnItemClickListener;
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(v);
    }

}
