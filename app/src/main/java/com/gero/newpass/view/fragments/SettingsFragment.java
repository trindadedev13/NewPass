package com.gero.newpass.view.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gero.newpass.R;
import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;
import com.gero.newpass.database.DatabaseServiceLocator;
import com.gero.newpass.databinding.FragmentSettingsBinding;
import com.gero.newpass.utilities.VibrationHelper;
import com.gero.newpass.view.activities.MainViewActivity;

public class SettingsFragment extends Fragment {
    private ImageButton buttonBack;
    private ImageView IVGithub, IVShare, IVContact, IVLanguage, IVExport, IVImport;
    private FragmentSettingsBinding binding;
    private Boolean isDarkModeSet;
    private static final int PICK_FILE_REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(binding);

        Activity activity = this.getActivity();

        //final SharedPreferences.Editor editor = sharedPreferences.edit();

        buttonBack.setOnClickListener(v -> {
            if (activity instanceof MainViewActivity) {
                ((MainViewActivity) activity).onBackPressed();
            }
        });

        IVGithub.setOnClickListener(v -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));
            String url = "https://github.com/6eero/NewPass";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        IVShare.setOnClickListener(v -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_share_text));
            startActivity(Intent.createChooser(shareIntent, "Share with..."));
        });

        IVContact.setOnClickListener(v -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));
            String url = "https://t.me/geroED";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });


        binding.toggleDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));
            if (isChecked && !isDarkModeSet) {
                SharedPreferencesHelper.setAndEditSharedPrefForDarkMode(
                        this.requireActivity().getApplicationContext());
            } else if (!isChecked && isDarkModeSet) {
                SharedPreferencesHelper.setAndEditSharedPrefForLightMode(
                        this.requireActivity().getApplicationContext());
            }
            if (activity instanceof MainViewActivity) {
                SharedPreferencesHelper.updateNavigationBarColor(isChecked, activity);
            }
        });

        IVLanguage.setOnClickListener(v -> {

            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));

            DialogFragment languageDialogFragment = new LanguageDialogFragment();
            languageDialogFragment.setCancelable(false);
            languageDialogFragment.show(requireActivity().getSupportFragmentManager(), "Language Dialog");
        });

        IVExport.setOnClickListener(v -> {

            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));

            //TODO: export database -> open file manager exporting db as "password.db" and let the user saves it
            Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_LONG).show();
        });

        IVImport.setOnClickListener(v -> {

            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));

            //TODO: import database -> open file manager and let the user pick a .db

//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST_CODE);
              Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_LONG).show();


        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedFileUri = data.getData();
                Log.i("23895", "file caricato: " + selectedFileUri);
                Log.i("23895", "db: " + DatabaseServiceLocator.getDatabaseHelper());
                //DatabaseServiceLocator.setDatabaseHelper(selectedFileUri);

            }
        }
    }

    private void initViews(FragmentSettingsBinding binding) {
        buttonBack = binding.backButton;
        IVGithub = binding.imageViewGithub;
        IVShare = binding.imageViewShare;
        IVContact = binding.imageViewContact;
        IVLanguage = binding.imageViewLanguage;
        IVExport = binding.imageViewExport;
        IVImport = binding.imageViewImport;

        isDarkModeSet = SharedPreferencesHelper.isDarkModeSet(this.requireActivity().getApplicationContext());

        binding.toggleDarkMode.setChecked(isDarkModeSet);
    }
}