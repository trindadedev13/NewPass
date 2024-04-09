package com.gero.newpass.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;


import com.gero.newpass.database.DatabaseServiceLocator;
import com.gero.newpass.databinding.ActivityMainViewBinding;

import com.gero.newpass.R;
import com.gero.newpass.encryption.EncryptionHelper;
import com.gero.newpass.utilities.SystemBarColorHelper;
import com.gero.newpass.view.fragments.MainViewFragment;

public class MainViewActivity extends AppCompatActivity {

    private ActivityMainViewBinding binding;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseServiceLocator.init(getApplicationContext());

        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        // Initial fragment setup, showing the 'AddFragment' by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainViewFragment())
                    .commit();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = EncryptionHelper.getSharedPreferences(this.getApplicationContext());
        //Checking if dark mode is set or not from shared  preferences, default value being True
        Boolean isDarkModeSet = sharedPreferences.getBoolean("isDarkModeOn", true);
        if (isDarkModeSet) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }
        updateNavigationBarColor(isDarkModeSet);
    }

    public void openFragment(Fragment fragment) {

        // Perform the fragment transaction and add it to the back stack
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        // If the fragment stack has more than one entry, pop the back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            // Otherwise, defer to the system default behavior
            super.onBackPressed();
        }
    }

    public void updateNavigationBarColor(Boolean isDarkMode){
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

}