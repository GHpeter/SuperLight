package com.peter.superlight;


import android.graphics.Color;
import android.view.View;


public class MainActivity extends SettingActivity {
    public void onclick_Controller(View view) {
        hideAllUI();
        if (myCurrentUIType != UIType.UI_TYPE_MAIN) {
            myUIMain.setVisibility(View.VISIBLE);
            myCurrentUIType = UIType.UI_TYPE_MAIN;
            myWarningLightFlicker = false;
            screenBrightness(myDefaultScreenBrightness / 255f);
            if (myBulbCrossFadeFlag) {
                myDrawable.reverseTransition(0);
            }
            myBulbCrossFadeFlag = false;
            mPoliceState = false;
            myShare.edit().putInt("warning_light_interval", myCurrentWarningLightInterval)
                    .putInt("police_light_interval", myCurrentPoliceLightInterval).commit();
        } else {
            switch (myLastUIType) {
                //手电筒
                case UI_TYPE_FLASHLIGHT:
                    myUiFlashlight.setVisibility(View.VISIBLE);
                    screenBrightness(1f);
                    myCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;

                    break;
                //警告灯
                case UI_TYPE_WARNINGLIGHT:
                    screenBrightness(1f);
                    myUIWarningLight.setVisibility(View.VISIBLE);
                    myCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
                    new WarningLightThread().start();
                case UI_TYPE_MORSE:
                    myUIMorse.setVisibility(View.VISIBLE);
                    myCurrentUIType = UIType.UI_TYPE_MORSE;
                    break;
                case UI_TYPE_BLUB:
                    screenBrightness(1f);
                    myUIBulb.setVisibility(View.VISIBLE);
                    myCurrentUIType = UIType.UI_TYPE_BLUB;
                    break;
                case UI_TYPE_POLICE:
                    screenBrightness(1f);
                    myUiPoliceLight.setVisibility(View.VISIBLE);
                    myCurrentUIType = UIType.UI_TYPE_POLICE;
                    new PoliceThread().start();

                    break;
                case UI_TYPE_SETTINGS:
                    MyUISettings.setVisibility(View.VISIBLE);
                    myLastUIType = UIType.UI_TYPE_SETTINGS;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 进入手电筒
     *
     * @param view
     */
    public void onClick_ToFlashlight(View view) {
        hideAllUI();
        screenBrightness(1f);
        myUiFlashlight.setVisibility(View.VISIBLE);
        myLastUIType = UIType.UI_TYPE_FLASHLIGHT;
        myCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;

    }

    /**
     * 进入警告灯界面
     *
     * @param view
     */
    public void onClick_ToWarningLight(View view) {
        hideAllUI();

        myUIWarningLight.setVisibility(View.VISIBLE);
        myLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
        myCurrentUIType = myLastUIType;
        screenBrightness(1f);
        new WarningLightThread().start();

    }

    /**
     * 发送莫尔斯电码
     */
    public void onClick_ToMorse(View view) {
        hideAllUI();
        myUIMorse.setVisibility(View.VISIBLE);
        myLastUIType = UIType.UI_TYPE_MORSE;
        myCurrentUIType = myLastUIType;
    }

    /**
     * 电灯泡
     */
    public void onClick_ToBulb(View view) {
        hideAllUI();
        myUIBulb.setVisibility(View.VISIBLE);
        screenBrightness(1f);
        myHideTextViewBulb.hide();
        myHideTextViewBulb.setTextColor(Color.BLACK);

        myLastUIType = UIType.UI_TYPE_BLUB;
        myCurrentUIType = myLastUIType;
    }

    /**
     * 彩色灯
     */
    public void onClick_ToColor(View view) {
        hideAllUI();
        myUIColorLight.setVisibility(View.VISIBLE);
        screenBrightness(1f);
        myLastUIType = UIType.UI_TYPE_COLOR;
        myCurrentUIType = myLastUIType;
        myHidetextViewColorLight.setTextColor(Color.rgb(
                255 - Color.red(myCurrentColorLight),
                255 - Color.green(myCurrentColorLight),
                255 - Color.blue(myCurrentColorLight)));
    }

    /**
     * 警灯
     */
    public void onClick_ToPolice(View view) {
        hideAllUI();
        myUiPoliceLight.setVisibility(View.VISIBLE);
        screenBrightness(1f);
        myLastUIType = UIType.UI_TYPE_POLICE;
        myCurrentUIType = myLastUIType;

        myHideTextViewPoliceLight.hide();
        new PoliceThread().start();
    }

    /**
     * 设置
     */
    public void onClick_ToSettings(View view) {
        hideAllUI();
        MyUISettings.setVisibility(View.VISIBLE);
        myLastUIType = UIType.UI_TYPE_SETTINGS;
        myCurrentUIType = myLastUIType;

    }

}
