package com.haieros.hosroom.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.haieros.hosroom.R;
import com.haieros.hosroom.utils.Logger;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Kang on 2017/10/31.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView kang_menu_cancel;
    private TextView kang_menu_add;
    private TextView kang_menu_manager;

    public CustomDialog(@NonNull Context context) {
        super(context,R.style.MyDialogTheme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_layout);

        kang_menu_cancel = (TextView) findViewById(R.id.kang_menu_cancel);
        kang_menu_add = (TextView) findViewById(R.id.kang_menu_add);
        kang_menu_manager = (TextView) findViewById(R.id.kang_menu_manager);

        kang_menu_cancel.setOnClickListener(this);
        kang_menu_add.setOnClickListener(this);
        kang_menu_manager.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        Logger.e(TAG,"CustomDialog--->setViewLocation--->:"+height);
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.kang_menu_cancel:
                this.dismiss();
                break;
            case R.id.kang_menu_add:
                this.dismiss();
                onCustomClickListener.add();
                break;
            case R.id.kang_menu_manager:
                this.dismiss();
                onCustomClickListener.manager();
                break;
        }
    }

    public void setOnCustomClickListener(OnCustomClickListener onCustomClickListener) {
        this.onCustomClickListener = onCustomClickListener;
    }

    private OnCustomClickListener onCustomClickListener;

    public interface OnCustomClickListener {
        void add();

        void manager();
    }
}
