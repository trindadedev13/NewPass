package com.gero.newpass.utilities;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;

public class SystemBarColorHelper {

    public static void changeBarsColor(Context context, int color) {
        if (SharedPreferencesHelper.isDarkModeSet(context)) {
            try {
                Window window = getWindow(context);
                View decor = window.getDecorView();
                decor.setSystemUiVisibility(0);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(context, color));
                window.setNavigationBarColor(ContextCompat.getColor(context, color));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("The provided color is invalid.");
            }
        }
    }

    private static Window getWindow(Context context) {
        return ((AppCompatActivity) context).getWindow();
    }
}
