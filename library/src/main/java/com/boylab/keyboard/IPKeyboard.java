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
 * IP软键盘
 */
public class IPKeyboard extends AbstractKeyboard implements View.OnClickListener {

    private KeyboardView mKeyboardView;
    private Keyboard mIPKeyboard;

    private final TextView[] text_Input = new TextView[4];
    private final int[] textIDs = new int[]{R.id.text_input_0, R.id.text_input_1, R.id.text_input_2, R.id.text_input_3};
    private TextView mCurrentView;

    private int maxLen = 6;

    public IPKeyboard(Context context, int maxLen, OnKeyboardListener commitListener) {
        super(context, commitListener);
        this.maxLen = maxLen;
        initKeyBoard();
    }

    public IPKeyboard(Context context, OnKeyboardListener commitListener) {
        super(context, commitListener);
        initKeyBoard();
    }

    public void initKeyBoard(){
        final View rootView = setContentView(R.layout.keyboard_ip);

        for (int i = 0; i < 4; i++) {
            text_Input[i] = rootView.findViewById(textIDs[i]);
            text_Input[i].setTag(i);
        }

        for (TextView view : text_Input) {
            // 关闭点击声效
            view.setSoundEffectsEnabled(false);
            view.setOnClickListener(this);
        }

        mIPKeyboard = new Keyboard(mContext, R.xml.keyboard_number_decimal);
        mKeyboardView = rootView.findViewById(R.id.keyboard_ip);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.setPreviewEnabled(false);// !!! Must be false
        mKeyboardView.setKeyboard(mIPKeyboard);
    }

    @Override
    protected void onShow() {
        text_Input[0].performClick();
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        for (int i = index; i < 4; i++) {
            text_Input[i].setText(null);
        }

        if (mCurrentView != null){
            mCurrentView.setActivated(false);
        }
        mCurrentView = (TextView) v;
        mCurrentView.setActivated(true);

        for (int i = 0; i < 4; i++) {
            text_Input[i].setSelected(i == index);
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        super.onKey(primaryCode, keyCodes);
        if (primaryCode == Keyboard.KEYCODE_CANCEL){
            dismiss();
        }else if (primaryCode == Keyboard.KEYCODE_DELETE){
            /**
             * 删除退格
             */
            int index = (int) mCurrentView.getTag();
            String text = mCurrentView.getText().toString().trim();
            if (text.isEmpty()){
                if (index - 1 >= 0){
                    text = text_Input[index - 1].getText().toString().trim();
                    nextInput(index - 1);
                    mCurrentView.setText(text.substring(0, text.length() -1));
                }else {
                    //略过
                }
            }else {
                mCurrentView.setText(text.substring(0, text.length() -1));
            }
        }else if (primaryCode == Keyboard.KEYCODE_DONE){
            onKeyFinish();
            dismiss();
        }else {
            /**
             * 添加字符
             */
            int index = (int) mCurrentView.getTag();
            String text = mCurrentView.getText().toString().trim();
            if (primaryCode == '.'){
                if (text.isEmpty()){
                    //略过
                }else {
                    if (index == 3){
                        //略过
                    }else{
                        nextInput(index + 1);
                    }
                }
            }else {
                if (text.length() < 3){
                    mCurrentView.append(Character.toString((char) primaryCode));
                }else {
                    if (index == 3){
                        //略过
                    }else{
                        nextInput(index + 1);
                        mCurrentView.append(Character.toString((char) primaryCode));
                    }
                }
            }
        }
        onKeyUpdate(primaryCode);
    }

    private void nextInput(int index) {
        if (index < 0 || index > 3){
            return;
        }
        text_Input[index].performClick();
    }

    private void onKeyUpdate(int primaryCode){
        String inputText = "";
        int index = (int) mCurrentView.getTag();
        for (int i = 0; i <= index; i++) {
            if (i != 0){
                inputText = inputText.concat(".");
            }
            String text = text_Input[i].getText().toString().trim();
            inputText = inputText.concat(text.isEmpty() ? "0": text);
        }
        if (mOnKeyboardListener != null){
            mOnKeyboardListener.onKeyPress(primaryCode, inputText);
        }
    }

    private void onKeyFinish(){
        String inputText = "";
        int index = (int) mCurrentView.getTag();
        for (int i = 0; i <= index; i++) {
            if (i != 0){
                inputText = inputText.concat(".");
            }
            String text = text_Input[i].getText().toString().trim();
            inputText = inputText.concat(text);
        }
        if (mOnKeyboardListener != null){
            mOnKeyboardListener.onKeyFinish(inputText);
        }
    }


    public static void show(Activity context, OnKeyboardListener listener) {
        View v= context.getWindow().getDecorView().getRootView();
        new IPKeyboard(context, listener).show(v);
    }

    public static IPKeyboard create(Context context, OnKeyboardListener listener) {
        return new IPKeyboard(context, listener);
    }


}
