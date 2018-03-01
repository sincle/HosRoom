package com.haieros.hosroom.room;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haieros.hosroom.PagerAdapter.PagerAdapter;
import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseBottomFragment;
import com.haieros.hosroom.utils.Logger;
import com.haieros.hosroom.widget.MenuPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Kang on 2016/12/9.
 */
public class RoomFrag extends BaseBottomFragment implements View.OnClickListener {
    private static final String TAG = RoomFrag.class.getSimpleName();
    private static final int REQUEST_CODE_ADD = 0x01;

    @BindView(R.id.kang_room_viewpager)
    ViewPager kang_room_viewpager;

    //子页面
    private List<Fragment> mFragLists;
    //子页面标题
    private List<String> mTitles;
    //子页面适配器
    private PagerAdapter mAdapter;
    private MenuPopupWindow mPopupWindow;
    private RoomDataRepo mRoomDataRepo;

    @Override
    public View initView() {
        Log.e(TAG, "RoomFrag->initView");
        View view = View.inflate(fContext, R.layout.room_frag, null);
        return view;
    }

    @Override
    public void initData() {
        Log.e(TAG, "RoomFrag->initData");

        mTitles = new ArrayList<>();
        mRoomDataRepo = RoomDataRepo.getInstance(getActivity());
        initTitleTab();
    }

    /**
     * tab 初始化数据
     */
    private void initTitleTab() {
        mFragLists = new ArrayList<>();
        mTitles = mRoomDataRepo.getRoomTitle();
        for (int i = 0; i < mTitles.size(); i++) {
            mFragLists.add(new SubRoomFrag());
        }
        kang_title_title.setVisibility(View.GONE);
        mAdapter = new PagerAdapter(getFragmentManager(), mFragLists, mTitles);
        kang_room_viewpager.setAdapter(mAdapter);
        kang_title_tabs.setupWithViewPager(kang_room_viewpager);
        kang_title_tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void titleMenuClick(View view) {
        mPopupWindow = new MenuPopupWindow(getActivity(), this);
        mPopupWindow.showPopupWindow(view);
    }

    @Override
    protected void onClickOther(View view) {
        switch (view.getId()) {
            case R.id.kang_room_add:

                mPopupWindow.dismiss();
                startActivityWithAnim(AddRoomActivity.class);
                break;
            case R.id.kang_room_delete:

                int currentItem = kang_room_viewpager.getCurrentItem();
                mPopupWindow.dismiss();
                if(mTitles.isEmpty()) {
                    Toast.makeText(getActivity(), "请先添加房间", Toast.LENGTH_SHORT).show();
                    return;
                }
                deleteData(currentItem);
                break;
        }
    }

    /**
     * 删除数据 同步数据库
     * @param currentItem
     */
    private void deleteData(int currentItem) {

        //数据库数据删除
        String roomName = mTitles.get(currentItem);
        mRoomDataRepo.delete(roomName);

        //内存内数据删除
        mFragLists.remove(currentItem);
        Logger.e(TAG,"RoomFrag--->deleteData--->:"+mFragLists.toString());
        mTitles.remove(currentItem);
        mAdapter.notifyDataSetChanged();

        //删除顺序
        if(mTitles.size() >= currentItem+1) {

            kang_room_viewpager.setCurrentItem(currentItem);
        }else {
            kang_room_viewpager.setCurrentItem(currentItem-1);
        }

    }

    /**
     * 启动activity
     *
     * @param activity
     */
    private void startActivityWithAnim(Class activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivityForResult(intent, REQUEST_CODE_ADD);
        getActivity().overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_ADD:
                if (resultCode == RESULT_OK) {

                    initTitleTab();
                    //先添加的为选中状态
                    kang_room_viewpager.setCurrentItem(mTitles.size());
                }
                break;
        }
    }
}
