package com.haieros.hosroom.data.net.okhttp.file;

/**
 * 响应体进度回调接口，比如用于文件下载中
 * Created by Kang on 2017/6/6.
 */
public interface ProgressResponseListener {

    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
