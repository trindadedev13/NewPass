package com.gero.newpass.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gero.newpass.R;
import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;

import java.util.Objects;

public class LanguageDialogFragment extends DialogFragment {
    int position;

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

        String currentLanguage = SharedPreferencesHelper.getCurrentLanguage(requireContext());
        String[] languageList = requireActivity().getResources().getStringArray(R.array.language_options);

        position = getLanguagePosition(languageList, currentLanguage);

        //Log.i("2353542", "array: " + Arrays.toString(languageList) + " currentLanguage: " + currentLanguage + " at posiztion:" + position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select your choice")
                .setSingleChoiceItems(languageList, position, (dialog, which) -> position = which)
                .setPositiveButton("Ok", (dialog, which) -> mListener.onPositiveButtonClicked(languageList, position))
                .setNegativeButton("Cancel", (dialog, which) -> mListener.onNegativeButtonClicked());

        return builder.create();
    }

    private int getLanguagePosition(String[] languageList, String currentLanguage) {

        for (int i = 0; i < languageList.length; i++) {
            if (Objects.equals(languageList[i].toLowerCase().substring(0, 2), currentLanguage)) {
                position = i;
                break;
            }
        }
        return position;
    }
}
