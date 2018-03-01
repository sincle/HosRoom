package com.haieros.hosroom.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.haieros.hosroom.utils.Logger;

import butterknife.ButterKnife;

/**
 * Created by Kang on 2017/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.e(TAG,"BaseActivity--->onCreate--->:");
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    protected abstract int getContentView();

    protected abstract void initViews();

    protected abstract void initData();

}
