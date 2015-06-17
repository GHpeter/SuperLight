package com.peter.superlight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 回忆 on 2015/2/16 0016.
 */
public class ColorPickerDialog extends Dialog {
    private final int COLOR_DIALOG_WIDTH = 300;
    private final int COLOR_DIALOG_HEIGHT = 300;
    private final int CENTER_X = COLOR_DIALOG_WIDTH / 2;
    private final int CENTER_Y = COLOR_DIALOG_HEIGHT / 2;
    private final int CENTER_RADIUS = 32;

    public interface OnColorChangedListener {
        void colorChanged(int color);
    }

    private OnColorChangedListener myListener;
    private int myInitialColor;

    public ColorPickerDialog(Context context, OnColorChangedListener listener, int initialColor) {
        super(context);
        myListener = listener;
        myInitialColor = initialColor;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnColorChangedListener listener = new OnColorChangedListener() {
            @Override
            public void colorChanged(int color) {
                myListener.colorChanged(color);
                dismiss();
            }
        };
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new ColorPicker(getContext(), listener, myInitialColor));
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.BLACK);
        getWindow().setBackgroundDrawable(colorDrawable);
        getWindow().setAttributes(new WindowManager.LayoutParams(
                COLOR_DIALOG_WIDTH, COLOR_DIALOG_HEIGHT, 0, 0, 0));

    }

    private class ColorPicker extends View {
        private Paint myPaint, myCenterPaint;
        private final int[] myColors;
        private OnColorChangedListener myListener;
        //点中心的小圆上，中心高亮
        private boolean myTrackingCenter, myHightlightCenter;
        private static final float PI = 3.1415926f;

        public ColorPicker(Context context, OnColorChangedListener listener, int color) {
            super(context);
            myListener = listener;

            myColors = new int[]{0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,
                    0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000};
            Shader shader = new SweepGradient(0, 0, myColors, null);
            myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            myPaint.setShader(shader);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(32);

            myCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            myCenterPaint.setColor(color);
            myCenterPaint.setStrokeWidth(5);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //计算外圈的半径
            float r = CENTER_X - myPaint.getStrokeWidth() * 0.5f - 20;

            canvas.translate(CENTER_X, CENTER_Y);
            canvas.drawCircle(0, 0, r, myPaint);
            canvas.drawCircle(0, 0, CENTER_RADIUS, myCenterPaint);
            if (myTrackingCenter) {
                int c = myCenterPaint.getColor();
                myCenterPaint.setStyle(Paint.Style.STROKE);
                if (myHightlightCenter) {
                    myCenterPaint.setAlpha(0xFF);
                } else {
                    myCenterPaint.setAlpha(0x00);
                }
                canvas.drawCircle(0, 0, CENTER_RADIUS + myCenterPaint.getStrokeWidth(), myCenterPaint);
                myCenterPaint.setStyle(Paint.Style.FILL);
                myCenterPaint.setColor(c);
            }
        }

        /**
         *
         */
        private int ave(int s, int d, float p) {
            return s + Math.round(p * (d - s));
        }

        private int interpColor(int[] colors, float unit) {
            if (unit <= 0) {
                return colors[0];
            }
            if (unit >= 1) {
                return colors[colors.length - 1];
            }
            float p = unit * (colors.length - 1);
            int i = (int) p;
            p -= i;

            int c0 = colors[i];
            int c1 = colors[i + 1];
            int alpha = ave(Color.alpha(c0), Color.alpha(c1), p);
            int red = ave(Color.red(c0), Color.red(c1), p);
            int green = ave(Color.green(c0), Color.green(c1), p);
            int blue = ave(Color.blue(c0), Color.green(c1), p);
            return Color.argb(alpha, red, green, blue);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX() - CENTER_X;
            float y = event.getY() - CENTER_Y;
            boolean inCenter = Math.sqrt(x * x + y * y) <= CENTER_RADIUS;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    myTrackingCenter = inCenter;
                    if (inCenter) {
                        myHightlightCenter = true;
                        invalidate();
                        break;
                    }
                case MotionEvent.ACTION_MOVE:
                    //弧度 反正切函数（-π ~π）
                    float angle = (float) Math.atan2(x, y);
                    float unit = angle / (2 * PI);
                    if (unit < 0) {
                        unit += 1;
                    }
                    myCenterPaint.setColor(interpColor(myColors, unit));
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (myTrackingCenter) {
                        if (inCenter) {
                            myListener.colorChanged(myCenterPaint.getColor());
                        }
                        myTrackingCenter = false;
                        invalidate();
                    }

                    break;
                default:
                    break;

            }


            return true;
        }
    }
}
