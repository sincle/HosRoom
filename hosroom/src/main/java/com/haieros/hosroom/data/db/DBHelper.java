package com.haieros.hosroom.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kang on 2016/5/29.
 *
 * 当调用SQLiteOpenHelper的getWritableDatabase()或者getReadableDatabase()方法
 * 获取用于操作数据库的SQLiteDatabase实例的时候，如果数据库不存在，Android系统会
 * 自动生成一个数据库，接着调用onCreate()方法，onCreate()方法在初次生成数据库时才
 * 会被调用，在onCreate()方法里可以生成数据库表结构及添加一些应用使用到的初始化数据。
 * onUpgrade()方法在数据库的版本发生变化时会被调用，一般在软件升级时才需改变版本号，
 * 而数据库的版本是由程序员控制的，假设数据库现在的版本是1，由于业务的变更，修改了数
 * 据库表结构，这时候就需要升级软件，升级软件时希望更新用户手机里的数据库表结构，
 * 为了实现这一目的，可以把原来的数据库版本设置为2(有同学问设置为3行不行？当然可以，
 * 如果你愿意，设置为100也行)，并且在onUpgrade()方法里面实现表结构的更新。当软件
 * 的版本升级次数比较多，这时在onUpgrade()方法里面可以根据原版号和目标版本号进行判断，
 * 然后作出相应的表结构及数据更新。
 */
class DBHelper extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态
    private static final String name = "app_haieros"; //数据库名称

    private static final int version = 1; //数据库版本

    protected DBHelper(Context context) {

        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "create table room(_id integer primary key autoincrement,time datetime,step integer,init_step integer)";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
