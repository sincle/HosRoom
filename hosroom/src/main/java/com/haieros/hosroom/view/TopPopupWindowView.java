package com.haieros.hosroom.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.haieros.hosroom.R;

/**
 * Created by Kang on 2017/10/27.
 */

public class TopPopupWindowView {

    private final LayoutInflater mLayoutInflater;
    Context context;
    private PopupWindow popupWindow;
    View popupWindowView;

    public TopPopupWindowView(Context context) {

        this.context=context;
        this.mLayoutInflater = LayoutInflater.from(context);
        initPopupWindow();
    }

    /**
     * 初始化
     */
    public void initPopupWindow() {
        popupWindowView = mLayoutInflater.inflate(R.layout.select_layout,null,false);
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


        popupWindow.setAnimationStyle(R.style.TopSelectAnimationShow);
        // 菜单背景色。加了一点透明度
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);

        //TODO 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
        popupWindow.showAtLocation(LayoutInflater.from(context).inflate(R.layout.scene_frag, null),
                Gravity.TOP | Gravity.CENTER, 0, 0);
        // 设置背景半透明
        backgroundAlpha(0.7f);

        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*
                 * if( popupWindow!=null && popupWindow.isShowing()){
                 * popupWindow.dismiss(); popupWindow=null; }
                 */
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
        dealWithSelect();
    }


    /**
     * 处理点击事件
     */
    private void dealWithSelect() {
        //点击了关闭图标（右上角图标）
        popupWindowView.findViewById(R.id.kang_menu_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimss();
            }
        });

        popupWindowView.findViewById(R.id.kang_menu_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show();
            }
        });

        popupWindowView.findViewById(R.id.kang_menu_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "管理", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    public void dimss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
