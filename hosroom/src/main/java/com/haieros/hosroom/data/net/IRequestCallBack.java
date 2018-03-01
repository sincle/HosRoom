package com.haieros.hosroom.data.net;

/**
 * Created by Kang on 2016/12/27.
 *
 * 网络请求回调接口
 */
public interface IRequestCallBack<T>{

    /**
     * 成功回调
     * @param response
     */
    void onSuccess(T response);

    /**
     * 失败回调
     * @param throwable
     */
    void onFailure(Throwable throwable);
}
