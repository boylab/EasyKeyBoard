package com.boylab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;

import com.boylab.easykeyboard.R;

import java.util.List;

public class CarKeyboardView extends KeyboardView {

    public CarKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 后续完善 2021-9-4 18:13:17
         *
         * https://blog.csdn.net/qq_29983773/article/details/79501658
         */
        List<Keyboard.Key> keyList = getKeyboard().getKeys();
        for (Keyboard.Key key : keyList) {
            /*if (key.codes[0] == Keyboard.KEYCODE_CANCEL){

            }else if (key.codes[0] == Keyboard.KEYCODE_DELETE){

            }else if (key.codes[0] == Keyboard.KEYCODE_DONE){

            }else if (key.codes[0] == Keyboard.KEYCODE_DONE){

            }*/
            Log.i("___boylab>>>___", "onDraw: = "+key.codes[0]);

            if (key.codes[0] == Keyboard.KEYCODE_DELETE){
                key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                if (key.pressed){
                    key.icon.setAlpha(155);
                }else {
                    key.icon.setAlpha(255);
                }
                key.icon.draw(canvas);
            }else if (key.codes[0] == '*' || key.codes[0] == ' '
                    || (key.codes[0] >= '0' && key.codes[0] <= '9') ) {
                if (key.label == null){
                    return;
                }
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.key_number_selector);
                drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.hashCode());
                drawable.draw(canvas);

                if (key.pressed){
                    drawable.setAlpha(155);
                }else {
                    drawable.setAlpha(255);
                }
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                /*Typeface font = Typeface.create(key.label.toString(), Typeface.BOLD);
                paint.setTypeface(font);*/
                paint.setTextSize(35);

                //canvas.drawRect(new Rect(0, 0, 320, 240), mPaint);
                canvas.drawText(key.label.toString(), key.x, key.y, paint);
            }else {
                if (key.label == null){
                    return;
                }
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.key_bg_selector);
                drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.hashCode());
                drawable.draw(canvas);

                if (key.pressed){
                    drawable.setAlpha(155);
                }else {
                    drawable.setAlpha(255);
                }
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                /*Typeface font = Typeface.create(key.label.toString(), Typeface.BOLD);
                paint.setTypeface(font);*/
                paint.setTextSize(35);

                //canvas.drawRect(new Rect(0, 0, 320, 240), mPaint);
                canvas.drawText(key.label.toString(), key.x, key.y, paint);
            }
        }
    }
}
