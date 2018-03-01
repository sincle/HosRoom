package com.haieros.hosroom.data.db;

import com.haieros.hosroom.bean.Bean;

import java.util.List;

/**
 * Created by Kang on 2017/8/18.
 */

public interface IHelper<T extends Bean> {

    boolean isTableExists();

    void createTable();

    /**
     * 获取所有数据包括子表
     * @return
     */
    List<T> getList();

    /**
     * 获取所有数据 不包含子表
     * @return
     */
    List<T> getAllBeans();

    void insert(T t);

    void delete(String name);

    int delete(int id);

    int update(T t);

    int qureyId(String name);

    T qurey(String name);

    interface ISubHelper<V extends Bean> {

        void insert(V v);

        int delete(int id);

        int update(V v);

        V qurey(int id);

        List<V> getAllBeans();
    }

}


