package com.boylab.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.widget.TextView;

import com.boylab.callback.OnKeyboardListener;
import com.boylab.easykeyboard.R;

/**
 * 6位密码键盘
 */
public class PassKeyboard extends AbstractKeyboard implements View.OnClickListener {

    private KeyboardView mKeyboardView;
    private Keyboard mPassKeyboard;

    private final TextView[] text_Input = new TextView[6];
    private final int[] textIDs = new int[]{R.id.text_input_0, R.id.text_input_1, R.id.text_input_2, R.id.text_input_3, R.id.text_input_4, R.id.text_input_5};
    private TextView mCurrentView;

    public PassKeyboard(Context context, OnKeyboardListener commitListener) {
        super(context, commitListener);
        initKeyboard();
    }

    private void initKeyboard() {
        final View rootView = setContentView(R.layout.keyboard_password);

        for (int i = 0; i < 6; i++) {
            text_Input[i] = rootView.findViewById(textIDs[i]);
            text_Input[i].setTag(i);
        }

        for (TextView view : text_Input) {
            // 关闭点击声效
            view.setSoundEffectsEnabled(false);
            view.setOnClickListener(this);
        }

        mPassKeyboard = new Keyboard(mContext, R.xml.keyboard_password);
        mKeyboardView = rootView.findViewById(R.id.keyboard_password);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.setPreviewEnabled(false);// !!! Must be false
        mKeyboardView.setKeyboard(mPassKeyboard);
    }

    @Override
    protected void onShow() {
        text_Input[0].performClick();
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        for (int i = index; i < 6; i++) {
            text_Input[i].setText(null);
        }

        if (mCurrentView != null){
            mCurrentView.setActivated(false);
        }
        mCurrentView = (TextView) v;
        mCurrentView.setActivated(true);

        for (int i = 0; i < 6; i++) {
            text_Input[i].setSelected(i == index);
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        super.onKey(primaryCode, keyCodes);
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            /**
             * 删除退格
             */
            int index = (int) mCurrentView.getTag();
            String text = mCurrentView.getText().toString().trim();
            if (text.isEmpty()){
                nextInput(index - 1);
            }else {
                String currentText = mCurrentView.getText().toString().trim();
                mCurrentView.setText(currentText.substring(0, currentText.length() -1));
            }

            if (mOnKeyboardListener != null){
                mOnKeyboardListener.onKeyUpdate(getInput(text_Input));
            }
        }else {
            int index = (int) mCurrentView.getTag();
            mCurrentView.setText(Character.toString((char) primaryCode));
            if (index < 5){
                nextInput(index + 1);
                if (mOnKeyboardListener != null){
                    mOnKeyboardListener.onKeyUpdate(getInput(text_Input));
                }
            }else {
                // 输入最后一位密码，自动提交
                if (mOnKeyboardListener != null){
                    mOnKeyboardListener.onKeyFinish(getInput(text_Input));
                }
                dismiss();
            }
        }
    }

    private void nextInput(int index) {
        if (index < 0 || index > 5){
            return;
        }
        text_Input[index].performClick();
    }

    public static void show(Activity context, OnKeyboardListener listener) {
        View v= context.getWindow().getDecorView().getRootView();
        new PassKeyboard(context, listener).show(v);
    }

    public static PassKeyboard create(Context context, OnKeyboardListener listener) {
        return new PassKeyboard(context, listener);
    }

}
