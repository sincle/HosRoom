package com.haieros.hosroom.widget.croputil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;


/**
 * Created by Kang on 2017/6/9.
 * 自定义裁剪工具实现类
 */
public class CustomCropPhotoImpl implements ICropPhoto {

    public static CustomCropPhotoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CustomCropPhotoImpl INSTANCE = new CustomCropPhotoImpl();
    }

    private CustomCropPhotoImpl() {

    }

    /**
     *
     * @param activity  上下文
     * @param uri 被裁剪图片uri
     * @param mDestinationUri 裁剪后保存uri
     * @param requsetCode 请求码
     */
    @Override
    public void cropPhoto(Activity activity, Uri uri, Uri mDestinationUri, int requsetCode) {

        Intent intent = new Intent();
        intent.setAction("com.ideahos.plugins.photo.crop");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mDestinationUri.getPath());
        intent.setPackage(activity.getApplicationContext().getPackageName());
        activity.startActivityForResult(intent, requsetCode);
    }
}
