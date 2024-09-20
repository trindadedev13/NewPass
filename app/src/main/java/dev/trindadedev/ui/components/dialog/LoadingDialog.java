package dev.trindadedev.ui.components.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.loadingindicator.LoadingIndicator;

import com.geero.newpass.databinding.DialogLoadingBinding;

public class LoadingScreen extends Dialog {

    private DialogLoadingBinding binding;
    private Context c;

    public LoadingScreen(Context context) {
        super(context);
        c = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
