package com.haieros.hosroom.widget.croputil;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by Kang on 2017/6/9.
 * 裁剪接口
 */
public interface ICropPhoto {

    /**
     * 裁剪图片
     * @param activity 上下文
     * @param uri 被裁剪图片uri
     * @param mDestinationUri 裁剪后保存uri
     * @param requsetCode 请求码
     */
    void cropPhoto(Activity activity, Uri uri, Uri mDestinationUri, int requsetCode);
}
