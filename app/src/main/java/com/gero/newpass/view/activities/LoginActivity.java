package com.gero.newpass.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gero.newpass.R;
import com.gero.newpass.encryption.EncryptionHelper;
import com.gero.newpass.utilities.StringUtility;
import com.gero.newpass.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.LoginViewModel;
import com.gero.newpass.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText passwordEntry;
    private ImageButton buttonRegisterOrUnlock;
    private TextView welcomeTextView, textViewRegisterOrUnlock;
    private EncryptedSharedPreferences encryptedSharedPreferences;
    private SharedPreferences sharedPreferences;
    private LoginViewModel loginViewModel;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        sharedPreferences = EncryptionHelper.getSharedPreferences(getApplicationContext());

        setLocale(getPreferredLanguage());

        initViews(binding);

        textViewRegisterOrUnlock.setText(getString(R.string.create_password_button_text));
        welcomeTextView.setText(getString(R.string.welcome_newpass_text));

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginMessageLiveData().observe(this, message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());

        loginViewModel.getLoginSuccessLiveData().observe(this, success -> {
            String savedPasswordSharedPreferences = encryptedSharedPreferences.getString("password", "");

            if (success) {
                Intent intent = new Intent(LoginActivity.this, MainViewActivity.class);
                StringUtility.setSharedString(savedPasswordSharedPreferences);
                startActivity(intent);
                finish();
            }
        });

        encryptedSharedPreferences = EncryptionHelper.getEncryptedSharedPreferences(getApplicationContext());

        setDarkMode();

        String password = encryptedSharedPreferences.getString("password", "");
        Boolean isPasswordEmpty = password.isEmpty();
        if (!isPasswordEmpty) {
            textViewRegisterOrUnlock.setText(getString(R.string.unlock_newpass_button_text));
            welcomeTextView.setText(getString(R.string.welcome_back_newpass_text));

        }
        buttonRegisterOrUnlockListener(buttonRegisterOrUnlock, isPasswordEmpty);
    }

    public void buttonRegisterOrUnlockListener(View view, Boolean isPasswordEmpty) {
        if (!isPasswordEmpty) {
            view.setOnClickListener(v -> {
                String passwordInput = passwordEntry.getText().toString();
                loginViewModel.loginUser(passwordInput, encryptedSharedPreferences);
            });
        } else {
            buttonRegisterOrUnlock.setOnClickListener(v -> {
                String passwordInput = passwordEntry.getText().toString();
                loginViewModel.createUser(passwordInput, encryptedSharedPreferences);
            });
        }
    }

    private void initViews(ActivityLoginBinding binding) {
        passwordEntry = binding.loginTwPassword;
        welcomeTextView = binding.welcomeLoginTw;
        buttonRegisterOrUnlock = binding.registerOrUnlockButton;
        textViewRegisterOrUnlock = binding.registerOrUnlockTextView;
    }

    private void setDarkMode() {
        //Checking if dark mode is set or not from shared  preferences, default value being True
        Boolean isDarkModeSet = sharedPreferences.getBoolean("isDarkModeOn", true);

        if (isDarkModeSet) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }
        updateNavigationBarColor(isDarkModeSet);
    }

    private void updateNavigationBarColor(Boolean isDarkMode) {
        Window window = getWindow();
        if (isDarkMode) {
            // Set dark color for navigation bar
            window.setNavigationBarColor(getResources().getColor(R.color.navigationbar_dark_mode));
        } else {
            // Set light color for navigation bar
            window.setNavigationBarColor(getResources().getColor(R.color.navigationbar_light_mode));
            // Additionally, if your navigation bar icons are not visible against the light background, you can make them dark:
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
    }

    private String getPreferredLanguage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getString("language", "").isEmpty()) {
            String languageOfTheDevice = Locale.getDefault().getLanguage();
            editor.putString("language", languageOfTheDevice);
            editor.apply();
        }

        return sharedPreferences.getString("language", "");
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}