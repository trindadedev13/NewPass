package com.gero.newpass.utilities;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class AnimationsUtility {
    public static void errorAnimation(View... views) {

        for (View view : views) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(view);
        }
    }
}
