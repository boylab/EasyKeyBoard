package com.boylab.number;

import android.app.Activity;

import com.boylab.easykeyboard.R;

/**
 * 自定义键盘
 * Created by kuangch on 17/3/10.
 */
public class KeyboardIdentity extends BaseKeyboard{

    public KeyboardIdentity(Activity activity) {

        init(activity, R.xml.keyboard_identity);
    }
}