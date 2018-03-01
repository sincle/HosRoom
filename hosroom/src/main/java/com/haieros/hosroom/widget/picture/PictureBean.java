package com.haieros.hosroom.widget.picture;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;


/**
 * Created by Kang on 2017/8/22.
 */

public class PictureBean {

    private long mId;
    private String mDisplayName;
    private String mPath;
    private String mParentName;
    private Uri mUri;
    private static final String TAG = PictureBean.class.getSimpleName();
    public PictureBean(long mId, String displayName, String path) {

        this.mId = mId;
        this.mDisplayName = displayName;
        this.mPath = path;
        this.mParentName = new File(path).getParentFile().getName();
        this.mUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mId);
    }

    public static PictureBean valueOf(Cursor cursor) {

        PictureBean pictureBean = new PictureBean(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)),
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)),
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        );
        Log.e(TAG, pictureBean.toString());
        return pictureBean;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmDisplayName() {
        return mDisplayName;
    }

    public void setmDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmParentName() {
        return mParentName;
    }

    public void setmParentName(String mParentName) {
        this.mParentName = mParentName;
    }

    public Uri getmUri() {
        return mUri;
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "mId=" + mId +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mPath='" + mPath + '\'' +
                ", mParentName='" + mParentName + '\'' +
                ", mUri=" + mUri +
                '}';
    }
}
