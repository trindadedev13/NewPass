package com.gero.newpass.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gero.newpass.R;
import com.gero.newpass.utilities.StringUtility;
import com.gero.newpass.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.LoginViewModel;
import com.gero.newpass.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginActivity extends AppCompatActivity {

    private EditText passwordEntry;
    private ImageButton buttonRegisterOrUnlock;
    private TextView welcomeTextView, textViewRegisterOrUnlock;
    private EncryptedSharedPreferences sharedPreferences;
    private LoginViewModel loginViewModel;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        initViews(binding);

        textViewRegisterOrUnlock.setText("[create password]");
        welcomeTextView.setText("Welcome to\nNewpass!");

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginMessageLiveData().observe(this, message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());

        loginViewModel.getLoginSuccessLiveData().observe(this, success -> {
            String savedPasswordSharedPreferences = sharedPreferences.getString("password", "");

            if (success) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                StringUtility.setSharedString(savedPasswordSharedPreferences);
                startActivity(intent);
                finish();
            }
        });


        try {
            MasterKey masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    this,
                    "loginkey",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        String password = sharedPreferences.getString("password", "");

        if (!password.isEmpty()){
            textViewRegisterOrUnlock.setText("[unlock NewPass]");
            welcomeTextView.setText("Welcome back\nto NewPass!");

            buttonRegisterOrUnlock.setOnClickListener(v -> {
                String passwordInput = passwordEntry.getText().toString();
                loginViewModel.loginUser(passwordInput, sharedPreferences);
            });
        } else {
            buttonRegisterOrUnlock.setOnClickListener(v -> {
                String passwordInput = passwordEntry.getText().toString();
                loginViewModel.createUser(passwordInput, sharedPreferences);
            });
        }


    }

    private void initViews(ActivityLoginBinding binding) {
        passwordEntry = binding.loginTwPassword;
        welcomeTextView = binding.welcomeLoginTw;
        buttonRegisterOrUnlock = binding.registerOrUnlockButton;
        textViewRegisterOrUnlock = binding.registerOrUnlockTextView;
    }
}