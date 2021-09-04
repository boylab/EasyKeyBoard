package com.boylab.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.boylab.callback.OnKeyboardListener;
import com.boylab.easykeyboard.R;

/**
 * 浮点数软键盘
 */
public class DecimalKeyboard extends AbstractKeyboard implements View.OnClickListener {

    private KeyboardView mKeyboardView;
    private Keyboard mDecimalKeyboard;

    private TextView text_Input;
    private TextView mCurrentView;

    private int maxIntLen = 6, pointLen = 2;
    private boolean isAddPoint = false;

    public DecimalKeyboard(Context context,  int maxIntLen, int pointLen, OnKeyboardListener commitListener) {
        super(context, commitListener);
        this.maxIntLen = maxIntLen;
        this.pointLen = pointLen;
        initKeyBoard();
    }


    public DecimalKeyboard(Context context, OnKeyboardListener commitListener) {
        super(context, commitListener);
        initKeyBoard();
    }

    public void initKeyBoard(){
        final View rootView = setContentView(R.layout.keyboard_number);

        text_Input = rootView.findViewById(R.id.text_Input);
        text_Input.setSoundEffectsEnabled(false);
        text_Input.setOnClickListener(this);

        mDecimalKeyboard = new Keyboard(mContext, R.xml.keyboard_number_decimal);
        mKeyboardView = rootView.findViewById(R.id.keyboard_number);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.setPreviewEnabled(false);// !!! Must be false
        mKeyboardView.setKeyboard(mDecimalKeyboard);
    }

    @Override
    protected void onShow() {
        text_Input.performClick();
    }

    @Override
    public void onClick(View v) {
        if (mCurrentView != null){
            mCurrentView.setActivated(false);
        }
        mCurrentView = (TextView) v;
        mCurrentView.setActivated(true);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        super.onKey(primaryCode, keyCodes);
        if (primaryCode == Keyboard.KEYCODE_CANCEL){
            dismiss();
        }else if (primaryCode == Keyboard.KEYCODE_DELETE){
            String number = text_Input.getText().toString();
            if (TextUtils.isEmpty(number)){
                return;
            }else {
                text_Input.setText(number.substring(0, number.length() -1));
            }
            number = text_Input.getText().toString();
            isAddPoint = number.contains(".");

            if (mOnKeyboardListener != null){
                mOnKeyboardListener.onKeyUpdate(number);
            }
        }else if (primaryCode == Keyboard.KEYCODE_DONE){
            String number = text_Input.getText().toString();

            if (TextUtils.isEmpty(number)){
                dismiss();
                return;
            }
            if (mOnKeyboardListener != null){
                mOnKeyboardListener.onKeyFinish(number);
            }
            dismiss();
        }else if (primaryCode == '.'){
            if (isAddPoint){
                return;
            }else {
                String number = text_Input.getText().toString();
                if (TextUtils.isEmpty(number)){
                    return;
                }else {
                    text_Input.append(Character.toString((char) primaryCode));
                }
            }
            String number = text_Input.getText().toString();
            isAddPoint = number.contains(".");
            if (mOnKeyboardListener != null){
                mOnKeyboardListener.onKeyUpdate(number);
            }
        }else {
            String number = text_Input.getText().toString();
            if (isAddPoint){
                int indexPoint = number.lastIndexOf(".");
                if (number.length() - indexPoint - 1 >= pointLen){
                    return;
                }else {
                    text_Input.append(Character.toString((char) primaryCode));
                }
            }else {
                if (number.length() >= maxIntLen){
                    text_Input.setText(number.substring(number.length() - maxIntLen + 1));
                    text_Input.append(Character.toString((char) primaryCode));
                }else {
                    text_Input.append(Character.toString((char) primaryCode));
                }
            }

            number = text_Input.getText().toString();
            if (mOnKeyboardListener != null){
                mOnKeyboardListener.onKeyUpdate(number);
            }
        }
    }

    public static void show(Activity context, int maxIntLen, int pointLen, OnKeyboardListener listener) {
        View v= context.getWindow().getDecorView().getRootView();
        new DecimalKeyboard(context, maxIntLen, pointLen, listener).show(v);
    }

    public static DecimalKeyboard create(Context context, int maxIntLen, int pointLen, OnKeyboardListener listener) {
        return new DecimalKeyboard(context, maxIntLen, pointLen, listener);
    }

}
