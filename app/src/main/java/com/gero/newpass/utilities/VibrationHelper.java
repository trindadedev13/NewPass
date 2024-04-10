package com.gero.newpass.utilities;

import android.content.Context;
import android.os.Vibrator;

public class VibrationHelper {

    // Metodo per vibrare per un determinato intervallo di tempo
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(milliseconds);
            }
        }
    }

    // Metodo per vibrare con un pattern specifico
    public static void vibratePattern(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(pattern, repeat);
            }
        }
    }

    // Metodo per interrompere la vibrazione in corso
    public static void cancelVibration(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
