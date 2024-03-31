package com.gero.newpass.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.Activities.MainActivity;
import com.gero.newpass.R;
import com.gero.newpass.databinding.ActivitySettingsBinding;
import com.gero.newpass.model.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.SettingsViewModel;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private SettingsViewModel settingsViewModel;
    private ImageButton buttonDarkTheme, buttonHapticFeedBack, buttonBack;
    private ImageView IVGithub, IVShare, IVContact;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        sharedPreferences = getSharedPreferences("Settings_Preferences", MODE_PRIVATE);

        initViews(binding);



        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });



        buttonDarkTheme.setOnClickListener(v -> settingsViewModel.toggleDarkTheme(sharedPreferences));
        buttonHapticFeedBack.setOnClickListener(v -> settingsViewModel.toggleHapticFeedback(sharedPreferences));

        settingsViewModel.getDarkThemeStateLiveData().observe(this, darkThemeState -> {
            int imageResource = (sharedPreferences.getBoolean("darkTheme", false)) ? R.drawable.btn_yes : R.drawable.btn_no;
            buttonDarkTheme.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imageResource));
        });

        settingsViewModel.getHapticFeedbackStateLiveData().observe(this, hapticFeedbackState -> {
            int imageResource = (sharedPreferences.getBoolean("hapticFeedback", false)) ? R.drawable.btn_yes : R.drawable.btn_no;
            buttonHapticFeedBack.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imageResource));
        });


    }

    private void initViews(ActivitySettingsBinding binding) {
        buttonDarkTheme = binding.buttonDarkTheme;
        buttonHapticFeedBack = binding.buttonHapticFeedbaxk;
        buttonBack = binding.backButton;
        IVGithub = binding.imageViewGithub;
        IVShare = binding.imageViewShare;
        IVContact = binding.imageViewContact;
    }
}