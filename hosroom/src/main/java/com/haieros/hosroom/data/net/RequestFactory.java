package com.haieros.hosroom.data.net;


import com.haieros.hosroom.data.net.okhttp.OkHttpRequestManager;

/**
 * Created by Kang on 2016/12/27.
 *
 * 网络请求工厂类
 */
public class RequestFactory {

    public static IRequestManager getRequestManager(){
        return OkHttpRequestManager.getInstance();
    }
}
