package com.gero.newpass.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.R;
import com.gero.newpass.databinding.ActivitySettingsBinding;
import com.gero.newpass.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.SettingsViewModel;

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



        IVGithub.setOnClickListener(v -> {
            String url = "https://github.com/6eero/NewPass";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        IVShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Unlock a world of security with NewPass – your ultimate password guardian. Explore it now: https://github.com/6eero/NewPass/releases");
            startActivity(Intent.createChooser(shareIntent, "Share with..."));
        });

        IVContact.setOnClickListener(v -> {
            String url = "https://t.me/geroED";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
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