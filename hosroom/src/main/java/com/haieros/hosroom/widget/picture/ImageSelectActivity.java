package com.haieros.hosroom.widget.picture;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haieros.hosroom.R;
import com.haieros.hosroom.widget.croputil.CropPhotoFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

/**
 * Created by Kang on 2017/10/30.
 */

public class ImageSelectActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>,PictureAdapter.OnItemClickListener {

    private static final int REQUEST_CODE = 0x01;
    private static final int CAMERA_REQUEST_CODE = 0x02;
    private static String[] PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DISPLAY_NAME
    };
    private static final String ORDER_BY = MediaStore.Images.Media._ID + " DESC";
    private PictureAdapter mAdapter;
    private RecyclerView kang_pictrue_review;
    private Uri mDestinationUri;
    private File mCameraPhoto;
    private Uri mCameraPhotoUri;

    @Subscribe
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_select);
        initView();
        initData();
    }


    public void initData() {
        EventBus.getDefault().register(this);
        getSupportLoaderManager().initLoader(1, null, this);
    }

    private void initView() {

        ImageView kang_title_left = (ImageView) findViewById(R.id.kang_title_left);
        kang_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView kang_title_title = (TextView) findViewById(R.id.kang_title_title);
        kang_title_title.setText("头像选取");
        kang_pictrue_review = (RecyclerView) findViewById(R.id.kang_pictrue_review);
        //创建默认的线性LayoutManager
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        int space = getResources().getDimensionPixelSize(R.dimen._10dp);
        kang_pictrue_review.addItemDecoration(new SpaceItemDecoration(space));
        kang_pictrue_review.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        kang_pictrue_review.setHasFixedSize(true);

        //创建并设置Adapter
        mAdapter = new PictureAdapter(this, null,1);
        kang_pictrue_review.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //创建了一个CursorLoader，查询的数据有文件地址，名次，添加时间，id并且按照添加时间排序
        CursorLoader cursorLoader = new CursorLoader(
                this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                PROJECTION,
                null,
                null,
                ORDER_BY);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Context context = this;
        if (context == null) {
            return;
        }
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(View view, String path) {

        File mCameraPhoto = new File(this.getExternalFilesDir("Photo"), "temp_photo_head.jpg");
        mDestinationUri = Uri.fromFile(mCameraPhoto);
        if("0".equals(path)) {
            //进入相机拍照
            takePhoto();
        }else{
            //打开裁剪工具 裁剪
            Uri mImageUri = getUri(path);
            //CropPhotoFactory.getCustomCropPhotoImpl().cropPhoto(this, uri, mDestinationUri,REQUEST_CODE);
            CropPhotoFactory.getSystemCropPhotoImpl().cropPhoto(this, mImageUri, mDestinationUri, REQUEST_CODE);
        }
    }
    /**
     * path转uri
     */
    private Uri getUri(String path) {
        Uri uri = null;
        if (path != null) {
            path = Uri.decode(path);
            ContentResolver cr = this.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID},
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
// set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
//do nothing
            } else {
                Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case  REQUEST_CODE:
                if(resultCode == RESULT_OK) {
//                    Intent intent = new Intent();
//                    intent.putExtra("img_uri",mDestinationUri);
//                    setResult(RESULT_OK,intent);
                    EventBus.getDefault().post(new ImgEvent(mDestinationUri.getPath()));
                    this.finish();
                }
                break;
            case CAMERA_REQUEST_CODE:
                CropPhotoFactory.getSystemCropPhotoImpl().cropPhoto(this, mCameraPhotoUri, mDestinationUri, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 调用相机
     */
    private void takePhoto() {
        mCameraPhoto = new File(getExternalFilesDir("Photo"), "temp_camera_head.jpg");
//        mImageFile = new File(cordova.getActivity().getExternalFilesDir("Image"), "camera_head.jpg");
        // mImageFile = new File(cordova.getActivity().getExternalFilesDir("Image"), "camera_head.jpg");
        mCameraPhotoUri = Uri.fromFile(mCameraPhoto);
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,mCameraPhotoUri);
        startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
    }
}
