package com.boylab.easykeyboard.number;

import android.app.Activity;

import com.boylab.easykeyboard.R;

/**
 * 自定义键盘
 * Created by kuangch on 17/3/10.
 */
public class KeyboardNumber extends BaseKeyboard{

    public KeyboardNumber(Activity activity) {
        this(activity, false, false);
    }

    public KeyboardNumber(Activity activity, boolean ifRandom, boolean isWidthDecimal) {

        int layoutResId;
        if(isWidthDecimal)
            layoutResId = R.xml.keyboard_number_with_decimal;
        else
            layoutResId = R.xml.keyboard_number;

        init(activity,layoutResId,ifRandom);
    }
}