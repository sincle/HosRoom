package com.haieros.hosroom.widget.picture;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kang on 2017/8/27.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = SpaceItemDecoration.class.getSimpleName();
    private float space;

    public SpaceItemDecoration(float space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int intSpace = (int) space;
        outRect.set(0, intSpace, 0, 0);

        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
        int totalItemCount = manager.getItemCount();
        // 判断是否滚动到底部
        if (lastVisibleItemPosition == (totalItemCount - 1)) {
            outRect.set(0, intSpace, 0, intSpace);
        }
    }
}
