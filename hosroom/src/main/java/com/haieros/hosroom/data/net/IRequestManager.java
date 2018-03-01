package com.haieros.hosroom.data.net;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Kang on 2016/12/27.
 *
 * 网络请求管理接口
 */
public interface IRequestManager {

    /**
     * get 请求 返回实体类
     * @param url 地址
     * @param type 类型 new TypeToken<T>(){}.getType()
     * @param requestCallBack 回调
     */
    void get(String url, Type type, IRequestCallBack requestCallBack);

    /**
     * get请求 默认返回字符串
     * @param url
     * @param requestCallBack
     */
    void get(String url, IRequestCallBack requestCallBack);

    /**
     * post 请求
     * @param url 地址
     * @param requestBodyJson 请求参数
     * @param requestCallBack 结果回调
     */
    void post(String url, String requestBodyJson, IRequestCallBack requestCallBack);

    /**
     * post 返回实体类
     * @param url 地址
     * @param requestBodyJson 请求参数
     * @param type 类型 new TypeToken<T>(){}.getType()
     * @param requestCallBack 结果回调
     */
    void post(String url, String requestBodyJson, Type type, IRequestCallBack requestCallBack);

    /**
     * put 请求
     * @param url 地址
     * @param requestBodyJson 参数
     * @param requestCallBack 结果
     */
    void put(String url, String requestBodyJson, IRequestCallBack requestCallBack);

    /**
     *
     * @param url
     * @param requestBodyJson
     * @param requestCallBack
     */
    void delete(String url, String requestBodyJson, IRequestCallBack requestCallBack);

    /**
     * 上传文件
     * @param url
     * @param filePath
     * @param params
     * @param requestCallBack
     */
    void upLoadFile(String url, String filePath, HashMap<String, String> params, IRequestCallBack requestCallBack);

    /**
     * 下载文件
     * @param url
     * @param requestCallBack
     */
    void downloadFile(String url, IRequestCallBack requestCallBack);
}
