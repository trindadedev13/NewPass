package com.gero.newpass.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gero.newpass.R;
import com.gero.newpass.databinding.FragmentSettingsBinding;
import com.gero.newpass.view.activities.MainViewActivity;


public class SettingsFragment extends Fragment {
    private ImageButton buttonBack;
    private ImageView IVGithub, IVShare, IVContact;
    private FragmentSettingsBinding binding;

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

        buttonBack.setOnClickListener(v -> {
            if (activity instanceof MainViewActivity) {
                ((MainViewActivity) activity).onBackPressed();
            }
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_share_text));
            startActivity(Intent.createChooser(shareIntent, "Share with..."));
        });

        IVContact.setOnClickListener(v -> {
            String url = "https://t.me/geroED";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }

    private void initViews(FragmentSettingsBinding binding) {
        buttonBack = binding.backButton;
        IVGithub = binding.imageViewGithub;
        IVShare = binding.imageViewShare;
        IVContact = binding.imageViewContact;
    }
}