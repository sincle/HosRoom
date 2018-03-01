package com.haieros.hosroom.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haieros.hosroom.R;
import com.haieros.hosroom.data.db.IHelper;
import com.haieros.hosroom.utils.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kang on 2017/8/17.
 */

public abstract class BaseAnimActivity extends BaseActivity {

    private static final String TAG = BaseAnimActivity.class.getSimpleName();

    protected IHelper mDatabaseUtil;
    //title id
    @BindView(R.id.kang_title_left)
    protected ImageView kang_title_left;
    //中间标题
    @BindView(R.id.kang_title_title)
    protected TextView kang_title_title;
    //中间tab
    @BindView(R.id.kang_title_tabs)
    protected TabLayout kang_title_tabs;
    //右侧菜单
    @BindView(R.id.kang_title_menu)
    protected ImageView kang_title_menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.e(TAG,"BaseAnimActivity--->onCreate--->:");
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#06BEAB"));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right_in,R.anim.left_to_right_out);
    }

    @OnClick({R.id.kang_title_left,R.id.kang_title_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kang_title_left:
                finish();
                break;
        }
        onClickOther(view);
    }

    protected abstract void onClickOther(View view);
}
