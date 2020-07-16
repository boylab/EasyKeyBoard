package com.boylab.keyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author YOOJIA.CHEN (yoojia.chen@gmail.com)
 */
class AbstractKeyboard {

    protected final Context mContext;
    private final PopupWindow mPopupWindow;
    protected final OnKeyActionListener mOnKeyActionListener;

    public AbstractKeyboard(Context context, OnKeyActionListener onKeyActionListener) {
        mContext = context;
        if (onKeyActionListener == null) {
            throw new NullPointerException("onKeyActionListener == null");
        }
        mOnKeyActionListener = onKeyActionListener;
        mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
    }

    protected View putContentView(int layoutResId, int width, int height) {
        mPopupWindow.setWidth(width);
        mPopupWindow.setHeight(height);

        final View view = LayoutInflater.from(mContext).inflate(layoutResId,null);
        mPopupWindow.setContentView(view);
        return view;
    }

    protected View putContentView(int layoutResId) {
        final View view = LayoutInflater.from(mContext).inflate(layoutResId,null);
        mPopupWindow.setContentView(view);
        return view;
    }

    public void show(final View anchorView) {
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.showAtLocation(anchorView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        onShow();
    }

    protected void onShow(){ }
    protected void onFilter(){ }

    public void dismiss(){
        mPopupWindow.dismiss();
    }

    public void beep(Context context){
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            VibrationEffect vibe = VibrationEffect.createWaveform(new long[]{0,60},-1);
            vibrator.vibrate(vibe);
        }
    }

    protected String getInput(TextView[] inputs) {
        final StringBuilder buff = new StringBuilder(inputs.length);
        for (TextView item : inputs){
            String text = String.valueOf(item.getText());
            if (! TextUtils.isEmpty(text)){
                buff.append(text);
            }
        }
        return buff.toString();
    }


}
