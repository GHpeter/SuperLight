package com.peter.superlight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.peter.superlight.widget.HideTextView;

/**
 * Created by 回忆 on 2015/2/16 0016.
 */
public class ColorLightActivity extends BulbActivity implements ColorPickerDialog.OnColorChangedListener {
    int myCurrentColorLight = Color.RED;//当前的颜色
    HideTextView myHidetextViewColorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHidetextViewColorLight = (HideTextView) findViewById(R.id.textview_hide_color_light);
    }

    public void onClick_ShowColorPicker(View view) {
        new ColorPickerDialog(this, this, Color.RED).show();
    }

    @Override
    public void colorChanged(int color) {
        myUIColorLight.setBackgroundColor(color);
        myCurrentColorLight = color;
    }
}
