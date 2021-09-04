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
 * 数字键盘
 */
public class NumberKeyboard extends AbstractKeyboard implements View.OnClickListener {

    private KeyboardView mKeyboardView;
    private Keyboard mNumberKeyboard;

    private TextView text_Input;
    private TextView mCurrentView;

    private int maxLen ;    //数字输入长度

    public NumberKeyboard(Context context, int maxLen, OnKeyboardListener commitListener) {
        super(context, commitListener);
        this.maxLen = maxLen;
        initKeyBoard();
    }

    private void initKeyBoard(){
        final View rootView = setContentView(R.layout.keyboard_number);

        text_Input = rootView.findViewById(R.id.text_Input);
        text_Input.setSoundEffectsEnabled(false);
        text_Input.setOnClickListener(this);

        mNumberKeyboard = new Keyboard(mContext, R.xml.keyboard_number);
        mKeyboardView = rootView.findViewById(R.id.keyboard_number);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.setPreviewEnabled(false);// !!! Must be false
        mKeyboardView.setKeyboard(mNumberKeyboard);
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
            }else{
                text_Input.setText(number.substring(0, number.length() -1));
            }
            number = text_Input.getText().toString();
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
        }else {
            String number = text_Input.getText().toString();
            if (number.length() >= maxLen){
                text_Input.setText(number.substring(number.length() - maxLen + 1));
                text_Input.append(Character.toString((char) primaryCode));
            }else {
                text_Input.append(Character.toString((char) primaryCode));
            }
            number = text_Input.getText().toString();
            if (mOnKeyboardListener != null){
                mOnKeyboardListener.onKeyUpdate(number);
            }
        }
    }

    public static void show(Activity context, int maxLen, OnKeyboardListener listener) {
        View v= context.getWindow().getDecorView().getRootView();
        new NumberKeyboard(context, maxLen, listener).show(v);
    }

    public static NumberKeyboard create(Context context, int maxLen, OnKeyboardListener listener) {
        return new NumberKeyboard(context, maxLen, listener);
    }

}
