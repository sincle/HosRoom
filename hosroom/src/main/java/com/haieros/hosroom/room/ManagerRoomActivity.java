package com.haieros.hosroom.room;

import android.graphics.Color;
import android.view.View;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseAnimActivity;

public class ManagerRoomActivity extends BaseAnimActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_manager_room;
    }

    @Override
    protected void initViews() {
        kang_title_left.setImageResource(R.drawable.icon_black_24dp);
        kang_title_menu.setImageResource(R.drawable.icon_add_24dp);
        kang_title_tabs.setVisibility(View.GONE);
        kang_title_title.setVisibility(View.VISIBLE);
        kang_title_title.setText(getResources().getString(R.string.kang_manager_room));
        kang_title_title.setTextColor(Color.WHITE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onClickOther(View view) {
        switch (view.getId()) {
            case R.id.kang_title_menu :
                break;
        }
    }
}
