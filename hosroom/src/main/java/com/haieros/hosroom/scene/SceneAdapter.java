package com.haieros.hosroom.scene;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haieros.hosroom.R;

import java.util.List;

/**
 * Created by Kang on 2017/10/27.
 */

public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.ViewHolder> implements View.OnClickListener{

    private final LayoutInflater mLayoutInflater;
    private List<SceneBean> mList;
    private OnItemClickListener mOnItemClickListener;

    public SceneAdapter(Context context, List<SceneBean> mList) {
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SceneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.scene_item, parent, false);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SceneBean repo = mList.get(position);
        holder.bindRepo(repo, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView kang_scene_item_img;
        private final TextView kang_scene_item_name;

        public ViewHolder(View itemView) {
            super(itemView);
            kang_scene_item_img = (ImageView) itemView.findViewById(R.id.scene_item_img);
            kang_scene_item_name = (TextView) itemView.findViewById(R.id.scene_item_name);

        }

        public void bindRepo(SceneBean repo, int position) {
            kang_scene_item_img.setImageResource(repo.getSceneImg());
            kang_scene_item_name.setText(repo.getSceneName());
            itemView.setTag(position);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int positon);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
