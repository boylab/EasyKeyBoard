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
 * 身份证键盘
 */
public class IDKeyboard extends AbstractKeyboard implements View.OnClickListener {

    private KeyboardView mKeyboardView;
    private Keyboard mIDKeyboard;

    private TextView text_Input;
    private TextView mCurrentView;

    private int maxLen = 18;

    public IDKeyboard(Context context, OnKeyboardListener commitListener) {
        super(context, commitListener);
        initKeyBoard();
    }

    public void initKeyBoard(){
        final View rootView = setContentView(R.layout.keyboard_identity);

        text_Input = rootView.findViewById(R.id.text_Input);
        text_Input.setSoundEffectsEnabled(false);
        text_Input.setOnClickListener(this);

        mIDKeyboard = new Keyboard(mContext, R.xml.keyboard_identity);
        mKeyboardView = rootView.findViewById(R.id.keyboard_ID);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.setPreviewEnabled(false);// !!! Must be false
        mKeyboardView.setKeyboard(mIDKeyboard);
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
            if (number.contains("X")){
                // nothing to do
            }else if (number.length() >= maxLen){
                // nothing to do
            }else {
                text_Input.append(Character.toString((char) primaryCode));
            }
        }
        String number = text_Input.getText().toString();
        if (mOnKeyboardListener != null){
            mOnKeyboardListener.onKeyPress(primaryCode, number);
        }
    }

    public static void show(Activity context, OnKeyboardListener listener) {
        View v= context.getWindow().getDecorView().getRootView();
        new IDKeyboard(context, listener).show(v);
    }

    public static IDKeyboard create(Context context, OnKeyboardListener listener) {
        return new IDKeyboard(context, listener);
    }


}
