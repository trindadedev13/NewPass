package com.gero.newpass.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gero.newpass.R;

public class LanguageDialogFragment extends DialogFragment {
    int position = 1;  // TODO: change the default position to the current language

    public interface LanguageListener {
        void onPositiveButtonClicked(String[] list, int position);
        void onNegativeButtonClicked();
    }

    LanguageListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (LanguageListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity() + "LanguageListener must be implemented!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] list = requireActivity().getResources().getStringArray(R.array.language_options);

        builder.setTitle("Select your choice")
                .setSingleChoiceItems(list, position, (dialog, which) -> position = which)
                .setPositiveButton("Ok", (dialog, which) -> mListener.onPositiveButtonClicked(list, position))
                .setNegativeButton("Cancel", (dialog, which) -> mListener.onNegativeButtonClicked());

        return builder.create();
    }
}
