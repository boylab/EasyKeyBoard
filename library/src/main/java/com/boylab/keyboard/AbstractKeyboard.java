package com.boylab.keyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boylab.callback.OnKeyboardListener;

/**
 * @author YOOJIA.CHEN (yoojia.chen@gmail.com)
 */
class AbstractKeyboard implements KeyboardView.OnKeyboardActionListener{

    protected final Context mContext;
    private final PopupWindow mPopupWindow;
    protected final OnKeyboardListener mOnKeyboardListener;

    public AbstractKeyboard(Context context, OnKeyboardListener onKeyboardListener) {
        mContext = context;
        if (onKeyboardListener == null) {
            throw new NullPointerException("onKeyActionListener == null");
        }
        mOnKeyboardListener = onKeyboardListener;
        mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
    }

    protected View setContentView(int layoutResId, int width, int height) {
        mPopupWindow.setWidth(width);
        mPopupWindow.setHeight(height);

        final View view = LayoutInflater.from(mContext).inflate(layoutResId, null);
        mPopupWindow.setContentView(view);
        return view;
    }

    protected View setContentView(int layoutResId) {
        final View view = LayoutInflater.from(mContext).inflate(layoutResId, null);
        mPopupWindow.setContentView(view);
        return view;
    }

    public void show(final View anchorView) {
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.showAtLocation(anchorView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        onShow();
    }

    protected void onShow() {
    }

    protected void onFilter() {
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    protected String getInput(TextView[] inputs) {
        final StringBuilder buff = new StringBuilder(inputs.length);
        for (TextView item : inputs) {
            String text = String.valueOf(item.getText());
            if (!TextUtils.isEmpty(text)) {
                buff.append(text);
            }
        }
        return buff.toString();
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
