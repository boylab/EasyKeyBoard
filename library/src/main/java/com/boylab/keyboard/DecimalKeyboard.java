package com.boylab.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.boylab.easykeyboard.R;

public class DecimalKeyboard extends AbstractKeyboard{

    private KeyboardView mKeyboardView;
    private Keyboard mNumberKeyboard;

    private TextView text_Input;
    private TextView mSelectedTextView;

    private int maxIntLen = 6, pointLen = 2;
    private boolean isAddPoint = false;

    public DecimalKeyboard(Context context,  int maxIntLen, int pointLen, OnKeyActionListener commitListener) {
        super(context, commitListener);

        this.maxIntLen = maxIntLen;
        this.pointLen = pointLen;
        initKeyBoard();
    }


    public DecimalKeyboard(Context context, OnKeyActionListener commitListener) {
        super(context, commitListener);
        initKeyBoard();
    }

    public void initKeyBoard(){
        final View contentView = putContentView(R.layout.keyboard_number);

        text_Input = contentView.findViewById(R.id.text_Input);

        final View.OnClickListener listener = createNumberListener();
        text_Input.setSoundEffectsEnabled(false);
        text_Input.setOnClickListener(listener);

        mNumberKeyboard = new Keyboard(mContext, R.xml.keyboard_number_decimal);
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
                    }else {
                        text_Input.setText(number.substring(0, number.length() -1));
                    }
                    number = text_Input.getText().toString();
                    isAddPoint = number.contains(".");

                    if (mOnKeyActionListener != null){
                        mOnKeyActionListener.onProcess(number);
                    }
                }else if (charCode == Keyboard.KEYCODE_DONE){
                    String number = text_Input.getText().toString();

                    if (TextUtils.isEmpty(number)){
                        dismiss();
                        return;
                    }
                    if (mOnKeyActionListener != null){
                        mOnKeyActionListener.onFinish(number);
                    }
                    dismiss();
                }else if (charCode == '.'){
                    if (isAddPoint){
                        return;
                    }else {
                        String number = text_Input.getText().toString();
                        if (TextUtils.isEmpty(number)){
                            return;
                        }else {
                            text_Input.append(Character.toString((char) charCode));
                        }
                    }
                    String number = text_Input.getText().toString();
                    isAddPoint = number.contains(".");
                    if (mOnKeyActionListener != null){
                        mOnKeyActionListener.onProcess(number);
                    }
                }else {
                    String number = text_Input.getText().toString();
                    if (isAddPoint){
                        int indexPoint = number.lastIndexOf(".");
                        if (number.length() - indexPoint - 1 >= pointLen){
                            return;
                        }else {
                            text_Input.append(Character.toString((char) charCode));
                        }
                    }else {
                        if (number.length() >= maxIntLen){
                            text_Input.setText(number.substring(number.length() - maxIntLen + 1));
                            text_Input.append(Character.toString((char) charCode));
                        }else {
                            text_Input.append(Character.toString((char) charCode));
                        }
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




    public static void show(Activity context, int maxIntLen, int pointLen, OnKeyActionListener listener) {
        View v= context.getWindow().getDecorView().getRootView();
        new DecimalKeyboard(context, maxIntLen, pointLen, listener).show(v);
    }

    public static DecimalKeyboard create(Context context, int maxIntLen, int pointLen, OnKeyActionListener listener) {
        return new DecimalKeyboard(context, maxIntLen, pointLen, listener);
    }
}
