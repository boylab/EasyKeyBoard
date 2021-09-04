package com.boylab;

import android.app.Activity;

import com.boylab.keyboard.DecimalKeyboard;
import com.boylab.keyboard.IDKeyboard;
import com.boylab.keyboard.IPKeyboard;
import com.boylab.keyboard.NumberKeyboard;
import com.boylab.callback.OnKeyboardListener;
import com.boylab.keyboard.CarKeyboard;
import com.boylab.keyboard.PassKeyboard;

/**
 * Created on 2020/07/16 16:06
 */
public class EasyKeyBoard {

    private static EasyKeyBoard singleton;

    public EasyKeyBoard() {
    }

    public static EasyKeyBoard singleton() {
        if (singleton == null) {
            singleton = new EasyKeyBoard();
        }
        return singleton;
    }

    /**
     * 纯数字键盘
     * @param context
     * @param maxLen
     * @param onKeyboardListener
     */
    public void bindNumber(Activity context, int maxLen, OnKeyboardListener onKeyboardListener){
        NumberKeyboard.show(context, maxLen, onKeyboardListener);
    }

    /**
     * 浮点键盘
     * @param context
     * @param maxIntLen
     * @param pointLen
     * @param onKeyboardListener
     */
    public void bindDecimal(Activity context, int maxIntLen, int pointLen, OnKeyboardListener onKeyboardListener){
        DecimalKeyboard.show(context, maxIntLen, pointLen, onKeyboardListener);
    }

    /**
     * 车牌键盘
     * @param context
     * @param defaultPlate
     * @param onKeyboardListener
     */
    public void bindCarNumber(Activity context, String defaultPlate, OnKeyboardListener onKeyboardListener){
        CarKeyboard.show(context, defaultPlate, onKeyboardListener);
    }

    /**
     * 身份证键盘
     * @param context
     * @param onKeyboardListener
     */
    public void bindID(Activity context, OnKeyboardListener onKeyboardListener){
        IDKeyboard.show(context, onKeyboardListener);
    }

    /**
     * IP键盘
     */
    public void bindIP(Activity context, OnKeyboardListener onKeyboardListener){
        IPKeyboard.show(context,onKeyboardListener);
    }

    /**
     * 密码键盘
     */
    public void bindPassword(Activity context, OnKeyboardListener onKeyboardListener){
        PassKeyboard.show(context,onKeyboardListener);
    }

}
