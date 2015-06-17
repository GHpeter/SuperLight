package com.peter.superlight;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;

import com.peter.superlight.widget.HideTextView;

/**
 * Created by 回忆 on 2015/2/16 0016.
 */
public class BulbActivity extends MorseActivity {
    boolean myBulbCrossFadeFlag;
    TransitionDrawable myDrawable;
    HideTextView myHideTextViewBulb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDrawable = (TransitionDrawable) MyImageViewBulb.getDrawable();
        myHideTextViewBulb = (HideTextView) findViewById(R.id.textview_hide_bulb);
    }

    public void onClick_BulbCrossFade(View view) {
        if (!myBulbCrossFadeFlag) {
            myDrawable.startTransition(500);
            myBulbCrossFadeFlag = true;
            screenBrightness(1f);
        } else {
            myDrawable.reverseTransition(500);
            myBulbCrossFadeFlag = false;
            screenBrightness(0f);

        }
    }
}
