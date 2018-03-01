package com.haieros.hosroom.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseFragment;
import com.haieros.hosroom.device.DeviceFrag;
import com.haieros.hosroom.room.RoomFrag;
import com.haieros.hosroom.scene.SceneFrag;
import com.haieros.hosroom.shop.ShopFrag;
import com.haieros.hosroom.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kang on 2016/12/8.
 */
public class MainContentFragment extends BaseFragment {


    private static final String TAG = MainContentFragment.class.getSimpleName();

    @BindView(R.id.content_viewpager)
    NoScrollViewPager contentViewpager;
    @BindView(R.id.main_radiogroup)
    RadioGroup mainRadiogroup;
    private List<BaseFragment> mList;
    private MPagerAdapter mAdapter;

    @Override
    public View initView() {
        Log.e(TAG, "MainContentFragment->initView");
        View view = View.inflate(fContext, R.layout.activity_content, null);
        return view;
    }

    @Override
    public void initData() {
        Log.e(TAG, "MainContentFragment->initData");
        mList = new ArrayList<>();

        //房间页面
        RoomFrag homeFrag = new RoomFrag();
        mList.add(homeFrag);

        //场景页面
        SceneFrag sceneFrag = new SceneFrag();
        mList.add(sceneFrag);

        //语音页面
//        VoiceFrag voiceFrag = new VoiceFrag();
//        mList.add(voiceFrag);

        //设备页面
        DeviceFrag deviceFrag = new DeviceFrag();
        mList.add(deviceFrag);

        //音响页面
        ShopFrag audioFrag = new ShopFrag();
        mList.add(audioFrag);

        mAdapter = new MPagerAdapter(getFragmentManager());
        contentViewpager.setAdapter(mAdapter);
        contentViewpager.setOffscreenPageLimit(4);
        setListener();
        mainRadiogroup.check(R.id.main_home_1);
    }

    private void setListener() {

        mainRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_home_1:
                        contentViewpager.setCurrentItem(0, false);
                        break;
                    case R.id.main_home_2:
                        contentViewpager.setCurrentItem(1, false);
                        break;
                    case R.id.main_home_4:
                        contentViewpager.setCurrentItem(2, false);
                        break;
                    case R.id.main_home_5:
                        contentViewpager.setCurrentItem(3, false);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.main_voice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_voice:
                Toast.makeText(fContext, "语音", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private class MPagerAdapter extends FragmentPagerAdapter {

        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
