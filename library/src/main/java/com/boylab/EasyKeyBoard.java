package com.boylab;

import android.app.Activity;

import com.boylab.keyboard.DecimalKeyboard;
import com.boylab.keyboard.IDKeyboard;
import com.boylab.keyboard.NumberKeyboard;
import com.boylab.keyboard.OnKeyActionListener;
import com.boylab.keyboard.VehiclePlateKeyboard;

/**
 * Created on 2020/07/16 16:06
 */
public class EasyKeyBoard {

    public EasyKeyBoard() {

    }

    public static void bindIdentity(Activity context, OnKeyActionListener onKeyActionListener){
        IDKeyboard.show(context, onKeyActionListener);
    }

    @Deprecated
    private static void bindIP(){
        //IPKeyboard.show(context,onKeyActionListener);
    }

    public static void bindNumber(Activity context, int maxLen, OnKeyActionListener onKeyActionListener){
        NumberKeyboard.show(context, maxLen, onKeyActionListener);
    }
    public static void bindDecimal(Activity context, int maxIntLen, int pointLen, OnKeyActionListener onKeyActionListener){
        DecimalKeyboard.show(context, maxIntLen, pointLen, onKeyActionListener);
    }

    public static void bindCarPlate(Activity context, String defaultPlate, OnKeyActionListener onKeyActionListener){
        VehiclePlateKeyboard.show(context, defaultPlate, onKeyActionListener);
    }

}
