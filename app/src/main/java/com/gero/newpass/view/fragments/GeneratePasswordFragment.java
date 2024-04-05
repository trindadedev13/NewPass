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
import androidx.lifecycle.Observer;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGeneratePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(binding);
        generatePasswordViewModel = new ViewModelProvider(this).get(GeneratePasswordViewModel.class);

        buttonRegenerate.setOnClickListener(v -> generatePasswordViewModel.generatePassword());

        generatePasswordViewModel.generatePassword();

        generatePasswordViewModel.getPasswordLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String newPassword) {
                textViewPassword.setText(newPassword);
                textViewLength.setText("[" + String.valueOf(newPassword.length()) + "]");
            }
        });


        textViewPassword.setOnClickListener(v -> {
            copyToClipboard(textViewPassword.getText().toString());
            Toast.makeText(this.getContext(), "Text copied to clipboard", Toast.LENGTH_SHORT).show();
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                generatePasswordViewModel.setPasswordLength(progress);

                textViewLength.setText("[" + String.valueOf(progress) + "]");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        buttonUppercase.setOnClickListener(v -> generatePasswordViewModel.toggleUppercase());
        buttonNumber.setOnClickListener(v -> generatePasswordViewModel.toggleNumber());
        buttonSpecial.setOnClickListener(v -> generatePasswordViewModel.toggleSpecial());

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
        ClipData clipData = ClipData.newPlainText("Text copied to clipboard", text);
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