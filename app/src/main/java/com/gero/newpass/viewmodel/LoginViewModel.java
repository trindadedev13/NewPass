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

        if (savedPasswordSharedPreferences.equals(password)) {
            loginSuccessLiveData.setValue(true);
            loginMessageLiveData.setValue("Login done");
        } else {
            loginSuccessLiveData.setValue(false);
            loginMessageLiveData.setValue("Access Denied");
        }
    }



    public void createUser(String password, EncryptedSharedPreferences sharedPreferences) {

        if (password.length() >= 4) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password", password);
            editor.apply();

            loginSuccessLiveData.setValue(true);
            loginMessageLiveData.setValue("User created successfully!");
        } else {
            loginSuccessLiveData.setValue(false);
            loginMessageLiveData.setValue("Password must be at least 4 characters long!");
        }
    }
}
