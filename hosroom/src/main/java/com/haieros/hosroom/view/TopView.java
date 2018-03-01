package com.haieros.hosroom.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.haieros.hosroom.R;

/**
 * Created by Kang on 2017/10/27.
 */

public class TopView extends PopupWindow{

    private final View conentView;

    public TopView(Activity context, View.OnClickListener listener) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.room_frag_menu, null);
        TextView kang_room_menu = (TextView) conentView.findViewById(R.id.kang_room_add);
        TextView kang_room_manager = (TextView) conentView.findViewById(R.id.kang_room_delete);
        kang_room_menu.setOnClickListener(listener);
        kang_room_manager.setOnClickListener(listener);
        // 设置SelectPicPopupWindow的View
        setContentView(conentView);
        //设置宽高
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setOutsideTouchable(false);
        setFocusable(true);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }
}
