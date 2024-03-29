package com.gero.newpass.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gero.newpass.Activities.MainActivity;
import com.gero.newpass.R;
import com.gero.newpass.databinding.ActivityAddBinding;
import com.gero.newpass.model.utilities.SystemBarColorHelper;
import com.gero.newpass.viewmodel.AddViewModel;

public class AddActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private AddViewModel addViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddBinding binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SystemBarColorHelper.changeBarsColor(this, R.color.background_primary);

        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        nameInput = binding.nameInput;
        emailInput = binding.emailInput;
        passwordInput = binding.passwordInput;
        ImageButton add_button = binding.addButton;
        ImageButton back_button = binding.backButton;

        add_button.setOnClickListener(v -> {

            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            addViewModel.addEntry(name, email, password);
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        back_button.setOnClickListener(v -> {
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        // Observe any feedback messages from the ViewModel
        addViewModel.getMessageLiveData().observe(this, message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}