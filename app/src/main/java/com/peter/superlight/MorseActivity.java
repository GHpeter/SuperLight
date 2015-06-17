package com.peter.superlight;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 回忆 on 2015/2/16 0016.
 */
public class MorseActivity extends WarningLightActivity {
    private final int DOT_TIME = 200;//点停留的时间 单位：毫秒
    private final int LINE_TIME = DOT_TIME * 3;//线停留的时间
    private final int DOT_LINE_TIME = DOT_TIME;//点到线的时间间隔

    private final int CHAR_CHAR_TIME = DOT_TIME * 3;//字符与字符之间的时间间隔
    private final int WORD_WORD_TIME = DOT_TIME * 7;//单词与单词之间的时间间隔

    private String MyMorseCode;//存储输入的莫尔斯电码
    /**
     * 要查询的电码表存入到程序中
     */
    private Map<Character, String> myMorseCodeMap = new HashMap<Character, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myMorseCodeMap.put('a', ".-");
        myMorseCodeMap.put('b', "-...");
        myMorseCodeMap.put('c', "-.-.");
        myMorseCodeMap.put('d', "-..");
        myMorseCodeMap.put('e', ".");
        myMorseCodeMap.put('f', "..-.");
        myMorseCodeMap.put('g', "--.");
        myMorseCodeMap.put('h', "....");
        myMorseCodeMap.put('i', "..");
        myMorseCodeMap.put('j', ".---");
        myMorseCodeMap.put('k', "-.-");
        myMorseCodeMap.put('l', ".-..");
        myMorseCodeMap.put('m', "--");
        myMorseCodeMap.put('n', "-.");
        myMorseCodeMap.put('o', "---");
        myMorseCodeMap.put('p', ".--.");
        myMorseCodeMap.put('q', "--.-");
        myMorseCodeMap.put('r', ".-.");
        myMorseCodeMap.put('s', "...");
        myMorseCodeMap.put('t', "-");
        myMorseCodeMap.put('u', "..-");
        myMorseCodeMap.put('v', "...-");
        myMorseCodeMap.put('w', ".--");
        myMorseCodeMap.put('x', "-..-");
        myMorseCodeMap.put('y', "-.--");
        myMorseCodeMap.put('z', "--..");

        myMorseCodeMap.put('0', "-----");
        myMorseCodeMap.put('1', ".----");
        myMorseCodeMap.put('2', "..---");
        myMorseCodeMap.put('3', "...--");
        myMorseCodeMap.put('4', "....-");
        myMorseCodeMap.put('5', ".....");
        myMorseCodeMap.put('6', "-....");
        myMorseCodeMap.put('7', "--...");
        myMorseCodeMap.put('8', "---..");
        myMorseCodeMap.put('9', "----.");
    }

     void sleepExt(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {

        }
    }

    public void onClick_SendMorseCode(View view) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {

            Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_SHORT).show();
            return;
        }
        if (verifyMorseCode()) {
            sendSentense(MyMorseCode);
        }
    }

    /**
     * 发送点
     */
    private void sendDot() {
        openFlashlight();
        sleepExt(DOT_TIME);
        closeFlashlight();
    }

    /**
     * 发送线
     */
    private void sendLine() {
        openFlashlight();
        sleepExt(LINE_TIME);
        closeFlashlight();
    }

    /**
     * 发送字符
     */
    private void sendChar(char c) {
        String morseCode = myMorseCodeMap.get(c);
        char last;
        if (morseCode != null) {
            char lastChar = ' ';
            for (int i = 0; i < morseCode.length(); i++) {
                char dotLine = morseCode.charAt(i);
                if (dotLine == '.') {
                    sendDot();
                } else if (dotLine == '_') {
                    sendLine();
                }
                if (i > 0 && i < morseCode.length() - 1) {
                    if (lastChar == '.' && dotLine == '_') {
                        sleepExt(DOT_LINE_TIME);
                    }
                }
                lastChar = dotLine;
            }
        }
    }

    /**
     * 发送字符-单词
     */
    private void sendWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sendChar(c);
            if (i < s.length() - 1) {
                sleepExt(CHAR_CHAR_TIME);
            }
        }
    }

    /**
     * 发送句子
     */
    private void sendSentense(String s) {
        String[] words = s.split(" +");
        for (int i = 0; i < words.length; i++) {
            sendWord(words[i]);
            if (i < s.length() - 1) {
                sleepExt(WORD_WORD_TIME);
            }
        }
        Toast.makeText(this, "莫尔斯电码已经发送完毕！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 验证输入的莫尔斯电码（必须是字母，数字，空格）
     */
    private boolean verifyMorseCode() {
        MyMorseCode = myEditTextMorseCode.getText().toString().toLowerCase();
        if ("".equals(MyMorseCode)) {
            Toast.makeText(this, "请输入摩尔斯电码字符串！", Toast.LENGTH_LONG).show();
            return false;
        }
        //验证字母、数字、空格
        for (int i = 0; i < MyMorseCode.length(); i++) {
            char c = MyMorseCode.charAt(i);
            if (!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && c != ' ') {
                Toast.makeText(this, "摩尔斯电码只能是字母、数字和空格！", Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }
        return true;
    }
}


