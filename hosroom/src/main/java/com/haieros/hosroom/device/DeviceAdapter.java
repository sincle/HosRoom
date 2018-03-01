package com.haieros.hosroom.device;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haieros.hosroom.R;
import com.haieros.hosroom.bean.DeviceBean;

import java.util.List;

/**
 * Created by Kang on 2017/11/9.
 */

class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private List<DeviceBean> list;
    private OnItemClickListener onItemClickListener;

    public DeviceAdapter(Context context, List<DeviceBean> list) {
        mLayoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.device_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceAdapter.ViewHolder holder, int position) {
        DeviceBean repo = list.get(position);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        holder.bindRepo(repo, position);
    }

    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取position
            onItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView device_item_name;

        public ViewHolder(View itemView) {
            super(itemView);
            device_item_name = (TextView) itemView.findViewById(R.id.device_item_name);
        }

        public void bindRepo(DeviceBean repo, int position) {
            device_item_name.setText(repo.getDeviceName());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int positon);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
