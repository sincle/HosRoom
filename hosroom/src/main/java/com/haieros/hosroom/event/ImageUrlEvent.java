package com.haieros.hosroom.event;

import android.net.Uri;

/**
 * Created by Kang on 2017/10/12.
 */

public class ImageUrlEvent {

    private Uri mMsg;

    public ImageUrlEvent(Uri msg) {

        mMsg = msg;
    }
    public Uri getMsg(){
        return mMsg;
    }
}
