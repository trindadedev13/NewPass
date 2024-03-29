package com.gero.newpass.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.security.crypto.EncryptedSharedPreferences;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> loginMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccessLiveData = new MutableLiveData<>();



    public LiveData<String> getLoginMessageLiveData() {
        return loginMessageLiveData;
    }

    public LiveData<Boolean> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }



    public void loginUser(String password, EncryptedSharedPreferences sharedPreferences) {

        String savedPasswordSharedPreferences = sharedPreferences.getString("password", "");
        String savedNameSharedPreferences = sharedPreferences.getString("name", "");

        if (savedPasswordSharedPreferences.equals(password)) {
            loginSuccessLiveData.setValue(true);
            loginMessageLiveData.setValue("Login done for: " + savedNameSharedPreferences);
        } else {
            loginSuccessLiveData.setValue(false);
            loginMessageLiveData.setValue("Access Denied");
        }
    }



    public void createUser(String name, String password, EncryptedSharedPreferences sharedPreferences) {

        if (name.length() >= 4 && name.length() <= 10 && password.length() >= 4) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password", password);
            editor.putString("name", name);
            editor.apply();

            loginSuccessLiveData.setValue(true);
            loginMessageLiveData.setValue("User " + name + " created");
        } else {
            loginSuccessLiveData.setValue(false);
            loginMessageLiveData.setValue("Username must be between 4 and 10 characters, and password must be at least 4 characters long.");
        }
    }
}
