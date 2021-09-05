package com.boylab.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.boylab.EasyKeyBoard;
import com.boylab.callback.OnKeyboardListener;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private EasyKeyBoard easyKeyBoard = EasyKeyBoard.singleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.easyKeyBoard.bindCarNumber(MainActivity.this,null, new OnKeyboardListener(){
                    @Override
                    public void onKeyUpdate(String input) {
                        display.setText("Processing: " + input);
                    }

                    @Override
                    public void onKeyFinish(String input) {
                        Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.easyKeyBoard.bindNumber(MainActivity.this, 6,new OnKeyboardListener() {
                    @Override
                    public void onKeyUpdate(String input) {
                        display.setText("Processing: " + input);
                    }

                    @Override
                    public void onKeyFinish(String input) {
                        Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.easyKeyBoard.bindDecimal(MainActivity.this, 4,2,new OnKeyboardListener() {
                    @Override
                    public void onKeyUpdate(String input) {
                        display.setText("Processing: " + input);
                    }

                    @Override
                    public void onKeyFinish(String input) {
                        Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.easyKeyBoard.bindID(MainActivity.this, new OnKeyboardListener() {
                    @Override
                    public void onKeyUpdate(String input) {
                        display.setText("Processing: " + input);
                    }

                    @Override
                    public void onKeyFinish(String input) {
                        Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.easyKeyBoard.bindIP(MainActivity.this, new OnKeyboardListener() {
                    @Override
                    public void onKeyUpdate(String input) {
                        display.setText("Processing: " + input);
                    }

                    @Override
                    public void onKeyFinish(String input) {
                        Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.easyKeyBoard.bindPassword(MainActivity.this, new OnKeyboardListener() {
                    @Override
                    public void onKeyUpdate(String input) {
                        display.setText("Processing: " + input);
                    }

                    @Override
                    public void onKeyFinish(String input) {
                        Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}