package com.haieros.hosroom.base;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haieros.hosroom.MainActivity;
import com.haieros.hosroom.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kang on 2017/8/16.
 */

public abstract class BaseBottomFragment extends BaseFragment {

    //title id
    //中间标题
    @Nullable
    @BindView(R.id.kang_title_title)
    protected TextView kang_title_title;
    //中间tab
    @Nullable
    @BindView(R.id.kang_title_tabs)
    protected TabLayout kang_title_tabs;
    //右侧菜单
    @Nullable
    @BindView(R.id.kang_title_menu)
    protected ImageView kang_title_menu;

    @OnClick({R.id.kang_title_left,R.id.kang_title_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kang_title_left:
                ((MainActivity)fContext).getDrawerLayout().openDrawer(GravityCompat.START);
                break;
            case R.id.kang_title_menu:
                titleMenuClick(view);
        }
        onClickOther(view);
    }

    protected abstract void onClickOther(View view);

    public abstract void titleMenuClick(View view);
}
