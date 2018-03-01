package com.haieros.hosroom.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haieros.hosroom.bean.RoomBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kang on 2017/8/18.
 */

public class RoomDataHelper implements IHelper<RoomBean> {

    private DBHelper dbHelper;
    private IHelper.ISubHelper mSubTableHelper;
    private static RoomDataHelper instance;
    private String roomTableName = "room";
    private String roomName = "room_name";
    private String roomImgUrl = "img_url";
    private String roomID = "room_id";

    private String subTableName = "sub_room";
    private String subRoomID = "room_id";
    private String subTableDev1 = "dev1";
    private String subTableDev2 = "dev2";
    private String subTableDev3 = "dev3";

    RoomDataHelper(Context context) {
        dbHelper = new DBHelper(context);
        mSubTableHelper = new SubTableHelper();
    }

    public static RoomDataHelper getInstance(Context context) {

        if (instance == null) {
            synchronized (RoomDataHelper.class) {
                if (instance == null) {
                    instance = new RoomDataHelper(context);
                }
            }
        }
        return instance;
    }

    /**
     * 查询表是否存在
     *
     * @return
     */
    @Override
    public boolean isTableExists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //查询表是否存在
        String sql = "SELECT count(*) FROM sqlite_master WHERE type=? AND name=?";
        Cursor cursor = db.rawQuery(sql, new String[]{"table", roomTableName});
        boolean isExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isExists;
    }

    /**
     * 创建表 不存在 则创建 ,存在则不创建
     */
    @Override
    public void createTable() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "create table if not exists " + roomTableName + "(" + roomID + " integer primary key autoincrement," + roomName +
                " varchar(10)," + roomImgUrl + " varcher(50))";
        db.execSQL(sql);
        String sql_sub = "create table if not exists " + subTableName + "(" + subRoomID + " integer," +
                subTableDev1 + " varchar(20)," + subTableDev2 + " varchar(20)," + subTableDev3 + " varchar(20))";
        db.execSQL(sql_sub);
        db.close();
    }

    /**
     * 查询所有数据
     * 两个表必须都有数据
     *
     * @return
     */
    @Override
    public List<RoomBean> getList() {
        List<RoomBean> result = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //String sql = "select * from num_black order by _id desc";
        //String sql1 = "select t.deviceId,t.device1,t.device2,t.device3 ,d.name from t_devacename t , t_deviceid d where t.deviceId = d.deviceId";
        String sql = "select t." + subRoomID + ",t." + subTableDev1 + ",t." + subTableDev2 + ",t." + subTableDev3 + ",d." + roomID +
                ",d." + roomName + ",d." + roomImgUrl + " from " + subTableName + " t , " + roomTableName +
                " d where t." + subRoomID + " = d." + roomID + "";
        Cursor cursor = database.rawQuery(sql, null);
        // Cursor cursor = database.query(roomTableName, null, null, null, null, null, "_id desc");
        while (cursor.moveToNext()) {
            int subRoomId = cursor.getInt(0);
            String dev1 = cursor.getString(1);
            String dev2 = cursor.getString(2);
            String dev3 = cursor.getString(3);
            int roomId = cursor.getInt(4);
            String roomName = cursor.getString(5);
            String roomImgUrl = cursor.getString(6);
            RoomBean roomBean = new RoomBean();
            RoomBean.DeviceBean deviceBean = new RoomBean.DeviceBean();
            deviceBean.setRoomID(subRoomId + "");
            deviceBean.setRoomDev1(dev1);
            deviceBean.setRoomDev2(dev2);
            deviceBean.setRoomDev3(dev3);
            roomBean.setDevice(deviceBean);
            roomBean.setRoomID(roomId + "");
            roomBean.setRoomName(roomName);
            roomBean.setRoomImgUrl(roomImgUrl);
            result.add(roomBean);
        }
        //关闭数据库连接
        cursor.close();
        database.close();
        return result;
    }

    /**
     * 升序查询 room 表中所有数据
     *
     * @return
     */
    public List<RoomBean> getAllBeans() {
        List<RoomBean> result = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String sql = "select * from " + roomTableName + " order by " + roomID + " asc";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            RoomBean roomBean = new RoomBean();
            roomBean.setRoomID(cursor.getInt(0) + "");
            roomBean.setRoomName(cursor.getString(1));
            roomBean.setRoomImgUrl(cursor.getString(2));
            result.add(roomBean);
        }
        cursor.close();
        database.close();
        return result;
    }

    /**
     * room 表中插入数据 roomBean 中的 roomName,roomImgurl
     *
     * @param roomBean
     */
    @Override
    public void insert(RoomBean roomBean) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String sql = "insert into " + roomTableName + "(" + roomName + "," + roomImgUrl + ") values(?,?)";
        database.execSQL(sql, new Object[]{roomBean.getRoomName(), roomBean.getRoomImgUrl()});
        database.close();
    }

    /**
     * 删除一条数据 房间名 可能删除多条数据 (房间名一致)
     *
     * @param room roomName
     */
    @Override
    public void delete(String room) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String sql = "delete from " + roomTableName + " where " + roomName + " = ?";
        database.execSQL(sql, new Object[]{room});

        database.close();
    }

    /**
     * 删除一条数据
     *
     * @param id
     * @return 1 成功 , 0 失败
     */
    @Override
    public int delete(int id) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        int result = database.delete(roomTableName, roomID + " = ?", new String[]{id + ""});

        database.close();

        return result;
    }

    /**
     * 更新数据
     *
     * @param roomBean
     * @return 1 成功 0 失败
     */
    @Override
    public int update(RoomBean roomBean) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(roomName, roomBean.getRoomName());
        values.put(roomImgUrl, roomBean.getRoomImgUrl());
        int update = database.update(roomTableName, values, roomID + " = ?", new String[]{roomBean.getRoomID()});
        database.close();
        return update;
    }

    /**
     * 根据room 名称 查询room id 多个重名 则返回最后一个
     *
     * @param room
     * @return
     */
    @Override
    public int qureyId(String room) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql_s = "select " + roomID + " from " + roomTableName + " where " + roomName + " = ?";
        Cursor cursor = database.rawQuery(sql_s, new String[]{room});
        int id = -1;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return id;
    }

    /**
     * 根据room 名称 查询room 所有数据
     *
     * @param room
     * @return
     */
    @Override
    public RoomBean qurey(String room) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //String sql1 = "select t.deviceId,t.device1,t.device2,t.device3 ,d.name from t_devacename t , t_deviceid d where t.deviceId = ? and d.deviceId = ?";
        String sql = "select t." + subRoomID + ",t." + subTableDev1 + ",t." + subTableDev2 + ",t." + subTableDev3 + ",d." + roomID +
                ",d." + roomName + ",d." + roomImgUrl + " from " + subTableName + " t , " + roomTableName +
                " d where t." + subRoomID + " = ? and d." + roomID + "= ?";
        int id = qureyId(room);
        RoomBean roomBean = new RoomBean();
        if (id >= 0) {
            Cursor cursor1 = database.rawQuery(sql, new String[]{id + "", id + ""});
            while (cursor1.moveToNext()) {
                int subRoomId = cursor1.getInt(0);
                String dev1 = cursor1.getString(1);
                String dev2 = cursor1.getString(2);
                String dev3 = cursor1.getString(3);
                int roomId = cursor1.getInt(4);
                String roomName = cursor1.getString(5);
                String roomImgUrl = cursor1.getString(6);
                RoomBean.DeviceBean deviceBean = new RoomBean.DeviceBean();
                deviceBean.setRoomID(subRoomId + "");
                deviceBean.setRoomDev1(dev1);
                deviceBean.setRoomDev2(dev2);
                deviceBean.setRoomDev3(dev3);
                roomBean.setDevice(deviceBean);
                roomBean.setRoomID(roomId + "");
                roomBean.setRoomName(roomName);
                roomBean.setRoomImgUrl(roomImgUrl);
            }
            cursor1.close();
        }
        database.close();
        return roomBean;
    }

    public void insertSub(RoomBean.DeviceBean deviceBean) {

        mSubTableHelper.insert(deviceBean);
    }

    public List<RoomBean.DeviceBean> getSubAllBeans() {

        return mSubTableHelper.getAllBeans();
    }

    public int deleteSub(int id) {

        return mSubTableHelper.delete(id);
    }

    public int updateSub(RoomBean.DeviceBean deviceBean) {
        return mSubTableHelper.update(deviceBean);
    }

    public RoomBean.DeviceBean qureySub(int id){
        return (RoomBean.DeviceBean) mSubTableHelper.qurey(id);
    }
    private class SubTableHelper implements IHelper.ISubHelper<RoomBean.DeviceBean> {

        /**
         * 插入数据
         * @param deviceBean
         */
        @Override
        public void insert(RoomBean.DeviceBean deviceBean) {
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String sql = "insert into " + subTableName + " (" + subRoomID + "," + subTableDev1 + "," + subTableDev2 + "," + subTableDev3 + ") values(?,?,?,?)";

            database.execSQL(sql, new Object[]{deviceBean.getRoomID(), deviceBean.getRoomDev1(), deviceBean.getRoomDev2(), deviceBean.getRoomDev3()});
            database.close();
        }

        /**
         * 删除一条数据
         *
         * @param id
         * @return 1 成功 , 0 失败
         */
        @Override
        public int delete(int id) {

            SQLiteDatabase database = dbHelper.getReadableDatabase();

            int result = database.delete(subTableName, subRoomID + " = ?", new String[]{id + ""});

            database.close();

            return result;
        }

        /**
         * 更新
         *
         * @param deviceBean
         * @return 1 成功 0 失败
         */
        @Override
        public int update(RoomBean.DeviceBean deviceBean) {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(subTableDev1, deviceBean.getRoomDev1());
            values.put(subTableDev2, deviceBean.getRoomDev2());
            values.put(subTableDev3, deviceBean.getRoomDev3());
            int update = database.update(subTableName, values, subRoomID + " = ?", new String[]{deviceBean.getRoomID()});
            database.close();
            return update;

        }

        /**
         * 获取单条数据
         * @param id
         * @return
         */
        @Override
        public RoomBean.DeviceBean qurey(int id) {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String sql_s = "select * from " + subTableName + " where " + subRoomID + " = ?";
            RoomBean.DeviceBean deviceBean = new RoomBean.DeviceBean();
            Cursor cursor = database.rawQuery(sql_s, new String[]{id + ""});
            while (cursor.moveToNext()) {
                deviceBean.setRoomID(cursor.getInt(0) + "");
                deviceBean.setRoomDev1(cursor.getString(1));
                deviceBean.setRoomDev2(cursor.getString(2));
                deviceBean.setRoomDev3(cursor.getString(3));
            }
            cursor.close();
            database.close();
            return deviceBean;
        }

        /**
         * 获取所有数据
         * @return
         */
        @Override
        public List<RoomBean.DeviceBean> getAllBeans() {
            List<RoomBean.DeviceBean> result = new ArrayList<>();
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String sql = "select * from " + subTableName + " order by " + subRoomID + " asc";
            Cursor cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                RoomBean.DeviceBean deviceBean = new RoomBean.DeviceBean();
                deviceBean.setRoomID(cursor.getInt(0) + "");
                deviceBean.setRoomDev1(cursor.getString(1));
                deviceBean.setRoomDev2(cursor.getString(2));
                deviceBean.setRoomDev3(cursor.getString(3));
                result.add(deviceBean);
            }
            cursor.close();
            database.close();
            return result;
        }
    }
}
