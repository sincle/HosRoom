package com.haieros.hosroom;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.haieros.hosroom.base.BaseActivity;
import com.haieros.hosroom.main.LeftDrawerFragment;
import com.haieros.hosroom.main.MainContentFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @Nullable
    @BindView(R.id.main_left_drawer)
    FrameLayout mainLeftDrawer;
    @Nullable
    @BindView(R.id.main_drawerlayout)
    DrawerLayout mainDrawerlayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        initView();
    }

    @Override
    protected void initData() {
        setListener();
    }


    private void setListener() {
        //设置抽屉锁定
        mainDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mainDrawerlayout.setDrawerListener(new MyDrawerListener());
    }

    private void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_left_drawer,new LeftDrawerFragment(),"drawer");
        transaction.add(R.id.main_container,new MainContentFragment(),"container");
        transaction.commit();
    }

    public DrawerLayout getDrawerLayout() {
        return mainDrawerlayout;
    }


    /**
     * 抽屉状态监听类
     */
    private class MyDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {
            Log.e("TAG", "onDrawerOpened");
            mainDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            Log.e("TAG", "onDrawerClosed");
            mainDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }
}
