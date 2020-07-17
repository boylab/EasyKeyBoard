package com.boylab.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.boylab.easykeyboard.R;

public class IPKeyboard extends AbstractKeyboard{

    private KeyboardView mKeyboardView;
    private Keyboard mNumberKeyboard;

    private TextView text_Input;
    private TextView mSelectedTextView;

    private int maxLen = 6;

    public IPKeyboard(Context context, int maxLen, OnKeyActionListener commitListener) {
        super(context, commitListener);

        this.maxLen = maxLen;
        initKeyBoard();
    }


    public IPKeyboard(Context context, OnKeyActionListener commitListener) {
        super(context, commitListener);
        initKeyBoard();
    }

    public void initKeyBoard(){
        final View contentView = putContentView(R.layout.keyboard_ip);

        text_Input = contentView.findViewById(R.id.text_Input);

        final View.OnClickListener listener = createNumberListener();
        text_Input.setSoundEffectsEnabled(false);
        text_Input.setOnClickListener(listener);

        mNumberKeyboard = new Keyboard(mContext, R.xml.keyboard_numbers);
        mKeyboardView = (KeyboardView) contentView.findViewById(R.id.keyboard_view);
        mKeyboardView.setOnKeyboardActionListener(new OnKeyboardActionHandler() {
            @Override
            public void onKey(int charCode, int[] keyCodes) {
                beep(mContext);
                if (charCode == Keyboard.KEYCODE_CANCEL){
                    dismiss();
                }else if (charCode == Keyboard.KEYCODE_DELETE){
                    String number = text_Input.getText().toString();
                    if (TextUtils.isEmpty(number)){
                        return;
                    }else{
                        text_Input.setText(number.substring(0, number.length() -1));
                    }

                    if (mOnKeyActionListener != null){
                        mOnKeyActionListener.onProcess(number);
                    }
                }else if (charCode == Keyboard.KEYCODE_DONE){
                    String number = text_Input.getText().toString();

                    if (TextUtils.isEmpty(number)){
                        return;
                    }
                    if (mOnKeyActionListener != null){
                        mOnKeyActionListener.onFinish(number);
                    }
                    dismiss();
                }else {
                    String number = text_Input.getText().toString();
                    if (number.length() >= maxLen){
                        text_Input.setText(number.substring(number.length() - maxLen + 1));
                        text_Input.append(Character.toString((char) charCode));
                    }else {
                        text_Input.append(Character.toString((char) charCode));
                    }
                    number = text_Input.getText().toString();
                    if (mOnKeyActionListener != null){
                        mOnKeyActionListener.onProcess(number);
                    }
                }
            }
        });
        mKeyboardView.setPreviewEnabled(false);// !!! Must be false
        mKeyboardView.setKeyboard(mNumberKeyboard);
    }

    @Override
    protected void onShow() {
        text_Input.performClick();
    }


    private View.OnClickListener createNumberListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedTextView != null){
                    mSelectedTextView.setActivated(false);
                }
                mSelectedTextView = (TextView) view;
                mSelectedTextView.setActivated(true);
            }
        };
    }




    public static void show(Activity context, OnKeyActionListener listener) {
        View v= context.getWindow().getDecorView().getRootView();
        new IPKeyboard(context, listener).show(v);
    }

    public static IPKeyboard create(Context context, OnKeyActionListener listener) {
        return new IPKeyboard(context, listener);
    }
}
