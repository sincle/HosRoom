package com.haieros.ssid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private UdpConfig udpConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String ssid = "ideahos;ideahos1708";
        byte[] bytes = ssid.getBytes();
        String re = "";
        for (int i = 0; i < bytes.length; i++) {
            re += bytes[i]+" ";
        }
        Log.e(TAG, "re:"+re);
        //int i = CRC16.calcCrc16(bytes);
        //Log.e(TAG, "crc:"+i);
        udpConfig = new UdpConfig(MainActivity.this);
        Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(){
                    public void run(){
                        while (true){
                            try {
                                udpConfig.startConfig(ssid);
//                                udpConfig.start();
                            } catch (Throwable throwable) {
                                Log.e(TAG, "throwable:"+throwable.getMessage());
                            }

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            }
        });
    }
    /**
     * 每两个字节
     * @param ssid
     */
    private void code2Char(byte[] ssid) throws Throwable {

        byte[] subBytes = new byte[2];
        int i = 0;
        while (i < ssid.length){
            subBytes[0] = ssid[i];
            if(++i < ssid.length) {
                subBytes[1] = ssid[i];
            }else {
                subBytes[1] = -1;
            }
            ++i;
            Log.e(TAG, "subByte:"+subBytes[0]+","+subBytes[1]);
            String ipByte = change2CharToIpString(subBytes);
            Log.e(TAG, "ipString:"+ipByte);
            InetAddress ipAddress = stringToIpAddress(ipByte);
            int len = i / 2;
            Log.e(TAG, "len:"+len);
            send(ipAddress, createLengthContent(len));
        }
    }

    private InetAddress stringToIpAddress(String ipByte) throws UnknownHostException {
        return InetAddress.getByName(ipByte);
    }

    /**
     * 创建内容
     * @param len
     * @return
     */
    private byte[] createLengthContent(int len) {
        byte[] bytes = new byte[len];
        Log.e(TAG, "bytes.len:"+bytes.length);
        return bytes;
    }

    /**
     * 发送数据包
     * @param byAddress
     * @param msg
     */
    public void send(InetAddress byAddress,byte[] msg){
        MulticastSocket mSocket = null;//生成套接字并绑定30001端口
        try {
            if (mSocket == null) {

                mSocket = new MulticastSocket(30001);
                //mSocket.setNetworkInterface(NetworkInterface.getByName("wlan0"));
            }
//            InetAddress group = InetAddress.getByName("239.0.0.0");//设定多播IP
//            byte[] addr = new byte[4];
//            addr[0] = (byte) 239;
//            addr[1] = (byte) 0;
//            addr[2] = (byte) 0;
//            addr[3] = (byte) 0;
//            InetAddress byAddress = InetAddress.getByAddress(addr);
            mSocket.joinGroup(byAddress);//加入多播组，发送方和接受方处于同一组时，接收方可抓取多播报文信息
            mSocket.setTimeToLive(4);//设定TTL
            //设定UDP报文（内容，内容长度，多播组，端口）
            DatagramPacket packet = new DatagramPacket(msg, msg.length, byAddress, 30001);
            Log.e(TAG, "TUdpSender--->send--->:" + packet.toString());
            mSocket.send(packet);//发送报文
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    * byte 数组 转为 ip 地址
     * @param ipByte
     */
    private InetAddress byteToIpAddress(byte[] ipByte) throws UnknownHostException {
       return InetAddress.getByAddress(ipByte);
    }

    /**
     * 转为ip byte[]
     * @param subBytes
     */
    private byte[] change2CharToIpByte(byte[] subBytes) throws Throwable {

        if(subBytes.length == 0) {
            throw new Throwable("byte[] is no value");
        }
        byte[] ip = new byte[4];
        ip[0] = (byte) 239;
        ip[1] = (byte) 127;
        byte sub_0 = subBytes[0];
        byte sub_1 = subBytes[1];
        ip[2] = (byte)sub_0;
        if(sub_1 != -1) {
            ip[3] = (byte)sub_1;
            return ip;
        }
        ip[3] = (byte)0;
        return ip;
    }
    /**
     * 转为ip byte[]
     * @param subBytes
     */
    private String change2CharToIpString(byte[] subBytes) throws Throwable {


        if(subBytes.length == 0) {
            throw new Throwable("byte[] is no value");
        }
        String ip = "239.127.";
        byte sub_0 = subBytes[0];
        ip = ip + sub_0+".";
        byte sub_1 = subBytes[1];
        if(sub_1 != -1) {
            ip = ip + sub_1;
            Log.e(TAG, "ip:"+ip);
            return ip;
        }
        ip = ip+"0";
        return ip;
    }

    /**
     * 字符串转为acsii码 字节数组
     * @param ssid ssid
     * @return 字节数组
     */
    private byte[] codeSSID(String ssid) throws Throwable {
        if(ssid == null || "".equals(ssid)) {
            throw new Throwable("ssid is null or no value");
        }
        return ssid.getBytes();
    }
}
