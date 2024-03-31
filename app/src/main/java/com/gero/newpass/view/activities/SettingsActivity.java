package com.gero.newpass.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.Activities.MainActivity;
import com.gero.newpass.R;
import com.gero.newpass.databinding.ActivitySettingsBinding;
import com.gero.newpass.model.SettingsItem;
import com.gero.newpass.model.utilities.SystemBarColorHelper;
import com.gero.newpass.view.adapters.SettingsAdapter;
import com.gero.newpass.viewmodel.SettingsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private final String[] settingsList = {"Dark Theme", "Haptic Feedback", "GitHub Repository", "Share NewPass", "Contact Me"};
    private final int[] iconList = {R.drawable.settings_icon_dark_theme, R.drawable.settings_icon_haptic_feedback, R.drawable.settings_icon_github, R.drawable.settings_icon_share, R.drawable.settings_icon_telegram};
    ListView listView;
    TextView textViewVersion;
    private ImageButton buttonBack;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        initViews(binding);

        SettingsAdapter settingsAdapter = new SettingsAdapter(getApplicationContext(), settingsList, iconList);
        listView.setAdapter(settingsAdapter);


        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            textViewVersion.setText("[NewPass v" + version + "]");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        initViews(binding);

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initViews(ActivitySettingsBinding binding) {
        listView = binding.customListView;
        textViewVersion = binding.version;
        buttonBack = binding.backButton;

    }
}