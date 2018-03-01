package com.haieros.hosroom.scene;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseBottomFragment;
import com.haieros.hosroom.widget.CustomDialog;
import com.haieros.hosroom.widget.MenuPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Kang on 2017/8/16.
 */

public class SceneFrag extends BaseBottomFragment implements View.OnClickListener{

    private static final String TAG = SceneFrag.class.getSimpleName();
    private MenuPopupWindow mPopupWindow;
    @BindView(R.id.scene_frag_rv)
    RecyclerView kang_rv;
    private SceneAdapter mAdapter;
    private List<SceneBean> mList;
    private CustomDialog customDialog;

    @Override
    public View initView() {
        Log.e(TAG, "SceneFrag----->initView");
        View view = View.inflate(fContext, R.layout.scene_frag,null);

        return view;
    }

    @Override
    public void initData() {
        kang_title_tabs.setVisibility(View.GONE);
        kang_title_title.setVisibility(View.VISIBLE);
        kang_title_title.setText("场景");
        Log.e(TAG, "SceneFrag------>initData");
        mList = new ArrayList<>();
        mList.add(new SceneBean("回家",R.mipmap.pyq_normal,"1"));
        mList.add(new SceneBean("离家",R.mipmap.qq_normal,"2"));
        mList.add(new SceneBean("起床",R.mipmap.qqkj_normal,"3"));
        mList.add(new SceneBean("睡觉",R.mipmap.wxhy_normal,"4"));
        mList.add(new SceneBean("模拟",R.mipmap.xlwb_normal,"5"));
        //设置线性
        kang_rv.setLayoutManager(new GridLayoutManager(fContext,4));
        //设置分割线
//        kangExerciseRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置悬浮标题
        //kang_rv.addItemDecoration(new FloatingBarItemDecoration(fContext, mList));
        //初始化adapter
        initAdapter();
        //设置adapter
        kang_rv.setAdapter(mAdapter);
    }

    /**
     * 初始化adpater
     */
    private void initAdapter() {
        mAdapter = new SceneAdapter(fContext, mList);
        mAdapter.setOnItemClickListener(new SceneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                Toast.makeText(fContext, "position:"+positon, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onClickOther(View view) {

    }

    @Override
    public void titleMenuClick(View view) {

        customDialog = new CustomDialog(fContext);
        customDialog.show();
        customDialog.setOnCustomClickListener(new CustomDialog.OnCustomClickListener() {
            @Override
            public void add() {
                Toast.makeText(fContext, "添加", Toast.LENGTH_SHORT).show();
                startActivityWithAnim(AddScennActivity.class);
            }

            @Override
            public void manager() {
                Toast.makeText(fContext, "管理", Toast.LENGTH_SHORT).show();
                startActivityWithAnim(ManagerSceneActivity.class);
            }
        });
    }
    private void startActivityWithAnim(Class Activity) {
        Intent intent = new Intent(getActivity(), Activity);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_to_left_in,R.anim.right_to_left_out);
    }
}
