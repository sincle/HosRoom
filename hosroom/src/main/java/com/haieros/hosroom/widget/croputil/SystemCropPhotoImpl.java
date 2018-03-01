package com.haieros.hosroom.widget.croputil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Kang on 2017/6/9.
 */
public class SystemCropPhotoImpl implements ICropPhoto {
    public static SystemCropPhotoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SystemCropPhotoImpl INSTANCE = new SystemCropPhotoImpl();
    }

    private SystemCropPhotoImpl() {

    }

    /**
     *
     * @param activity CordovaPlugin 实例
     * @param uri 被裁剪图片uri
     * @param mDestinationUri 裁剪后保存uri
     * @param requsetCode 请求码
     */
    @Override
    public void cropPhoto(Activity activity, Uri uri, Uri mDestinationUri, int requsetCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //把裁剪的数据填入里面
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 200);
        intent.putExtra("aspectY", 200);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mDestinationUri);
        activity.startActivityForResult(intent, requsetCode);
    }
}
