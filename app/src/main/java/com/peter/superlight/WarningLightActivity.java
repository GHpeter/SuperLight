package com.peter.superlight;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by 回忆 on 2015/2/15 0015.
 */
public class WarningLightActivity extends FlashLightActivity {
    boolean myWarningLightFlicker;//true:闪烁 false:不闪烁
    boolean myWarningLightState;//ture:on-off false:off-on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myWarningLightFlicker = true;
    }

    class WarningLightThread extends Thread {
        @Override
        public void run() {
            myWarningLightFlicker = true;
            while (myWarningLightFlicker) {
                try {
                    Thread.sleep(300);
                    myWarningHandler.sendEmptyMessage(0);
                } catch (Exception e) {

                }
            }
        }
    }

    private Handler myWarningHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (myWarningLightState) {
                myImageviewWarninngLight1.setImageResource(R.drawable.warning_light_on);
                myImageviewWarninngLight2.setImageResource(R.drawable.warning_light_off);
                myWarningLightState = false;
            } else {
                myImageviewWarninngLight1.setImageResource(R.drawable.warning_light_off);
                myImageviewWarninngLight2.setImageResource(R.drawable.warning_light_on);
                myWarningLightState = true;
            }
        }
    };
}
