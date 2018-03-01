package com.haieros.ssid;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by Kang on 2017/11/4.
 */

public class UdpConfig {

    private static final String TAG = UdpConfig.class.getSimpleName();
    private static int CONFIG_PORT = 30001;
    private WifiManager.MulticastLock multicastLock;

    public UdpConfig(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        multicastLock = wifiManager.createMulticastLock("multicast.wifi");
        multicastLock.acquire();
    }

    /**
     * 每两个字节 发送一次
     *
     * @param ssid
     */
    private void splitAndSend(byte[] ssid) throws Throwable {

        //新建2个字节数组
        byte[] subBytes = new byte[2];
        int i = 0;

        //开启循环
        while (i < ssid.length) {

            //取2个字节
            subBytes[0] = ssid[i];
            if (++i < ssid.length) {
                subBytes[1] = ssid[i];
            } else {
                subBytes[1] = 0;
            }
            ++i;

            //2个字节内容 ------> ip字符串
            String ipByte = change2CharToIpString(subBytes);

            //ip 字符串 转为 ip 地址
            InetAddress ipAddress = stringToIpAddress(ipByte);

            //包序号
            int len = i / 2;

            //发送IP 及 内容
            send(ipAddress, createLengthContent(len));
        }
    }

    /**
     * 字符串 转为 ip 地址
     *
     * @param ipByte
     * @return
     * @throws UnknownHostException
     */
    private InetAddress stringToIpAddress(String ipByte) throws UnknownHostException {
        return InetAddress.getByName(ipByte);
    }

    /**
     * 创建内容
     *
     * @param len 长度
     * @return
     */
    private byte[] createLengthContent(int len) {
        byte[] bytes = new byte[len];
        return bytes;
    }

    /**
     * 发送数据包
     *
     * @param byAddress
     * @param msg
     */
    private void send(InetAddress byAddress, byte[] msg) throws IOException {
        MulticastSocket mSocket = null;
        if (mSocket == null) {
            //生成套接字并绑定端口
            mSocket = new MulticastSocket(CONFIG_PORT);
        }
        //加入多播组，发送方和接受方处于同一组时，接收方可抓取多播报文信息
        mSocket.joinGroup(byAddress);
        //设定TTL
        mSocket.setTimeToLive(4);
        //设定UDP报文（内容，内容长度，多播组，端口）
        DatagramPacket packet = new DatagramPacket(msg, msg.length, byAddress, CONFIG_PORT);
        Log.e(TAG, "UdpConfig--->send--->: start");
        mSocket.send(packet);//发送报文
    }

    /**
     * byte 数组 转为 ip 地址
     *
     * @param ipByte
     */
    private InetAddress byteToIpAddress(byte[] ipByte) throws UnknownHostException {
        return InetAddress.getByAddress(ipByte);
    }

    /**
     * byte数组 转 ip 格式byte数组
     *
     * @param subBytes
     */
    private byte[] change2CharToIpByte(byte[] subBytes) throws Throwable {

        if (subBytes.length == 0) {
            throw new Throwable("byte[] is no value");
        }
        byte[] ip = new byte[4];
        ip[0] = (byte) 239;
        ip[1] = (byte) 127;
        byte sub_0 = subBytes[0];
        byte sub_1 = subBytes[1];
        ip[2] = (byte) sub_0;
        if (sub_1 != 0) {
            ip[3] = (byte) sub_1;
            return ip;
        }
        ip[3] = (byte) 0;
        return ip;
    }

    /**
     * byte 数组转为 IP 字符串
     *
     * @param subBytes
     */
    private String change2CharToIpString(byte[] subBytes) throws Throwable {

        if (subBytes.length == 0) {
            throw new Throwable("byte[] is no value");
        }
        String ip = "239.127.";
        short sub_0 = (short) (subBytes[0] & 0xFF);
        ip = ip + sub_0 + ".";
        short sub_1 = (short) (subBytes[1] & 0xFF);
        Log.e(TAG, "sub_o:" + sub_0 + ",sub_1" + sub_1);
        if (sub_1 != -1) {
            ip = ip + sub_1;
            Log.e(TAG, "ip:" + ip);
            return ip;
        }
        ip = ip + "0";
        Log.e(TAG, "ip:" + ip);
        return ip;
    }

    /**
     * 字符串转为acsii码 字节数组
     *
     * @param content
     * @return 字节数组
     */
    private byte[] codeContent(String content) throws Throwable {
        if (content == null || "".equals(content)) {
            throw new Throwable("content is null or no value");
        }
        return content.getBytes();
    }

    /**
     * 开始配置
     *
     * @param content 参数
     */
    public void startConfig(String content) throws Throwable {

        byte[] contentBytes = codeContent(content);
        //发送开始标志
        send(stringToIpAddress(change2CharToIpString(new byte[]{(byte) 255, (byte) 255})), createLengthContent(0));
        splitAndSend(contentBytes);
        //发送结束
        send(stringToIpAddress(change2CharToIpString(new byte[]{(byte) 255, (byte) 254})), createLengthContent((content.length() + 1) / 2 + 1));
    }

    public void start() throws Throwable {
        send(stringToIpAddress(code(new byte[]{0, (byte) 161,0})), createLengthContent(0));
        send(stringToIpAddress(code(new byte[]{1, (byte) 160,0})), createLengthContent(1));
        send(stringToIpAddress(code(new byte[]{2, (byte) 157,2})), createLengthContent(2));
        send(stringToIpAddress(code(new byte[]{3,109,49})), createLengthContent(3));
        send(stringToIpAddress(code(new byte[]{4, (byte) 202, (byte) 211})), createLengthContent(4));

        ///////////////////radom value
        send(stringToIpAddress(code(new byte[]{5,75,81})), createLengthContent(5));
        send(stringToIpAddress(code(new byte[]{6,109,46})), createLengthContent(6));
        send(stringToIpAddress(code(new byte[]{7,38,116})), createLengthContent(7));
        send(stringToIpAddress(code(new byte[]{8,56,97})), createLengthContent(8));
        //////////////////////

        send(stringToIpAddress(code(new byte[]{9,117,35})), createLengthContent(9));
        send(stringToIpAddress(code(new byte[]{10,95,56})), createLengthContent(10));
        send(stringToIpAddress(code(new byte[]{11,73,77})), createLengthContent(11));
        send(stringToIpAddress(code(new byte[]{12,4, (byte) 145})), createLengthContent(12));
        send(stringToIpAddress(code(new byte[]{13, (byte) 137,11})), createLengthContent(13));
        send(stringToIpAddress(code(new byte[]{14, (byte) 169, (byte) 234})), createLengthContent(14));
        send(stringToIpAddress(code(new byte[]{15, (byte) 252, (byte) 150})), createLengthContent(15));
        send(stringToIpAddress(code(new byte[]{16, (byte) 233, (byte) 168})), createLengthContent(16));
        send(stringToIpAddress(code(new byte[]{17, (byte) 200, (byte) 200})), createLengthContent(17));
        send(stringToIpAddress(code(new byte[]{18,58,85})), createLengthContent(18));
        send(stringToIpAddress(code(new byte[]{19,126,16})), createLengthContent(19));
        send(stringToIpAddress(code(new byte[]{20, (byte) 223, (byte) 174})), createLengthContent(20));
        send(stringToIpAddress(code(new byte[]{21,87,53})), createLengthContent(21));
        send(stringToIpAddress(code(new byte[]{22,36,103})), createLengthContent(22));
        send(stringToIpAddress(code(new byte[]{23,61,77})), createLengthContent(23));
        send(stringToIpAddress(code(new byte[]{24, (byte) 213, (byte) 180})), createLengthContent(24));
        send(stringToIpAddress(code(new byte[]{25, (byte) 135,1})), createLengthContent(25));
        send(stringToIpAddress(code(new byte[]{26,38,97})), createLengthContent(26));
        send(stringToIpAddress(code(new byte[]{27, (byte) 222, (byte) 168})), createLengthContent(27));
        send(stringToIpAddress(code(new byte[]{28, (byte) 196, (byte) 193})), createLengthContent(28));
        send(stringToIpAddress(code(new byte[]{29,70,62})), createLengthContent(29));
        send(stringToIpAddress(code(new byte[]{30, (byte) 155, (byte) 232})), createLengthContent(30));
        send(stringToIpAddress(code(new byte[]{31,115,15})), createLengthContent(31));
        send(stringToIpAddress(code(new byte[]{32,29,100})), createLengthContent(32));
        send(stringToIpAddress(code(new byte[]{33, (byte) 226, (byte) 158})), createLengthContent(33));
        send(stringToIpAddress(code(new byte[]{34, (byte) 179, (byte) 204})), createLengthContent(34));
        send(stringToIpAddress(code(new byte[]{35, (byte) 133, (byte) 249})), createLengthContent(35));
        send(stringToIpAddress(code(new byte[]{36, (byte) 184, (byte) 197})), createLengthContent(36));
        send(stringToIpAddress(code(new byte[]{37,53,71})), createLengthContent(37));
        send(stringToIpAddress(code(new byte[]{38, (byte) 250, (byte) 129})), createLengthContent(38));
        send(stringToIpAddress(code(new byte[]{39,1,121})), createLengthContent(39));
        send(stringToIpAddress(code(new byte[]{40, (byte) 136, (byte) 241})), createLengthContent(40));
        send(stringToIpAddress(code(new byte[]{41,37,83})), createLengthContent(41));
        send(stringToIpAddress(code(new byte[]{42,95,24})), createLengthContent(42));
        send(stringToIpAddress(code(new byte[]{43, (byte) 232, (byte) 142})), createLengthContent(43));
        send(stringToIpAddress(code(new byte[]{44,86,31})), createLengthContent(44));
        send(stringToIpAddress(code(new byte[]{45, (byte) 227, (byte) 145})), createLengthContent(45));
        send(stringToIpAddress(code(new byte[]{46, (byte) 184, (byte) 187})), createLengthContent(46));
        send(stringToIpAddress(code(new byte[]{47,39,75})), createLengthContent(47));
        send(stringToIpAddress(code(new byte[]{48, (byte) 237, (byte) 132})), createLengthContent(48));
    }

    public String code(byte[] bytes) throws Throwable {
        if (bytes.length == 0) {
            throw new Throwable("byte[] is no value");
        }
        String ip = "239.";
        short sub_0 = (short) (bytes[0] & 0xFF);
        ip = ip + sub_0 + ".";
        short sub_1 = (short) (bytes[1] & 0xFF);
        ip = ip + sub_1+".";
        short sub_2 = (short) (bytes[2] & 0xFF);
        ip = ip + sub_2;
        Log.e(TAG, "ip:" + ip);
        return ip;
    }
}
