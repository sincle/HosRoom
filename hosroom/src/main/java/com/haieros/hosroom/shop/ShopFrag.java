package com.haieros.hosroom.shop;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseBottomFragment;

/**
 * Created by Kang on 2017/8/16.
 */

public class ShopFrag extends BaseBottomFragment{

    private static final String TAG = ShopFrag.class.getSimpleName();

    @Override
    public View initView() {
        Log.e(TAG, "ShopFrag->initView");
        View view = View.inflate(fContext, R.layout.audio_frag,null);
        return view;
    }

    @Override
    public void initData() {
        kang_title_tabs.setVisibility(View.GONE);
        kang_title_title.setVisibility(View.VISIBLE);
        kang_title_title.setText("商城");
    }

    @Override
    protected void onClickOther(View view) {

    }

    @Override
    public void titleMenuClick(View view) {
        Toast.makeText(getActivity(), "商城", Toast.LENGTH_SHORT).show();
    }
}
