package com.haieros.hosroom.widget.croputil;

/**
 * Created by Kang on 2017/6/9.
 * 裁剪工厂
 */
public class CropPhotoFactory {

    /**
     * 自定义裁剪工具
     * @return
     */
    public static ICropPhoto getCustomCropPhotoImpl(){
        return CustomCropPhotoImpl.getInstance();
    }

    /**
     * 系统裁剪工具
     * @return
     */
    public static ICropPhoto getSystemCropPhotoImpl(){
        return SystemCropPhotoImpl.getInstance();
    }
}
