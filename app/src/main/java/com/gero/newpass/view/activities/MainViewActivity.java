package com.gero.newpass.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.widget.Toast;


import com.gero.newpass.ContextWrapper.NewPassContextWrapper;
import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;
import com.gero.newpass.database.DatabaseServiceLocator;
import com.gero.newpass.databinding.ActivityMainViewBinding;

import com.gero.newpass.R;
import com.gero.newpass.utilities.SystemBarColorHelper;
import com.gero.newpass.view.fragments.LanguageDialogFragment;
import com.gero.newpass.view.fragments.MainViewFragment;

import java.util.Locale;

public class MainViewActivity extends AppCompatActivity implements LanguageDialogFragment.LanguageListener {

    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.gero.newpass.databinding.ActivityMainViewBinding binding = ActivityMainViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseServiceLocator.init(getApplicationContext());

        if (checkPermissions()) {
            Log.e("32890457", "Permission already granted...");
            //...
        } else {
            Log.e("32890457", "Permission was not granted, request...");
            askPermissions();
        }

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
        SharedPreferencesHelper.toggleDarkLightModeUI(this);
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

    @Override
    protected void attachBaseContext(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesHelper.SHARED_PREF_FLAG, MODE_PRIVATE);
        String language = sharedPreferences.getString(SharedPreferencesHelper.LANG_PREF_FLAG, "en");

        super.attachBaseContext(NewPassContextWrapper.wrap(context, language));

        Locale locale = new Locale(language);
        Resources resources = getBaseContext().getResources();
        Configuration conf = resources.getConfiguration();

        conf.setLocale(locale);
        resources.updateConfiguration(conf, resources.getDisplayMetrics());
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        String selectedLanguage = list[position];
        SharedPreferencesHelper.setLanguage(this, selectedLanguage);
    }

    @Override
    public void onNegativeButtonClicked() { }

    private void askPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Log.d("32890457", "aksPermission: try");
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);

            } catch (Exception e) {
                Log.e("32890457", "aksPermission: catch", e);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);
            }
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Log.e("32890457", "onActivityResult: ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (Environment.isExternalStorageManager()) {
                            Log.e("32890457", "onActivityResult: Manage External Storage is granted");
                            //...
                        } else {
                            Log.e("32890457", "onActivityResult: Manage External Storage is denied");
                            Toast.makeText(MainViewActivity.this, "onActivityResult: Manage External Storage is denied", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                    }
                }
            }

    );

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (write && read) {
                    Log.e("32890457", "onRequestPermissionsResult: External Storage permission granted");
                    //...
                } else {
                    Log.e("32890457", "onRequestPermissionsResult: External Storage permission denied");
                    Toast.makeText(MainViewActivity.this, "External Storage permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}