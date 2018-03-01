package com.haieros.hosroom.data.net.okhttp.file;

/**
 * 请求体进度回调接口，比如用于文件上传中
 * Created by Kang on 2017/6/6.
 */
public interface ProgressRequestListener {

    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
