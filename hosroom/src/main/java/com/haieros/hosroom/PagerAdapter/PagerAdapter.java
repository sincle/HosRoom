package com.haieros.hosroom.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Kang on 2017/8/16.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> data;
    private List<String> titles;

    public PagerAdapter(FragmentManager fm, List<Fragment> data, List<String> titles) {
        super(fm);
        this.data = data;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
