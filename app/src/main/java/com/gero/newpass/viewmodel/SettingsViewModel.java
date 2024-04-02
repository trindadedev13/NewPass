package com.gero.newpass.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> darkThemeStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hapticFeedbackStateLiveData = new MutableLiveData<>();
    private boolean darkTheme = false;
    private boolean hapticFeedback = false;

    public SettingsViewModel() {
        darkThemeStateLiveData.setValue(darkTheme);
        hapticFeedbackStateLiveData.setValue(hapticFeedback);
    }

    public LiveData<Boolean> getDarkThemeStateLiveData() {
        return darkThemeStateLiveData;
    }

    public LiveData<Boolean> getHapticFeedbackStateLiveData() {
        return hapticFeedbackStateLiveData;
    }

    public void toggleDarkTheme(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean darkThemeState = sharedPreferences.getBoolean("darkTheme", false);

        darkThemeState = !darkThemeState;
        editor.putBoolean("darkTheme", darkThemeState);
        editor.apply();

        darkThemeStateLiveData.setValue(darkThemeState);

        /* TODO: Enable switch theme */
        if (darkThemeState) {

            // Switch to dak mode
            Log.i("283957", "dark theme");
        } else {

            // Switch to light mode
            Log.i("283957", "light theme");
        }
    }

    public void toggleHapticFeedback(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean hapticFeedback = sharedPreferences.getBoolean("hapticFeedback", false);

        hapticFeedback = !hapticFeedback;
        editor.putBoolean("hapticFeedback", hapticFeedback);
        editor.apply();

        hapticFeedbackStateLiveData.setValue(hapticFeedback);

        /* TODO: Enable haptic feedback */
    }
}
