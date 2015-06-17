package com.peter.superlight;


import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * 闪光灯
 * Created by 回忆 on 2015/2/14 0014.
 */
public class FlashLightActivity extends BaseActivity {
    LayoutParams layParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myImageViewFlashlight.setTag(false);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        layParams = myImageViewFlashlight_controller.getLayoutParams();
        layParams.height = point.y * 3 / 4;
        layParams.width = point.x / 3;
        myImageViewFlashlight_controller.setLayoutParams(layParams);

    }

    public void onClick_Flashlight(View view) {
/**
 * 判断当前Android设备的版本是否支持闪光灯
 */
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {

            Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_SHORT).show();
            return;
        }
        if (((Boolean) myImageViewFlashlight.getTag()) == false) {
            openFlashlight();
        } else {
            closeFlashlight();
        }
    }

    //打开闪光灯
    protected void openFlashlight() {
        TransitionDrawable drawable = (TransitionDrawable) myImageViewFlashlight.getDrawable();
        drawable.startTransition(200);
        myImageViewFlashlight.setTag(true);
        try {
            myCamera = Camera.open();
            int textureId = 0;
            myCamera.setPreviewTexture(new SurfaceTexture(textureId));
            myCamera.startPreview();

            myParameters = myCamera.getParameters();
            myParameters.setFlashMode(myParameters.FLASH_MODE_TORCH);
            myCamera.setParameters(myParameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭闪光灯
    protected void closeFlashlight() {
        TransitionDrawable drawable = (TransitionDrawable) myImageViewFlashlight.getDrawable();
        if (((Boolean) myImageViewFlashlight.getTag())) {
            drawable.reverseTransition(200);
            myImageViewFlashlight.setTag(false);
            if (myCamera != null) {
                myParameters = myCamera.getParameters();
                myParameters.setFlashMode(myParameters.FLASH_MODE_OFF);
                myCamera.setParameters(myParameters);
                myCamera.stopPreview();
                myCamera.release();
                myCamera = null;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeFlashlight();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeFlashlight();
    }
}
