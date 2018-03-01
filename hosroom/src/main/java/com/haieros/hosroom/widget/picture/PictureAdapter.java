package com.haieros.hosroom.widget.picture;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haieros.hosroom.R;

/**
 * Created by Kang on 2017/8/22.
 */

public class PictureAdapter extends CursorAdapter<PictureAdapter.PictureAdapterHolder> implements View.OnClickListener {

    private static final String TAG = PictureAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener = null;
    private final LayoutInflater inflater;
    private int img_loading = R.mipmap.image_not_exist;
    private int camera_ic = R.mipmap.ic_camera;
    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter;
     *                Currently it accept {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public PictureAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void onContentChanged() {

    }

    @Override
    public void onBindViewHolder(PictureAdapter.PictureAdapterHolder holder, Cursor cursor, int position) {
        if(position == 0) {
            holder.itemView.setTag("0");
            Glide.with(holder.kang_pic_img.getContext())
                    .load(camera_ic)
                    .centerCrop()
                    .error(img_loading)
                    .placeholder(img_loading)
                    .dontAnimate()
                    .into(holder.kang_pic_img);
        }else{
            PictureBean bean = PictureBean.valueOf(cursor);
            //将position保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(bean.getmPath());
            Glide.with(holder.kang_pic_img.getContext())
                    .load(bean.getmUri())
                    .centerCrop()
                    .error(img_loading)
                    .placeholder(img_loading)
                    .dontAnimate()
                    .into(holder.kang_pic_img);
        }
    }

    @Override
    public PictureAdapter.PictureAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.picture_item, parent, false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new PictureAdapterHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));
        }
    }

    public class PictureAdapterHolder extends RecyclerView.ViewHolder {

        public ImageView kang_pic_img;

        public PictureAdapterHolder(View itemView) {
            super(itemView);
            kang_pic_img = (ImageView) itemView.findViewById(R.id.kang_pic_img);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
