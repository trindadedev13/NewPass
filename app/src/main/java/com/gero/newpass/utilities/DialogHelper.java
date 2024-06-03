package com.gero.newpass.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;

import com.gero.newpass.R;
import com.gero.newpass.database.DatabaseHelper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DialogHelper {

    public static void showChangePasswordDialog(Context context, EncryptedSharedPreferences encryptedSharedPreferences) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        builder.setView(dialogView);

        EditText firstInput = dialogView.findViewById(R.id.first_input);
        EditText secondInput = dialogView.findViewById(R.id.second_input);
        EditText thirdInput = dialogView.findViewById(R.id.third_input);

        builder.setTitle(R.string.settings_change_password)
                .setPositiveButton(R.string.update_alertdialog_yes, (dialog, id) -> {
                    String inputOne = firstInput.getText().toString();
                    String inputTwo = secondInput.getText().toString();
                    String inputThree = thirdInput.getText().toString();

                    if (inputOne.equals(encryptedSharedPreferences.getString("password", "")) && inputTwo.length() >= 4 && inputTwo.equals(inputThree)) {
                        SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
                        editor.putString("password", inputTwo);
                        editor.apply();

                        DatabaseHelper.changeDBPassword(inputTwo, context);
                    } else if (inputTwo.length() < 4) {
                        Toast.makeText(context, R.string.password_must_be_at_least_4_characters_long, Toast.LENGTH_SHORT).show();
                    } else if (!inputTwo.equals(inputThree)) {
                        Toast.makeText(context, R.string.passwords_do_not_match, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.wrong_password, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.update_alertdialog_no, (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showExportingDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_export_or_import_db, null);
        builder.setView(dialogView);
        EditText input = dialogView.findViewById(R.id.input);

        builder.setTitle(R.string.export_database)
                .setPositiveButton(R.string.confirm, (dialog, id) -> {
                    String password = input.getText().toString();

                    if (password.isEmpty()) {
                        Toast.makeText(context, context.getString(R.string.password_cannot_be_empty), Toast.LENGTH_LONG).show();
                    } else {
                        DatabaseHelper.exportDatabaseToJson(context, password);
                    }

                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showImportingDialog(Context context, Uri fileURL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_export_or_import_db, null);
        builder.setView(dialogView);
        EditText input = dialogView.findViewById(R.id.input);

        builder.setTitle(R.string.import_database)
                .setPositiveButton(R.string.confirm, (dialog, id) -> {
                    String password = input.getText().toString();
                    try {
                        DatabaseHelper.importJsonToDatabase(context, fileURL, password);
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        Log.e("8953467", "Error: ", e);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
