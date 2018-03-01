package com.haieros.hosroom.bean;

/**
 * Created by Kang on 2017/8/18.
 */

public class RoomBean extends Bean {


    /**
     * roomDev1 : 1
     * roomDev2 : 2
     * roomDev3 : 3
     */

    private DeviceBean device;
    /**
     * device : {"roomDev1":"1","roomDev2":"2","roomDev3":"3"}
     * roomID : 1
     * roomImgUrl : www.baidu.com
     * roomName : room1
     */
    private String roomID;
    private String roomImgUrl;
    private String roomName;

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomImgUrl() {
        return roomImgUrl;
    }

    public void setRoomImgUrl(String roomImgUrl) {
        this.roomImgUrl = roomImgUrl;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public static class DeviceBean extends Bean {

        private String roomID;
        private String roomDev1;
        private String roomDev2;
        private String roomDev3;

        public String getRoomID() {
            return roomID;
        }

        public void setRoomID(String roomID) {
            this.roomID = roomID;
        }
        public String getRoomDev1() {
            return roomDev1;
        }

        public void setRoomDev1(String roomDev1) {
            this.roomDev1 = roomDev1;
        }

        public String getRoomDev2() {
            return roomDev2;
        }

        public void setRoomDev2(String roomDev2) {
            this.roomDev2 = roomDev2;
        }

        public String getRoomDev3() {
            return roomDev3;
        }

        public void setRoomDev3(String roomDev3) {
            this.roomDev3 = roomDev3;
        }

        @Override
        public String toString() {
            return "DeviceBean{" +
                    "roomID='" + roomID + '\'' +
                    ", roomDev1='" + roomDev1 + '\'' +
                    ", roomDev2='" + roomDev2 + '\'' +
                    ", roomDev3='" + roomDev3 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RoomBean{" +
                "device=" + device +
                ", roomID='" + roomID + '\'' +
                ", roomImgUrl='" + roomImgUrl + '\'' +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
