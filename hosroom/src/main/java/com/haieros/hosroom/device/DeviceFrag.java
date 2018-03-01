package com.haieros.hosroom.device;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseBottomFragment;
import com.haieros.hosroom.bean.DeviceBean;
import com.haieros.hosroom.widget.MenuPopupWindow;
import com.haieros.hosroom.widget.picture.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.zbar.CaptureScanActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Kang on 2017/8/16.
 */

public class DeviceFrag extends BaseBottomFragment implements View.OnClickListener {

    private static final String TAG = DeviceFrag.class.getSimpleName();
    private static final int REQUEST_CODE_FROM_DEVICE = 0x01;

    @BindView(R.id.kang_device_frag_recyclerview)
    RecyclerView kang_device_frag_recyclerview;

    private List<DeviceBean> mDeviceList;
    private DeviceAdapter mAdapter;
    private MenuPopupWindow mPopupWindow;

    @Override
    public View initView() {
        Log.e(TAG, "DeviceFrag->initView");
        View view = View.inflate(fContext, R.layout.device_frag, null);
        return view;
    }

    @Override
    public void initData() {
        kang_title_title.setText("设备");
        kang_title_menu.setImageResource(R.drawable.icon_add_24dp);
        mDeviceList = new ArrayList<>();
        mDeviceList.add(new DeviceBean("灯-1"));
        mDeviceList.add(new DeviceBean("灯-2"));
        mDeviceList.add(new DeviceBean("灯-3"));
        mDeviceList.add(new DeviceBean("灯-4"));
        mDeviceList.add(new DeviceBean("灯-5"));
        //设置线性
        LinearLayoutManager layoutManager = new LinearLayoutManager(fContext);
        kang_device_frag_recyclerview.setLayoutManager(layoutManager);

        //设置间距
        float space = fContext.getResources().getDimension(R.dimen.space);
        kang_device_frag_recyclerview.addItemDecoration(new SpaceItemDecoration(space));

        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        kang_device_frag_recyclerview.setHasFixedSize(true);

        //初始化adapter
        initAdapter();
        //设置adapter
        kang_device_frag_recyclerview.setAdapter(mAdapter);
    }

    private void initAdapter() {
        mAdapter = new DeviceAdapter(fContext, mDeviceList);
        mAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                Toast.makeText(fContext, "position:"+positon, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onClickOther(View view) {

    }

    @Override
    public void titleMenuClick(View view) {
        Intent intent = new Intent(fContext, CaptureScanActivity.class);
        startActivityForResult(intent,REQUEST_CODE_FROM_DEVICE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_FROM_DEVICE :

                if(resultCode == RESULT_OK) {
                    String scan_result = data.getStringExtra("SCAN_RESULT");
                    handleClickItem(scan_result);
                }
                break;
        }
    }

    /**
     * 处理点击事件
     * @param result
     */
    private void handleClickItem(final String result) {

        View inflate = View.inflate(fContext, R.layout.device_toast, null);
        TextView kang_wifi_toast_ssid = (TextView) inflate.findViewById(R.id.kang_device_toast_id);
        final EditText kang_wifi_toast_pwd = (EditText) inflate.findViewById(R.id.kang_device_toast_key);
        TextView kang_wifi_toast_cancel = (TextView) inflate.findViewById(R.id.kang_device_toast_cancel);
        TextView kang_wifi_toast_confirm = (TextView) inflate.findViewById(R.id.kang_device_toast_confirm);
        kang_wifi_toast_ssid.setText(result);

        final AlertDialog dialog = new AlertDialog.Builder(fContext)
                .setView(inflate)
                .show();

        //设置大小
        Window alertDialogWindow = dialog.getWindow();
        WindowManager.LayoutParams params = alertDialogWindow.getAttributes();
        params.height = dp2px(fContext,220);
        //params.height = dp2px(cordova.getActivity(),124);
        alertDialogWindow.setAttributes(params);
        //设置点击外部不取消
        dialog.setCanceledOnTouchOutside(false);

        //确认
        kang_wifi_toast_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Serial_ID = result;
                String key = kang_wifi_toast_pwd.getText().toString().trim();
                if ("".equals(key) || key == null) {
                    Toast.makeText(fContext, "key no value", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(fContext, "ssid:" + Serial_ID + ",key:" + key, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        //取消
        kang_wifi_toast_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    /**
     * dp转px
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
