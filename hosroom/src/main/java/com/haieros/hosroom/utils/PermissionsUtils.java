package com.haieros.hosroom.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kang on 2016/9/28.
 */
public class PermissionsUtils {

    private static final String TAG = PermissionsUtils.class.getSimpleName();

    /**
     * 请求权限 首先检测 有权限 返回 , 没权限申请权限
     *
     * @param activity   activity实例
     * @param permission 权限数组 填写一个
     * @param requstCode 请求码
     */
    public static void requestActivityPermission(Activity activity, String permission, int requstCode, HavePermission havePermission) {

        int permissionResult = ActivityCompat.checkSelfPermission(activity, permission);

        //检测是否有权限 有权限 返回
        if (permissionResult == PackageManager.PERMISSION_GRANTED) {
            havePermission.callBack();
            return;
        }

        //否则申请所需权限
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requstCode);

    }

    /**
     * 回调结果
     *
     * @param activity
     * @param info
     */
    public static void onAlertDialog(final Activity activity, String info) {

        new AlertDialog.Builder(activity)
                .setTitle("需要权限:")
                .setMessage("必须需要:" + info + ",请到设置>应用>" + activity.getApplication().getApplicationInfo().loadLabel(activity.getPackageManager()) + ">权限中开启")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .show();
    }

    /**
     * 全部同意获取权限返回true  否则返回false
     * @param grantResults 结果
     * @return
     */
    public static boolean isAllGranted(int[] grantResults) {
        boolean flag = false;
        for (int grant : grantResults) {
            if(grant != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
            flag =true;
        }
        return flag;
    }

    public static void requestFragmentPermission(Fragment fragment,String permission,int REQUST_CODE,HavePermission havePermission) {

        int permissionResult = ContextCompat.checkSelfPermission(fragment.getActivity(), permission);

            //检测是否有权限 有权限 返回
            if (permissionResult == PackageManager.PERMISSION_GRANTED) {
                havePermission.callBack();
                return;
            }
            //否则申请所需权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                fragment.requestPermissions(new String[]{permission}, REQUST_CODE);
            }
    }

    /**
     * 获取到权限回调
     */
    public interface HavePermission {
        //有权限回调
        void callBack();
    }

    /**
     * 获取多个权限
     *
     * @param activity
     * @param requestCode
     */
    public static void requestMutilActivityPermission(final Activity activity, String[] permissions,final int requestCode,HavePermission havePermission) {

        final List<String> permissionsList = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            addPermission(activity, permissionsList,permissions[i]);
        }
        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    requestCode);
        }else{
            havePermission.callBack();
        }
    }

    /**
     * 添加权限
     *
     * @param activity
     * @param permissionsList
     * @param permission
     * @return
     */
    private static boolean addPermission(Activity activity, List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            return false;
        }
        return true;
    }
}
