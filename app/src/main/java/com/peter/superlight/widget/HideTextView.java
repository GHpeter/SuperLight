package com.peter.superlight.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 回忆 on 2015/2/16 0016.
 */
public class HideTextView extends TextView {

    public HideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                setVisibility(GONE);
            } else if (msg.what == 1) {
                setVisibility(VISIBLE);
            }
        }
    };

    class TextViewThread extends Thread {
        @Override
        public void run() {
            super.run();
            myHandler.sendEmptyMessage(1);
            try {
                sleep(300);
                myHandler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void hide() {
        new TextViewThread().start();
    }

}
