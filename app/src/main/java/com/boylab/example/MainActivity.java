package com.boylab.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.boylab.EasyKeyBoard;
import com.boylab.keyboard.OnKeyActionListener;
import com.boylab.keyboard.VehiclePlateKeyboard;

public class MainActivity extends AppCompatActivity {

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyKeyBoard.bindCarPlate(MainActivity.this,"武J12345", new OnKeyActionListener(){

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

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EasyKeyBoard.bindNumber(MainActivity.this, 2,new OnKeyActionListener() {
                    @Override
                    public void onFinish(String input) {
                        Log.i(">>>boylab>>", ">>>onFinish: "+input);
                    }

                    @Override
                    public void onProcess(String input) {
                        display.setText("Processing: " + input);
                    }
                });
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyKeyBoard.bindDecimal(MainActivity.this, 4,2,new OnKeyActionListener() {
                    @Override
                    public void onFinish(String input) {
                        Log.i(">>>boylab>>", ">>>onFinish: "+input);
                    }

                    @Override
                    public void onProcess(String input) {
                        display.setText("Processing: " + input);
                    }
                });
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}