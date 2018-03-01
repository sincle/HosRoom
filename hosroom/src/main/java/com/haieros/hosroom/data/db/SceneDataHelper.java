package com.haieros.hosroom.data.db;

import android.content.Context;

import com.haieros.hosroom.bean.SceneBean;

import java.util.List;

/**
 * Created by Kang on 2017/8/18.
 */

public class SceneDataHelper implements IHelper<SceneBean>{

    private DBHelper dbHelper;
    private static SceneDataHelper instance;

    SceneDataHelper(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static SceneDataHelper getInstance(Context context) {

        if (instance == null) {
            synchronized (SceneDataHelper.class) {
                if (instance == null) {
                    instance = new SceneDataHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isTableExists() {
        return false;
    }

    @Override
    public void createTable() {

    }

    @Override
    public List<SceneBean> getList() {
        return null;
    }

    @Override
    public List<SceneBean> getAllBeans() {
        return null;
    }

    @Override
    public void insert(SceneBean sceneBean) {

    }

    @Override
    public void delete(String name) {

    }

    @Override
    public int delete(int id) {
        return 0;
    }


    @Override
    public int update(SceneBean sceneBean) {
        return 1;
    }

    @Override
    public int qureyId(String name) {
        return 0;
    }

    @Override
    public SceneBean qurey(String name) {
        return null;
    }

}
