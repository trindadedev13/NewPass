package com.gero.newpass.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> darkThemeStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hapticFeedbackStateLiveData = new MutableLiveData<>();
    private boolean darkTheme = true;
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

    public void toggleDarkTheme() {
        darkTheme = !darkTheme;
        darkThemeStateLiveData.setValue(darkTheme);
    }

    public void toggleHapticFeedback() {
        hapticFeedback = !hapticFeedback;
        hapticFeedbackStateLiveData.setValue(hapticFeedback);
    }
}
