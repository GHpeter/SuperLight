package com.peter.superlight;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;


/**
 * Created by 回忆 on 2015/2/14 0014.
 */
public class BaseActivity extends Activity {
    //枚举类型定义所有的状态
    enum UIType {
        UI_TYPE_MAIN,//主界面
        UI_TYPE_FLASHLIGHT,//手电筒
        UI_TYPE_WARNINGLIGHT,//警告灯
        UI_TYPE_MORSE,//莫尔斯电码
        UI_TYPE_BLUB,//灯泡
        UI_TYPE_COLOR,//带颜色的灯
        UI_TYPE_POLICE,//警灯
        UI_TYPE_SETTINGS//设置
    }

    //存储当前频率的变量
    int myCurrentWarningLightInterval = 500;
    int myCurrentPoliceLightInterval = 100;

    ImageView myImageViewFlashlight, myImageViewFlashlight_controller,
            myImageviewWarninngLight1, myImageviewWarninngLight2,
            MyImageViewBulb;
    android.hardware.Camera myCamera;
    EditText myEditTextMorseCode;
    android.hardware.Camera.Parameters myParameters;
    FrameLayout myUiFlashlight, myUIBulb, myUIColorLight, myUiPoliceLight;

    LinearLayout myUIMain, myUIWarningLight, myUIMorse, MyUISettings;

    SeekBar mySeelbarWarningLight, mySeekBarPoliceLight;

    Button myButtonAddShortcut, myButtonRemoveShortcut;

    UIType myCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
    UIType myLastUIType = UIType.UI_TYPE_FLASHLIGHT;

    float myDefaultScreenBrightness;

    //计数器-记录单击的次数
    int myFinishCount = 0;

    SharedPreferences myShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initAllViews();
        myDefaultScreenBrightness = getScreenBrightness();
    }

    /**
     * 初始化所有的控件
     */
    public void initAllViews() {
        myUIMain = (LinearLayout) findViewById(R.id.linearlayout_main);
        myUiFlashlight = (FrameLayout) findViewById(R.id.framelayout_flashlight);
        myImageViewFlashlight = (ImageView) findViewById(R.id.imageview_flashlight);
        myImageViewFlashlight_controller = (ImageView) findViewById(R.id.imageview_flashlight_controller);
        myImageviewWarninngLight1 = (ImageView) findViewById(R.id.imageview_warning_light1);
        myImageviewWarninngLight2 = (ImageView) findViewById(R.id.imageview_warning_light2);
        myUIWarningLight = (LinearLayout) findViewById(R.id.linearlayout_warning_light);
        myUIMorse = (LinearLayout) findViewById(R.id.linearlayout_morse);
        myEditTextMorseCode = (EditText) findViewById(R.id.et_morse_code);
        MyImageViewBulb = (ImageView) findViewById(R.id.imageview_bulb);
        myUIBulb = (FrameLayout) findViewById(R.id.framelayout_bulb);
        myUIColorLight = (FrameLayout) findViewById(R.id.framelayout_color_light);
        myUiPoliceLight = (FrameLayout) findViewById(R.id.framelayout_police_light);

        MyUISettings = (LinearLayout) findViewById(R.id.linearlayout_settings);
        mySeelbarWarningLight = (SeekBar) findViewById(R.id.seekbar_warning_light);
        mySeekBarPoliceLight = (SeekBar) findViewById(R.id.seekbar_police_light);
        myButtonAddShortcut = (Button) findViewById(R.id.button_add_shortcut);
        myButtonRemoveShortcut = (Button) findViewById(R.id.button_remove_shortcut);
        mySeekBarPoliceLight.setProgress(myCurrentPoliceLightInterval - 50);
        mySeelbarWarningLight.setProgress(myCurrentWarningLightInterval - 100);
        myShare = getSharedPreferences("config", Context.MODE_PRIVATE);


        myCurrentWarningLightInterval = myShare.getInt("warning_light_interval", 200);
        myCurrentPoliceLightInterval = myShare.getInt("police_light_interval", 100);
    }


    /**
     * 隐藏所有的ui
     */
    public void hideAllUI() {
        myUiFlashlight.setVisibility(View.GONE);
        myUIMain.setVisibility(View.GONE);
        myUIWarningLight.setVisibility(View.GONE);
        myUIMorse.setVisibility(View.GONE);
        myUIBulb.setVisibility(View.GONE);
        myUIColorLight.setVisibility(View.GONE);
        myUiPoliceLight.setVisibility(View.GONE);
        MyUISettings.setVisibility(View.GONE);
    }

    /**
     * 改变屏幕的亮度
     */
    public void screenBrightness(float value) {
        try {
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness = value;
            getWindow().setAttributes(layout);

        } catch (Exception e) {

        }
    }

    /**
     * 获得当前屏幕的亮度
     */
    public int getScreenBrightness() {
        int value = 0;
        try {
            value = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {

        }
        return value;
    }

    @Override
    public void finish() {
        myFinishCount++;
        if (myFinishCount == 1) {
            Toast.makeText(this, "再按一次退出程序！", Toast.LENGTH_SHORT).show();
        } else if (myFinishCount == 2) {
            super.finish();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        myFinishCount = 0;
        return super.dispatchTouchEvent(ev);
    }
}
