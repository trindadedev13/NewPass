package com.gero.newpass.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gero.newpass.Activities.MainActivity;
import com.gero.newpass.R;
import com.gero.newpass.model.utilities.StringUtility;
import com.gero.newpass.model.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.LoginViewModel;
import com.gero.newpass.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginActivity extends AppCompatActivity {

    private EditText loginTextViewName, loginTextViewPassword;
    private EncryptedSharedPreferences sharedPreferences;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        loginTextViewName = binding.loginTwName;
        loginTextViewPassword = binding.loginTwPassword;
        TextView welcomeRegisterTextView = binding.welcomeRegisterTw;
        TextView welcomeLoginTextView = binding.welcomeLoginTw;
        ImageButton buttonLogin = binding.loginButton;
        ImageButton buttonRegister = binding.registerButton;
        ImageView backgroundInputboxName = binding.backgroundInputbox1;
        ImageView logo = binding.logoRegister;
        ImageView logoLogin = binding.logoLogin;


        buttonLogin.setVisibility(View.GONE);
        welcomeLoginTextView.setVisibility(View.GONE);
        logoLogin.setVisibility(View.GONE);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginMessageLiveData().observe(this, message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show());

        loginViewModel.getLoginSuccessLiveData().observe(this, success -> {
            String savedPasswordSharedPreferences = sharedPreferences.getString("password", "");

            if (success) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                StringUtility.setSharedString(savedPasswordSharedPreferences);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(LoginActivity.this, "Login failed. Access denied", Toast.LENGTH_SHORT).show();
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

        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");

        if (!password.isEmpty() && !name.isEmpty()){
            buttonRegister.setVisibility(View.GONE);
            logo.setVisibility(View.GONE);
            welcomeRegisterTextView.setVisibility(View.GONE);
            loginTextViewName.setVisibility(View.GONE);
            backgroundInputboxName.setVisibility(View.GONE);

            buttonLogin.setVisibility(View.VISIBLE);
            logoLogin.setVisibility(View.VISIBLE);
            welcomeLoginTextView.setVisibility(View.VISIBLE);

            String next = "<font color='"+ String.format("#%06X", 0xFFFFFF &  ContextCompat.getColor(this, R.color.accent)) +"'>"+ name +"</font>";
            welcomeLoginTextView.setText(Html.fromHtml("Welcome back<br>" + next + "!", Html.FROM_HTML_MODE_LEGACY));
        }

        buttonLogin.setOnClickListener(v -> {
            String passwordInput = loginTextViewPassword.getText().toString();
            loginViewModel.loginUser(passwordInput, sharedPreferences);
        });

        buttonRegister.setOnClickListener(v -> {
            String nameInput = loginTextViewName.getText().toString();
            String passwordInput = loginTextViewPassword.getText().toString();
            loginViewModel.createUser(nameInput, passwordInput, sharedPreferences);
        });
    }
}