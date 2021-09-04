package com.boylab.utils;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class BeepUtils {

    /**
     * 震动
     * @param context
     */
    public void beep(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            VibrationEffect vibe = VibrationEffect.createWaveform(new long[]{0, 60}, -1);
            vibrator.vibrate(vibe);
        }
    }

}