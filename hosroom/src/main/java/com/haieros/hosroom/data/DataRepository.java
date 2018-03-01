package com.haieros.hosroom.data;

import android.content.Context;

import com.haieros.hosroom.data.net.IRequestManager;
import com.haieros.hosroom.data.net.RequestFactory;

/**
 * Created by Kang on 2017/8/21.
 */

public class DataRepository {

    protected IRequestManager mRequestManager;

    protected DataRepository(Context context) {
        mRequestManager = RequestFactory.getRequestManager();
        initDb(context);
    }

    public void initDb(Context context){

    }
}
