package com.haieros.hosroom.bean;

/**
 * Created by Kang on 2017/11/9.
 */

public class DeviceBean extends Bean {


    private String deviceName;

    public DeviceBean(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
