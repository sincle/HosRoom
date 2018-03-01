package com.haieros.hosroom.room;

import android.content.Context;

import com.haieros.hosroom.bean.RoomBean;
import com.haieros.hosroom.data.DataRepository;
import com.haieros.hosroom.data.db.RoomDataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kang on 2017/8/21.
 */

public class RoomDataRepo extends DataRepository {

    private static RoomDataRepo instance;
    private RoomDataHelper mRoomDataHelper;
    protected RoomDataRepo(Context context) {
        super(context);
    }

    public static RoomDataRepo getInstance(Context context) {

        if (instance == null) {
            synchronized (RoomDataRepo.class) {
                if (instance == null) {
                    instance = new RoomDataRepo(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void initDb(Context context) {
        mRoomDataHelper = RoomDataHelper.getInstance(context);
        mRoomDataHelper.createTable();
    }

    public List<RoomBean> getRoomData(){

        List<RoomBean> beans = mRoomDataHelper.getAllBeans();
        return beans;
    }

    public List<String> getRoomTitle(){
        List<RoomBean> roomData = getRoomData();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < roomData.size(); i++) {
            RoomBean roomBean = roomData.get(i);
            String roomName = roomBean.getRoomName();
            list.add(roomName);
        }
        return list;
    }

    public List<String> getRoomImg(){
        List<RoomBean> roomData = getRoomData();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < roomData.size(); i++) {
            RoomBean roomBean = roomData.get(i);
            String roomName = roomBean.getRoomImgUrl();
            list.add(roomName);
        }
        return list;
    }

    public void setRoomData(RoomBean roomData) {

        mRoomDataHelper.insert(roomData);
    }

    public boolean checkNameForDuplicate(String name) {

        List<String> roomTitle = getRoomTitle();

        if(roomTitle == null){
            return true;
        }
        for (int i = 0; i < roomTitle.size(); i++) {
            if(roomTitle.get(i).equals(name)) {
                return false;
            }
        }
        return true;
    }

    public void delete(String roomName) {

        mRoomDataHelper.delete(roomName);
    }
}
