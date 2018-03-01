package com.haieros.udpsend;

import java.util.ResourceBundle;

/**
 * Created by Kang on 2017/11/2.
 */

public class DictionaryHelper {

    private static DictionaryHelper instance;
    private String path = "com.haieros.dictionary";
    private String kang = "com.haieros.kang";
    private ResourceBundle mResource;
    private final ResourceBundle mKang;

    private DictionaryHelper() {
        mResource = ResourceBundle.getBundle(path);
        mKang = ResourceBundle.getBundle(kang);
    }

    public static DictionaryHelper newInstance() {

        if (instance == null) {
            synchronized (DictionaryHelper.class) {
                if (instance == null) {
                    instance = new DictionaryHelper();
                }
            }
        }
        return instance;
    }

    public String getString(String key) {
        return mResource.getString(mKang.getString(key));
    }
}
