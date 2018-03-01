package com.haieros.hosroom.main;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseFragment;
import com.haieros.hosroom.event.ImageUrlEvent;
import com.haieros.hosroom.widget.CircleImageView;
import com.haieros.hosroom.widget.picture.ImageSelectActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Kang on 2016/12/8.
 */
public class LeftDrawerFragment extends BaseFragment {

    private static final String TAG = LeftDrawerFragment.class.getSimpleName();
    @BindView(R.id.kang_navigation_name)
    TextView kangNavigationName;
    @BindView(R.id.kang_navigation_level)
    TextView kangNavigationLevel;
    @BindView(R.id.kang_navigation_afterlogin)
    LinearLayout kangNavigationAfterlogin;

    @BindView(R.id.kang_navigation_head)
    CircleImageView kang_navigation_head;

    @Override
    public View initView() {
        Log.e(TAG, "LeftDrawerFragment->initView");
        View view = View.inflate(fContext, R.layout.activity_navigation, null);
        return view;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        Log.e(TAG, "LeftDrawerFragment->initData");
    }

    @OnClick({R.id.kang_navigation_head,
            R.id.kang_navigation_login,
            R.id.activity_navigation_auto,
            R.id.activity_navigation_device_share,
            R.id.activity_navigation_family,
            R.id.activity_navigation_center,
            R.id.activity_navigation_set,
            R.id.activity_navigation_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.kang_navigation_head:
                Intent intent = new Intent(fContext, ImageSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.kang_navigation_login:
                break;
            case R.id.activity_navigation_auto:
                break;
            case R.id.activity_navigation_device_share:
                break;
            case R.id.activity_navigation_family:
                break;
            case R.id.activity_navigation_update:
                break;
            case R.id.activity_navigation_center:
                break;
            case R.id.activity_navigation_set:
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(ImageUrlEvent event) {
        Log.e(TAG, "result:" + event.getMsg());
        kang_navigation_head.setImageURI(event.getMsg());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
