package com.boylab.number;

import android.app.Activity;

import com.boylab.easykeyboard.R;

/**
 * 自定义键盘
 * Created by kuangch on 17/3/10.
 */
public class KeyboardIP extends BaseKeyboard{

    public KeyboardIP(Activity activity) {

        init(activity, R.xml.keyboard_ip_address);
    }
}