package com.gero.newpass.utilities;

import android.content.Context;
import android.os.Vibrator;

public class VibrationHelper {

    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(milliseconds);
            }
        }
    }

    public static void vibratePattern(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(pattern, repeat);
            }
        }
    }

    public static void cancelVibration(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
