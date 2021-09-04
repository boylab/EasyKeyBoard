package com.boylab.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.boylab.callback.OnKeyboardListener;
import com.boylab.easykeyboard.R;
import com.boylab.view.CarKeyboardView;

/**
 * 中国民用
 * 车号键盘
 */
public class CarKeyboard extends AbstractKeyboard implements View.OnClickListener {

    private static final int NUMBER_LENGTH = 8;

    private KeyboardView mKeyboardView;
    private Keyboard mCarKeyboard;

    private TextView text_Input;
    private Button button_Cancel, button_Confirm;
    private TextView mCurrentView;

    public CarKeyboard(Context context, String defaultPlate, OnKeyboardListener keyActionListener) {
        super(context, keyActionListener);
        final View rootView = setContentView(R.layout.keyboard_car_number, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        text_Input = rootView.findViewById(R.id.text_Input);
        text_Input.setSoundEffectsEnabled(false);
        text_Input.setOnClickListener(this);

        mCarKeyboard = new Keyboard(context, R.xml.keyboard_car_number);
        mKeyboardView = rootView.findViewById(R.id.keyboard_car);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.setPreviewEnabled(false);// !!! Must be false
        mKeyboardView.setKeyboard(mCarKeyboard);


        button_Cancel = rootView.findViewById(R.id.button_Cancel);
        button_Confirm = rootView.findViewById(R.id.button_Confirm);
        button_Cancel.setOnClickListener(this);
        button_Confirm.setOnClickListener(this);
    }

    @Override
    protected void onShow() {
        text_Input.performClick();
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        if (viewID == R.id.text_Input) {
            if (mCurrentView != null) {
                mCurrentView.setActivated(false);
            }
            mCurrentView = (TextView) v;
            mCurrentView.setActivated(true);
        } else if (viewID == R.id.button_Cancel) {
            dismiss();
        } else if (viewID == R.id.button_Confirm) {
            String number = text_Input.getText().toString();
            if (mOnKeyboardListener != null) {
                mOnKeyboardListener.onKeyFinish(number);
            }
            dismiss();
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        super.onKey(primaryCode, keyCodes);

        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            String text = text_Input.getText().toString();
            if (TextUtils.isEmpty(text)) {
                return;
            } else {
                text_Input.setText(text.substring(0, text.length() - 1));
            }
            text = text_Input.getText().toString();
            if (mOnKeyboardListener != null) {
                mOnKeyboardListener.onKeyUpdate(text);
            }
        } else {
            String text = text_Input.getText().toString();
            if (text.length() < NUMBER_LENGTH) {
                text_Input.append(Character.toString((char) primaryCode));
            }
            text = text_Input.getText().toString();
            if (mOnKeyboardListener != null) {
                mOnKeyboardListener.onKeyUpdate(text);
            }
        }
    }

    public static void show(Activity activity, String defaultPlate, OnKeyboardListener listener) {
        new CarKeyboard(activity, defaultPlate, listener).show(activity.getWindow().getDecorView().getRootView());
    }

    public static CarKeyboard create(Context context, String defaultPlate, OnKeyboardListener listener) {
        return new CarKeyboard(context, defaultPlate, listener);
    }
}
