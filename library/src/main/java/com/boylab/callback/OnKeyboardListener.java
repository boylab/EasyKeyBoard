package com.boylab.callback;

/**
 * @author  yoojia.chen@gmail.com
 * @version version 2015-04-24
 * @since   1.6
 */

/**
 *
 */
public interface OnKeyboardListener {

    /**
     * 输入过程中的数据
     * @param primaryCode (KEYCODE_CANCEL = -3; KEYCODE_DONE = -4; KEYCODE_DELETE = -5;)
     * @param input 当前已经输入的内容
     */
    void onKeyPress(int primaryCode, String input);

    /**
     * 输入完成并提交
     * @param input 输入内容
     */
    void onKeyFinish(String input);

}
