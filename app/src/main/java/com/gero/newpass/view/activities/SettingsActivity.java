package com.gero.newpass.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.R;
import com.gero.newpass.databinding.ActivitySettingsBinding;
import com.gero.newpass.model.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    private SettingsViewModel settingsViewModel;
    private ImageButton buttonDarkTheme, buttonHapticFeedBack, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        initViews(binding);

        buttonDarkTheme.setOnClickListener(v -> settingsViewModel.toggleDarkTheme());
        buttonHapticFeedBack.setOnClickListener(v -> settingsViewModel.toggleHapticFeedback());

        settingsViewModel.getDarkThemeStateLiveData().observe(this, darkThemeState -> {
            int imageResource = (darkThemeState) ? R.drawable.btn_no : R.drawable.btn_yes;
            buttonDarkTheme.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imageResource));
        });

        settingsViewModel.getHapticFeedbackStateLiveData().observe(this, hapticFeedbackState -> {
            int imageResource = (hapticFeedbackState) ? R.drawable.btn_no : R.drawable.btn_yes;
            buttonHapticFeedBack.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imageResource));
        });
    }

    private void initViews(ActivitySettingsBinding binding) {
        buttonDarkTheme = binding.buttonDarkTheme;
        buttonHapticFeedBack = binding.buttonHapticFeedbaxk;
        buttonBack = binding.backButton;
    }
}