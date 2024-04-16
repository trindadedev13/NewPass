package com.gero.newpass.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gero.newpass.ContextWrapper.NewPassContextWrapper;
import com.gero.newpass.R;
import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;
import com.gero.newpass.encryption.EncryptionHelper;
import com.gero.newpass.factory.ViewMoldelsFactory;
import com.gero.newpass.repository.ResourceRepository;
import com.gero.newpass.utilities.StringHelper;
import com.gero.newpass.utilities.SystemBarColorHelper;
import com.gero.newpass.utilities.VibrationHelper;
import com.gero.newpass.viewmodel.LoginViewModel;
import com.gero.newpass.databinding.ActivityLoginBinding;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText passwordEntry;
    private ImageButton buttonRegisterOrUnlock;
    private ImageView passwordBox, bgImage;
    private TextView welcomeTextView, textViewRegisterOrUnlock;
    private EncryptedSharedPreferences encryptedSharedPreferences;
    private LoginViewModel loginViewModel;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        initViews(binding);

        textViewRegisterOrUnlock.setText(getString(R.string.create_password_button_text));
        welcomeTextView.setText(getString(R.string.welcome_newpass_text));

        loginViewModel = new ViewModelProvider(this, new ViewMoldelsFactory(new ResourceRepository(getApplicationContext()))).get(LoginViewModel.class);

        loginViewModel.getLoginMessageLiveData().observe(this, message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());

        loginViewModel.getLoginSuccessLiveData().observe(this, success -> {
            String savedPasswordSharedPreferences = encryptedSharedPreferences.getString("password", "");

            if (success) {
                Intent intent = new Intent(LoginActivity.this, MainViewActivity.class);
                StringHelper.setSharedString(savedPasswordSharedPreferences);
                startActivity(intent);
                finish();
            }
        });

        encryptedSharedPreferences = EncryptionHelper.getEncryptedSharedPreferences(getApplicationContext());

        //Determining whether to set dark or light mode based on shared preferences
        SharedPreferencesHelper.toggleDarkLightModeUI(this);

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

            Log.d("LOGIN_VM", "Already launched before");

            BiometricManager biometricManager = BiometricManager.from(this);

            int canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.DEVICE_CREDENTIAL);

            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                Log.d("LOGIN_VM", "App can authenticate using biometrics or device credentials.");

                buttonRegisterOrUnlock.setVisibility(View.GONE);
                passwordEntry.setVisibility(View.GONE);
                passwordBox.setVisibility(View.GONE);
                welcomeTextView.setVisibility(View.GONE);
                bgImage.setVisibility(View.GONE);

                loginViewModel.loginUserWithBiometricAuth(this);

            } else {

                Log.d("LOGIN_VM", "No biometric or credential authentication features available on this device.");

                view.setOnTouchListener((v, event) -> {

                    String passwordInput = passwordEntry.getText().toString();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            VibrationHelper.vibrate(this, getResources().getInteger(R.integer.vibration_duration0));
                            return true;
                        case MotionEvent.ACTION_UP:
                            v.performClick();
                            loginViewModel.loginUserWithPassword(passwordInput, encryptedSharedPreferences);
                            VibrationHelper.vibrate(this, getResources().getInteger(R.integer.vibration_duration1));
                            return true;
                    }
                    return false;
                });
            }
        } else {

            Log.d("LOGIN_VM", "First launch");

            buttonRegisterOrUnlock.setOnClickListener(v -> {
                String passwordInput = passwordEntry.getText().toString();
                loginViewModel.createUser(passwordInput, encryptedSharedPreferences);
                VibrationHelper.vibrate(this, getResources().getInteger(R.integer.vibration_duration1));
            });
        }
    }


    private void initViews(ActivityLoginBinding binding) {
        passwordEntry = binding.loginTwPassword;
        welcomeTextView = binding.welcomeLoginTw;
        buttonRegisterOrUnlock = binding.registerOrUnlockButton;
        textViewRegisterOrUnlock = binding.registerOrUnlockTextView;
        passwordBox = binding.backgroundInputbox2;
        bgImage = binding.logoLogin;
    }

    @Override
    protected void attachBaseContext(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesHelper.SHARED_PREF_FLAG, MODE_PRIVATE);
        String language = sharedPreferences.getString(SharedPreferencesHelper.LANG_PREF_FLAG, "en");
        super.attachBaseContext(NewPassContextWrapper.wrap(context, language));
        Locale locale = new Locale(language);
        Resources resources = getBaseContext().getResources();
        Configuration conf = resources.getConfiguration();
        conf.locale = locale;
        resources.updateConfiguration(conf, resources.getDisplayMetrics());
    }
}