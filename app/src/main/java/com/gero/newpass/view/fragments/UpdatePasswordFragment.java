package com.gero.newpass.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.databinding.FragmentUpdatePasswordBinding;
import com.gero.newpass.encryption.EncryptionHelper;

import com.gero.newpass.view.activities.MainViewActivity;
import com.gero.newpass.viewmodel.UpdateViewModel;


public class UpdatePasswordFragment extends Fragment {


    private FragmentUpdatePasswordBinding binding;

    private EditText name_input, email_input, password_input;
    private String entry, name, email, password;
    private UpdateViewModel updateViewModel;
    private ImageButton updateButton, deleteButton, copyButtonPassword, copyButtonEmail, backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAndSetIntentData();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        updateViewModel = new ViewModelProvider(this).get(UpdateViewModel.class);

        initViews(binding);

        String decryptedPassword = EncryptionHelper.decrypt(password);

        name_input.setText(name);
        email_input.setText(email);
        password_input.setText(decryptedPassword);

        // Observe any feedback messages from the ViewModel
        updateViewModel.getMessageLiveData().observe(getViewLifecycleOwner(), message ->
                Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show());

        updateButton.setOnClickListener(v -> {

            name = name_input.getText().toString().trim();
            email = email_input.getText().toString().trim();
            password = password_input.getText().toString().trim();

            updateViewModel.updateEntry(entry, name, email, password);
        });

        Activity activity = this.getActivity();

        deleteButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Delete " + name + " ?");
            builder.setMessage("Are you sure you want to delete " + name + " ?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                updateViewModel.deleteEntry(entry);
                if (activity instanceof MainViewActivity) {
                    Bundle result = new Bundle();
                    //Result key for the main fragment to update the deletion
                    result.putString("resultKey", "1");
                    getParentFragmentManager().setFragmentResult("requestKey", result);
                    ((MainViewActivity) activity).onBackPressed();
                }
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {

            });
            builder.create().show();

        });

        copyButtonPassword.setOnClickListener(v -> {
            copyToClipboard(password_input.getText().toString().trim());
            Toast.makeText(this.getContext(), "Password copied to the clipboard", Toast.LENGTH_SHORT).show();
        });

        copyButtonEmail.setOnClickListener(v -> {
            copyToClipboard(email_input.getText().toString().trim());
            Toast.makeText(this.getContext(), "Email copied to the clipboard", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> {
            if (activity instanceof MainViewActivity) {
                Bundle result = new Bundle();
                result.putString("resultKey", "1");
                getParentFragmentManager().setFragmentResult("requestKey", result);
                ((MainViewActivity) activity).onBackPressed();
            }
        });
    }

    private void getAndSetIntentData() {
        Bundle args = getArguments();
        if (args != null && args.containsKey("entry") && args.containsKey("name") &&
                args.containsKey("email") && args.containsKey("password")) {
            entry = args.getString("entry");
            name = args.getString("name");
            email = args.getString("email");
            password = args.getString("password");
        } else {
            Toast.makeText(this.getContext(), "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method for copying text to the clipboard
     *
     * @param text text to copy to the clipboard
     */
    private void copyToClipboard(String text) {

        ClipboardManager clipboardManager = (ClipboardManager) this.requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Text copied to the clipboard", text);
        clipboardManager.setPrimaryClip(clipData);
    }

    private void initViews(FragmentUpdatePasswordBinding binding) {
        name_input = binding.nameInput2;
        email_input = binding.emailInput2;
        password_input = binding.passwordInput2;
        updateButton = binding.updateButton;
        backButton = binding.backButton;
        deleteButton = binding.deleteButton;
        copyButtonPassword = binding.copyButtonPassword;
        copyButtonEmail = binding.copyButtonEmail;
    }
}
