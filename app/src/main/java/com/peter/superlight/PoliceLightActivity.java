package com.peter.superlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.peter.superlight.widget.HideTextView;

/**
 * Created by 回忆 on 2015/2/21 0021.
 */
public class PoliceLightActivity extends ColorLightActivity {
    boolean mPoliceState;
    HideTextView myHideTextViewPoliceLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHideTextViewPoliceLight = (HideTextView) findViewById(R.id.textview_hide_police_light);
    }

    class PoliceThread extends Thread {
        @Override
        public void run() {
            mPoliceState = true;
            while (mPoliceState) {

                myHandler.sendEmptyMessage(Color.BLUE);
                sleepExt(100);
                myHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(100);
                myHandler.sendEmptyMessage(Color.RED);
                sleepExt(100);
                myHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(100);

            }
        }
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int color = msg.what;
            myUiPoliceLight.setBackgroundColor(color);

        }
    };
}
