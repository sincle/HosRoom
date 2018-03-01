package com.haieros.udpsend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TUdpSender tUdpSender = new TUdpSender(this);
        Button kang_button = (Button) findViewById(R.id.kang_button);
        kang_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String msg = "";
                        while (true){
                            for (int i = 0; i < 10; i++) {
                                msg = msg +i;
                                tUdpSender.send(msg);
                                Log.e(TAG, "---------:"+msg);
                            }
                            msg = "";
                        }
                    }
                }).start();
            }
        });
        DictionaryHelper.newInstance().getString("0");
    }

    /**
     * 方法一：将char 强制转换为byte
     * @param ch
     * @return
     */
    public static byte charToByteAscii(char ch){
        byte byteAscii = (byte)ch;

        return byteAscii;
    }
}
