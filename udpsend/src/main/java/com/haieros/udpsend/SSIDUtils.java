package com.haieros.udpsend;

/**
 * Created by Kang on 2017/11/4.
 */

public class SSIDUtils {


    /**
     * 字符转为ascii码
     * @param character
     * @return
     */
    private byte CharToASCII(char character){

        return (byte)character;
    }

    private byte[] codeSSID(String ssid){

        return ssid.getBytes();
    }
    /**
     *
     * @return
     */
    private byte[] q(){
        return null;
    }
}
