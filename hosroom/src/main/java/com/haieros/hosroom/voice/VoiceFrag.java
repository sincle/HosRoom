package com.haieros.hosroom.voice;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseBottomFragment;

/**
 * Created by Kang on 2017/8/16.
 */

public class VoiceFrag extends BaseBottomFragment{

    private static final String TAG = VoiceFrag.class.getSimpleName();

    @Override
    protected void onClickOther(View view) {

    }

    @Override
    public void titleMenuClick(View view) {
        Toast.makeText(getActivity(), "语音菜单", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View initView() {
        Log.e(TAG, "VoiceFrag->initView");
        View view = View.inflate(fContext, R.layout.voice_frag,null);
        return view;
    }

    @Override
    public void initData() {
        kang_title_tabs.setVisibility(View.GONE);
        kang_title_title.setVisibility(View.VISIBLE);
        kang_title_title.setText("语音");
    }
}
