package com.gero.newpass.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricManager;
import android.os.Build;
import android.util.Log;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.gero.newpass.R;
import com.gero.newpass.repository.ResourceRepository;

import java.util.concurrent.Executor;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> loginMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccessLiveData = new MutableLiveData<>();
    private final ResourceRepository resourceRepository;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    
    
    public LoginViewModel(ResourceRepository resourceRepository) {
        this.resourceRepository =  resourceRepository;
    }

    
    public LiveData<String> getLoginMessageLiveData() {
        return loginMessageLiveData;
    }
    public LiveData<Boolean> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }



    public void loginUserWithoutBiometric(String password, EncryptedSharedPreferences sharedPreferences) {

        String savedPasswordSharedPreferences = sharedPreferences.getString("password", "");

        if (savedPasswordSharedPreferences.equals(password)) {
            loginSuccessLiveData.setValue(true);
            loginMessageLiveData.setValue(resourceRepository.getString(R.string.login_done));
        } else {
            loginSuccessLiveData.setValue(false);
            loginMessageLiveData.setValue(resourceRepository.getString(R.string.access_denied));
        }
    }

    public void loginUser(Context context) {
        BiometricManager biometricManager = BiometricManager.from(context);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("LOGIN_VM", "App can authenticate using biometrics.");
                promptBiometricLogin(context);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("LOGIN_VM", "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("LOGIN_VM", "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e("LOGIN_VM", "The user hasn't associated any biometric credentials with their account.");

                break;
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                break;
        }
    }

    private void promptBiometricLogin(Context context) {
        Executor executor = ContextCompat.getMainExecutor(context);
        biometricPrompt = new BiometricPrompt((FragmentActivity) context, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                loginMessageLiveData.postValue(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                loginSuccessLiveData.postValue(true);
                loginMessageLiveData.postValue(resourceRepository.getString(R.string.login_done));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                loginMessageLiveData.postValue(resourceRepository.getString(R.string.access_denied));
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }


    public void createUser(String password, EncryptedSharedPreferences sharedPreferences) {

        if (password.length() >= 4) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password", password);
            editor.apply();

            loginSuccessLiveData.setValue(true);
            loginMessageLiveData.setValue(resourceRepository.getString(R.string.user_created_successfully));
        } else {
            loginSuccessLiveData.setValue(false);
            loginMessageLiveData.setValue(resourceRepository.getString(R.string.password_must_be_at_least_4_characters_long));
        }
    }
}
