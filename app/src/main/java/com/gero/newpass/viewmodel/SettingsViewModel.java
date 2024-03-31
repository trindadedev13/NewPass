package com.gero.newpass.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;

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

        //Log.i("283957", "value in the shared pref before clicking the button: " + darkThemeState);

        darkThemeState = !darkThemeState;
        editor.putBoolean("darkTheme", darkThemeState);
        editor.apply();
        darkThemeStateLiveData.setValue(darkThemeState);

        //darkThemeState = sharedPreferences.getBoolean("darkTheme", false);
        //Log.i("283957", "value in the shared pref after clicking the button: " + darkThemeState);
    }

    public void toggleHapticFeedback(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean darkThemeState = sharedPreferences.getBoolean("darkTheme", false);

        hapticFeedback = !hapticFeedback;
        editor.putBoolean("hapticFeedback", darkThemeState);
        editor.apply();
        hapticFeedbackStateLiveData.setValue(hapticFeedback);
    }
}
