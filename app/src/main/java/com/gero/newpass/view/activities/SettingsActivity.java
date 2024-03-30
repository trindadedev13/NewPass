package com.gero.newpass.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.R;
import com.gero.newpass.databinding.ActivitySettingsBinding;
import com.gero.newpass.model.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    SettingsViewModel settingsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        initViews(binding);
    }

    private void initViews(ActivitySettingsBinding binding) {
    }
}