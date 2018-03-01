package com.haieros.udpsend;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

/**
 * Created by Kang on 2017/11/1.
 */

public class TUdpSender {

    private Activity activity;

    public TUdpSender(WifiManager AWifiManager) {
        wifiManager = AWifiManager;
    }

    public TUdpSender(Activity activity) {
        this.activity = activity;
        wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(activity.WIFI_SERVICE);
        WifiManager.MulticastLock multicastLock = wifiManager
                .createMulticastLock("multicast.test");

        multicastLock.acquire();
    }

    WifiManager.MulticastLock multicastLock;
    WifiManager wifiManager;

    // 单播发送
    public void DoSendMsg(String ARemoteIP, int ARemotePort, String AMsg) {
        DatagramSocket skt = null;
        allowMulticast();
        try {
            skt = new DatagramSocket();
            DatagramPacket sendPgk = new DatagramPacket(
                    AMsg.getBytes(),
                    AMsg.getBytes().length,
                    InetAddress.getByName(ARemoteIP),
                    ARemotePort
            );
            skt.send(sendPgk);
        } catch (Exception er) {
        } finally {
            try {
                skt.close();
            } catch (Exception er) {
            }
            try {
                multicastLock.release();
            } catch (Exception er) {
            }
        }
    }

    // 组播发送
    public void DoSendGroupMsg(String AGroupIP, int ARemotePort, String AMsg) {
        allowMulticast();
        MulticastSocket skt = null;
        try {
            skt = new MulticastSocket(ARemotePort);
            InetAddress broadcastAddress = InetAddress.getByName(AGroupIP);
            skt.joinGroup(broadcastAddress);
            skt.setLoopbackMode(false);
            DatagramPacket sendPgk = new DatagramPacket(
                    AMsg.getBytes(),
                    AMsg.getBytes().length,
                    InetAddress.getByName(AGroupIP),
                    ARemotePort
            );
            skt.send(sendPgk);
        } catch (Exception er) {
        } finally {
            try {
                skt.close();
            } catch (Exception er) {
            }
            try {
                multicastLock.release();
            } catch (Exception er) {
            }
        }
    }

    private static final String TAG = TUdpSender.class.getSimpleName();

    public void send(String msg) {
        //-----------------------------------------------------------------------------------------
        MulticastSocket mSocket = null;//生成套接字并绑定30001端口
        try {
            if (mSocket == null) {

                mSocket = new MulticastSocket(3000);
                //mSocket.setNetworkInterface(NetworkInterface.getByName("wlan0"));
            }
            InetAddress group = InetAddress.getByName("239.0.0.0");//设定多播IP
            byte[] addr = new byte[4];
            addr[0] = (byte) 239;
            addr[1] = (byte) 0;
            addr[2] = (byte) 0;
            addr[3] = (byte) 0;
            InetAddress byAddress = InetAddress.getByAddress(addr);
            byte[] buff = msg.getBytes("utf-8");//设定多播报文的数据
            mSocket.joinGroup(byAddress);//加入多播组，发送方和接受方处于同一组时，接收方可抓取多播报文信息
            mSocket.setTimeToLive(4);//设定TTL
            //设定UDP报文（内容，内容长度，多播组，端口）
            DatagramPacket packet = new DatagramPacket(buff, buff.length, group, 30001);
            Log.e(TAG, "TUdpSender--->send--->:" + packet.toString());
            mSocket.send(packet);//发送报文
        } catch (IOException e) {
            e.printStackTrace();
        }

//-----------------------------------------------------------------------------------------
    }


    private void allowMulticast() {
        //WifiManager wifiManager=(WifiManager)Context.getSystemService(Activity.WIFI_SERVICE);

        try {
            multicastLock = wifiManager.createMulticastLock("multicast.test");
            multicastLock.acquire();
        } catch (Exception er) {
            ;
        }
    }

    public static void DoSendUdpMsg(Activity AContext, String ARemoteIP, int ARemotePort, String AMsg) {
        TUdpSender Audp = new TUdpSender(AContext);
        Audp.DoSendMsg(ARemoteIP, ARemotePort, AMsg);
        Audp = null;
    }

    public static void DoSendUdpGroupMsg(Activity AContext, String AGroupIP, int ARemotePort, String AMsg) {
        TUdpSender Audp = new TUdpSender(AContext);
        Audp.DoSendGroupMsg(AGroupIP, ARemotePort, AMsg);
        Audp = null;
    }

    public void re() {
        Log.e(TAG, "--------------------------------------");
        MulticastSocket s = null;//生成套接字并绑定端口
        try {
            s = new MulticastSocket(8600);
            s.setNetworkInterface(NetworkInterface.getByName("wlan0"));
            InetAddress group = InetAddress.getByName("224.0.0.1");//设定多播IP
            s.joinGroup(group);//接受者加入多播组，需要和发送者在同一组
            final byte[] buffer = new byte[10];
            DatagramPacket packet = new DatagramPacket(buffer, 10);//创建接收报文，以接收通过多播传递过来的报文
            s.receive(packet);//接收多播报文，程序停滞等待直到接收到报文
            Log.e(TAG, "reslut:" + new String(buffer));
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "111:" + new String(buffer), Toast.LENGTH_SHORT).show();
                }
            });
            s.close();//关闭套接字
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMultiBroadcast() throws IOException {
        Log.v(TAG, "sendMultiBroadcast...");
            /*
             * 实现多点广播时，MulticastSocket类是实现这一功能的关键，当MulticastSocket把一个DatagramPacket发送到多点广播IP地址，
             * 该数据报将被自动广播到加入该地址的所有MulticastSocket。MulticastSocket类既可以将数据报发送到多点广播地址，
             * 也可以接收其他主机的广播信息
             */
        MulticastSocket socket = new MulticastSocket(8600);
        socket.setNetworkInterface(NetworkInterface.getByName("wlan0"));
        //IP协议为多点广播提供了这批特殊的IP地址，这些IP地址的范围是224.0.0.0至239.255.255.255
        InetAddress address = InetAddress.getByName("224.0.0.1");
            /*
             * 创建一个MulticastSocket对象后，还需要将该MulticastSocket加入到指定的多点广播地址，
             * MulticastSocket使用jionGroup()方法来加入指定组；使用leaveGroup()方法脱离一个组。
             */
        socket.joinGroup(address);

            /*
             * 在某些系统中，可能有多个网络接口。这可能会对多点广播带来问题，这时候程序需要在一个指定的网络接口上监听，
             * 通过调用setInterface可选择MulticastSocket所使用的网络接口；
             * 也可以使用getInterface方法查询MulticastSocket监听的网络接口。
             */
        //socket.setInterface(address);

        DatagramPacket packet;
        //发送数据包
        Log.v(TAG, "send packet");
        byte[] buf = "Hello I am MultiSocketA".getBytes();
        packet = new DatagramPacket(buf, buf.length, address, 8601);
        socket.send(packet);

        //接收数据
//        Log.v(TAG, "receiver packet");
//        byte[] rev = new byte[512];
//        packet = new DatagramPacket(rev, rev.length);
//        socket.receive(packet);
//        Log.v(TAG, "get data = " + new String(packet.getData()).trim());    //不加trim，则会打印出512个byte，后面是乱码

        //退出组播
        socket.leaveGroup(address);
        socket.close();
    }
}
