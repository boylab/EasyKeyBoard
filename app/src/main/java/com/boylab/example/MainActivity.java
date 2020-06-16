package com.boylab.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.boylab.easykeyboard.car.OnKeyActionListener;
import com.boylab.easykeyboard.car.PasswordKeyboard;
import com.boylab.easykeyboard.car.VehiclePlateKeyboard;
import com.boylab.easykeyboard.number.BaseKeyboard;
import com.boylab.easykeyboard.number.KeyboardIP;
import com.boylab.easykeyboard.number.KeyboardIdentity;
import com.boylab.easykeyboard.number.KeyboardNumber;
import com.boylab.easykeyboard.number.KeyboardUtils;
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

        final TextView display = (TextView) findViewById(R.id.display);
        final Button vehicle = (Button) findViewById(R.id.vehicle);

        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VehiclePlateKeyboard keyboard = new VehiclePlateKeyboard(MainActivity.this, new OnKeyActionListener() {
                    @Override
                    public void onFinish(String input) {
                        display.setText(input);
                    }

                    @Override
                    public void onProcess(String input) {
                        display.setText("Processing: " + input);
                    }
                });
                keyboard.setDefaultPlateNumber("武J12345");
                keyboard.show(getWindow().getDecorView().getRootView());
            }
        });

        final Button number = (Button) findViewById(R.id.number);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordKeyboard.show(MainActivity.this, new OnKeyActionListener() {
                    @Override
                    public void onFinish(String input) {
                        display.setText(input);
                    }

                    @Override
                    public void onProcess(String input) {
                        display.setText("Processing: " + input);
                    }
                });
            }
        });



    }
}