package com.haieros.hosroom.scene;

import android.graphics.Color;
import android.view.View;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseAnimActivity;

public class ManagerSceneActivity extends BaseAnimActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_manager_scene;
    }

    @Override
    protected void initViews() {
        kang_title_left.setImageResource(R.drawable.icon_black_24dp);
        kang_title_menu.setImageResource(R.drawable.icon_add_24dp);
        kang_title_tabs.setVisibility(View.GONE);
        kang_title_title.setVisibility(View.VISIBLE);
        kang_title_title.setText(getResources().getString(R.string.kang_manager_scene));
        kang_title_title.setTextColor(Color.WHITE);
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void onClickOther(View view) {

    }
}
