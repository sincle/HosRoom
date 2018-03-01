package cn.bingoogolapple.qrcode.zbar;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;

public class CaptureScanActivity extends Activity implements QRCodeView.Delegate {
    private static final String TAG = CaptureScanActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private static final int REQUEST_CODE = 1;

    private QRCodeView mQRCodeView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);
        ImageButton mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CaptureScanActivity.this.finish();

            }
        });
        ImageButton mButtonFlash = (ImageButton) findViewById(R.id.btn_flashlight);
        mButtonFlash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                handleLight();
            }
        });
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
    }

    /**
     * 闪光灯 开关控制
     */
    private void handleLight() {
        if(Camera.Parameters.FLASH_MODE_OFF.equals(mQRCodeView.getmCamera().getParameters().getFlashMode())) {
            mQRCodeView.openFlashlight();
        }else {
            mQRCodeView.closeFlashlight();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startSpotDelay(100);
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        vibrate();
        //mQRCodeView.startSpot();
        if (result.equals("")) {
            Toast.makeText(CaptureScanActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(getIntent().getAction());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra("SCAN_RESULT", result.toString());
            setResult(RESULT_OK, intent);
        }
        CaptureScanActivity.this.finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }
}