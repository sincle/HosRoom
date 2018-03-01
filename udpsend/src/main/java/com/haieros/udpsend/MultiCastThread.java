package com.haieros.udpsend;

import android.util.Log;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Kang on 2017/11/1.
 */

class MultiCastThread implements Runnable {

    MulticastSocket s;
    DatagramPacket pack;

    public MultiCastThread() {
        try {
            s = new MulticastSocket(WifiConstants.PORT_NO);
            s.joinGroup(InetAddress.getByName(WifiConstants.GROUP_ADDR));
        } catch (Exception e) {
            Log.v("Socket Error: ", e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            pack = new DatagramPacket(WifiConstants.WHO_IS.getBytes(), WifiConstants.WHO_IS.getBytes().length, InetAddress.getByName(WifiConstants.GROUP_ADDR), WifiConstants.PORT_NO);
            s.setTimeToLive(WifiConstants.TIME_TO_LIVE);
            s.send(pack);
        } catch (Exception e) {
            Log.e("Packet Sending Error: ", e.getMessage());
        }
    }
}
