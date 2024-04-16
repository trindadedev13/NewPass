package com.gero.newpass.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gero.newpass.R;
import com.gero.newpass.databinding.FragmentGeneratePasswordBinding;
import com.gero.newpass.utilities.VibrationHelper;
import com.gero.newpass.view.activities.MainViewActivity;
import com.gero.newpass.viewmodel.GeneratePasswordViewModel;


public class GeneratePasswordFragment extends Fragment {

    private GeneratePasswordViewModel generatePasswordViewModel;
    private SeekBar seekBar;
    private TextView textViewLength, textViewPassword;
    private ImageButton buttonUppercase, buttonNumber, buttonSpecial;
    private ImageButton buttonRegenerate, backButton;
    private FragmentGeneratePasswordBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGeneratePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(binding);
        generatePasswordViewModel = new ViewModelProvider(this).get(GeneratePasswordViewModel.class);

        buttonRegenerate.setOnClickListener(v -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration1));
            generatePasswordViewModel.generatePassword();
        });

        generatePasswordViewModel.generatePassword();

        generatePasswordViewModel.getPasswordLiveData().observe(getViewLifecycleOwner(), newPassword -> {
            textViewPassword.setText(newPassword);
            textViewLength.setText("[" + newPassword.length() + "]");
        });


        textViewPassword.setOnClickListener(v -> {
            copyToClipboard(textViewPassword.getText().toString());
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration2));
            Toast.makeText(this.getContext(), R.string.generate_text_copied_to_clipboard, Toast.LENGTH_SHORT).show();
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                generatePasswordViewModel.setPasswordLength(progress);
                VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration0));
                textViewLength.setText("[" + progress + "]");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        buttonUppercase.setOnClickListener(v -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration0));
            generatePasswordViewModel.toggleUppercase();
        });
        buttonNumber.setOnClickListener(v -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration0));
            generatePasswordViewModel.toggleNumber();
    });
        buttonSpecial.setOnClickListener(v -> {
            VibrationHelper.vibrate(requireContext(), getResources().getInteger(R.integer.vibration_duration0));
            generatePasswordViewModel.toggleSpecial();
        });

        generatePasswordViewModel.getUppercaseStateLiveData().observe(getViewLifecycleOwner(), uppercaseState -> {
            int imageResource = (uppercaseState) ? R.drawable.btn_yes : R.drawable.btn_no;
            buttonUppercase.setImageDrawable(ContextCompat.getDrawable(this.requireActivity().getApplicationContext(), imageResource));
        });

        generatePasswordViewModel.getNumberStateLiveData().observe(getViewLifecycleOwner(), numberState -> {
            int imageResource = (numberState) ? R.drawable.btn_yes : R.drawable.btn_no;
            buttonNumber.setImageDrawable(ContextCompat.getDrawable(this.requireActivity().getApplicationContext(), imageResource));
        });

        generatePasswordViewModel.getSpecialStateLiveData().observe(getViewLifecycleOwner(), specialState -> {
            int imageResource = (specialState) ? R.drawable.btn_yes : R.drawable.btn_no;
            buttonSpecial.setImageDrawable(ContextCompat.getDrawable(this.requireActivity().getApplicationContext(), imageResource));
        });

        Activity activity = this.getActivity();

        backButton.setOnClickListener(v -> {
            if (activity instanceof MainViewActivity) {
                ((MainViewActivity) activity).onBackPressed();
            }
        });
    }

    private void copyToClipboard(String text) {

        ClipboardManager clipboardManager = (ClipboardManager) this.requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(getString(R.string.text_copied_to_clipboard), text);
        clipboardManager.setPrimaryClip(clipData);
    }


    private void initViews(FragmentGeneratePasswordBinding binding) {
        seekBar = binding.seekBar;
        textViewLength = binding.textViewLenghtValue;
        textViewPassword = binding.textViewPassword;
        buttonUppercase = binding.buttonUppercase1;
        buttonNumber = binding.buttonNumber1;
        buttonSpecial = binding.buttonSpecial1;
        buttonRegenerate = binding.buttonRegenerate;
        backButton = binding.backButton;
    }
}