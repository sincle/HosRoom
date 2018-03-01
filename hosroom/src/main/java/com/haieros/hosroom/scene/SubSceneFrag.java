package com.haieros.hosroom.scene;

import android.util.Log;
import android.view.View;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseFragment;

/**
 * Created by Kang on 2017/8/16.
 */

public class SubSceneFrag extends BaseFragment {

    private static final String TAG = SubSceneFrag.class.getSimpleName();
    @Override
    public View initView() {
        Log.e(TAG, "SubSceneFrag----->initView");
        View view = View.inflate(fContext, R.layout.sub_scene,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
