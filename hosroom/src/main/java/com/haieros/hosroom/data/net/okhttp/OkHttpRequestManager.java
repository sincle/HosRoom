package com.haieros.hosroom.data.net.okhttp;


import android.os.Handler;

import com.google.gson.Gson;
import com.haieros.hosroom.data.net.IRequestCallBack;
import com.haieros.hosroom.data.net.IRequestManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kang on 2016/12/27.
 * <p/>
 * 网络请求管理类
 */
public class OkHttpRequestManager implements IRequestManager {

    private static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = OkHttpRequestManager.class.getSimpleName();
    private OkHttpClient okHttpClient;
    private final Handler handler;
    private final Gson gson;

    public static OkHttpRequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OkHttpRequestManager INSTANCE = new OkHttpRequestManager();
    }

    private OkHttpRequestManager() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        handler = new Handler();
        gson = new Gson();
    }

    /**
     * get
     *
     * @param url             地址
     * @param requestCallBack 回调
     */
    @Override
    public void get(String url, Type type, IRequestCallBack requestCallBack) {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        addCallBack(request, type, requestCallBack);
    }

    @Override
    public void get(String url, IRequestCallBack requestCallBack) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        addCallBack(request, requestCallBack);
    }


    /**
     * post
     *
     * @param url             地址
     * @param requestBodyJson 请求参数
     * @param requestCallBack 结果回调
     */
    @Override
    public void post(String url, String requestBodyJson, IRequestCallBack requestCallBack) {

        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        addCallBack(request, requestCallBack);
    }
    @Override
    public void post(String url, String requestBodyJson, Type type, IRequestCallBack requestCallBack) {
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        addCallBack(request, type, requestCallBack);
    }

    /**
     *  post 添加键值对
     * @param url
     * @param params
     * @param requestCallBack
     */
    public void post(String url, HashMap<String,String> params,  Type type,IRequestCallBack requestCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        //解析参数 加入到MultipartBody 请求体
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        addCallBack(request,type,requestCallBack);
    }
    /**
     * put
     *
     * @param url             地址
     * @param requestBodyJson 参数
     * @param requestCallBack 结果
     */
    @Override
    public void put(String url, String requestBodyJson, IRequestCallBack requestCallBack) {

        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        addCallBack(request, requestCallBack);
    }

    /**
     * delete
     *
     * @param url
     * @param requestBodyJson
     * @param requestCallBack
     */
    @Override
    public void delete(String url, String requestBodyJson, IRequestCallBack requestCallBack) {

        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);

        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        addCallBack(request, requestCallBack);
    }


    /**
     * 上传文件
     *
     * @param url             地址
     * @param filePath        文件地址
     * @param params          参数 没参数传null
     * @param requestCallBack 回调
     */
    @Override
    public void upLoadFile(String url, String filePath, HashMap<String, String> params, IRequestCallBack requestCallBack) {
        //创建File
        File file = new File(filePath);

        //创建文件请求提体
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        //构建MultipartBody 请求体
        MultipartBody.Builder builder = new MultipartBody.Builder();

        //设置from表单类型
        builder.setType(MultipartBody.FORM).addFormDataPart("uploadfile", file.getName(), fileBody);

        //解析参数 加入到MultipartBody 请求体
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
        }
        MultipartBody body = builder.build();
        //创建Request
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        addCallBack(request, requestCallBack);
    }

    @Override
    public void downloadFile(String url, IRequestCallBack requestCallBack) {

    }

    private <T> void addCallBack(Request request, final Type type, final IRequestCallBack requestCallBack) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestCallBack.onFailure(e);
                    }
                });

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    //Logger.e(TAG, "response json:"+json);
                    final T t = gson.fromJson(json, type);
                    //final T t1 = o;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallBack.onSuccess(t);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallBack.onFailure(new IOException(response.message() + ",url=" + call.request().url().toString()));

                        }
                    });
                }
            }
        });
    }

    /**
     * 添加到okhttpclient
     *
     * @param request
     * @param requestCallBack
     */
    private void addCallBack(final Request request, final IRequestCallBack requestCallBack) {

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestCallBack.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallBack.onSuccess(json);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallBack.onFailure(new IOException(response.message() + ",url=" + call.request().url().toString()));

                        }
                    });
                }
            }
        });
    }
}
