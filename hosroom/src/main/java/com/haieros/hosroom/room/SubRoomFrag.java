package com.haieros.hosroom.room;

import android.util.Log;
import android.view.View;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseFragment;

/**
 * Created by Kang on 2017/8/16.
 */

public class SubRoomFrag extends BaseFragment {

    private static final String TAG = SubRoomFrag.class.getSimpleName();

    @Override
    public View initView() {
        Log.e(TAG, "SubRoomFrag----->initView");
        View view = View.inflate(fContext, R.layout.sub_room,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
