package com.boylab.easykeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.boylab.easykeyboard.utils.IPUtils;
import com.boylab.easykeyboard.utils.IdentityUtils;

public class MainActivity extends AppCompatActivity {


    EditText et_1, et_2, et_3, et_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        KeyboardIdentity keyboardIdentity = new KeyboardIdentity(this);
        et_1 = findViewById(R.id.et_1);
        KeyboardUtils.bindEditTextEvent(keyboardIdentity, et_1);

        et_2 = findViewById(R.id.et_2);
        KeyboardIP keyboardIP = new KeyboardIP(this);
        KeyboardUtils.bindEditTextEvent(keyboardIP, et_2);

        et_3 = findViewById(R.id.et_3);
        KeyboardNumber keyboardNumber = new KeyboardNumber(this);
        KeyboardUtils.bindEditTextEvent(keyboardNumber, et_3);

        et_4 = findViewById(R.id.et_4);
        KeyboardNumber keyboardNumberRandom = new KeyboardNumber(this, true, false);
        KeyboardUtils.bindEditTextEvent(keyboardNumberRandom, et_4);


        keyboardIdentity.setOnOkClick(new BaseKeyboard.OnOkClick() {
            @Override
            public void onOkClick() {
                IdentityUtils identityUtils = new IdentityUtils();
                if (!identityUtils.isValidatedAllIdcard(et_1.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "输入的身份证号不合法", Toast.LENGTH_SHORT).show();
                }
            }
        });
        keyboardIP.setOnOkClick(new BaseKeyboard.OnOkClick() {
            @Override
            public void onOkClick() {
                if (!IPUtils.isValidatedIPPort(et_2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "输入的IP不合法", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
}