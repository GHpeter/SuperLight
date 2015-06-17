package com.peter.superlight;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by 回忆 on 2015/2/21 0021.
 */
public class SettingActivity extends PoliceLightActivity implements SeekBar.OnSeekBarChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySeelbarWarningLight.setOnSeekBarChangeListener(this);
        mySeekBarPoliceLight.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekbar_warning_light:
                myCurrentWarningLightInterval = progress + 100;
                break;
            case R.id.seekbar_police_light:
                myCurrentPoliceLightInterval = progress + 50;
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * 判断一个快捷方式是否已经存在桌面上
     */
    private boolean shortcutInScreen() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.cyanogenmod.trebuchet.settings/favorites"),
                null,
                "intent like ?",
                new String[]{"%component=com.peter.superlight/.MainActivity%"},
                null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;

        }
    }

    /**
     * 添加快捷方式
     *
     * @param view
     */


    public void onClick_AddShortcut(View view) {
        if (!shortcutInScreen()) {
            Intent installShortcut = new Intent(
                    "com.android.launcher.action.INSTALL_SHORTCUT");
            installShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");
            Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);

            Intent flashlightIntent = new Intent();
            flashlightIntent.setClassName("com.peter.superlight", "com.peter.superlight.MainActivity");
            flashlightIntent.setAction(Intent.ACTION_MAIN);
            flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);
            sendBroadcast(installShortcut);
            Toast.makeText(this, "已成功添加快捷方式到桌面！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "快捷方式已存在！", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 移除快捷方式
     *
     * @param view
     */
    public void onClick_RemoveShortcut(View view) {
        if (shortcutInScreen()) {
            Intent uninstallShortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");
            Intent flashlightIntent = new Intent();
            flashlightIntent.setClassName("com.peter.superlight", "com.peter.superlight.MainActivity");
            uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);
            uninstallShortcut.setAction(Intent.ACTION_MAIN);
            uninstallShortcut.addCategory(Intent.CATEGORY_LAUNCHER);
            sendBroadcast(uninstallShortcut);
        } else {
            Toast.makeText(this, "没有添加快捷方式，无法删除！", Toast.LENGTH_SHORT).show();

        }
    }
}
