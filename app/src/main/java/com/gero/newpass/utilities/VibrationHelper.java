package com.gero.newpass.utilities;

import android.view.HapticFeedbackConstants;
import android.view.View;


public class VibrationHelper {

    /**
     * Types of vibration.
     */
    public enum VibrationType {
        Weak, Strong
    }

    public static void vibrate(View view, VibrationType type) {
        switch (type) {
            case Weak:
                view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);
                break;
            case Strong:
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                break;
        }

    }
}
