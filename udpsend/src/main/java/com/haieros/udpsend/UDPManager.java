package com.haieros.udpsend;

import android.app.Activity;
import android.net.wifi.WifiManager;

/**
 * Created by Kang on 2017/11/1.
 */

public class UDPManager {

    private WifiManager wifiManager;
    private Activity activity;

    public UDPManager(Activity activity) {
        this.activity = activity;
        wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(activity.WIFI_SERVICE);
        WifiManager.MulticastLock lock = wifiManager.createMulticastLock("WifiDevices");
        lock.acquire();
    }

    public void send(){
        Thread sendMulticast = new Thread(new MultiCastThread());
        sendMulticast.start();
    }
}
