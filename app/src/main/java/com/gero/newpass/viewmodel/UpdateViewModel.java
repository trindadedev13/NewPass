package com.gero.newpass.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gero.newpass.model.database.DatabaseHelper;
import com.gero.newpass.model.database.DatabaseServiceLocator;
import com.gero.newpass.model.encryption.EncryptionHelper;

public class UpdateViewModel extends ViewModel {

    private final DatabaseHelper databaseHelper;
    private final MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public UpdateViewModel() {
        databaseHelper = DatabaseServiceLocator.getDatabaseHelper();
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public void updateEntry(String entry, String name, String email, String password) {

        String encryptedPassword = EncryptionHelper.encrypt(password);

        int nameMaxLen = 30;
        int emailMaxLen = 30;
        int passwordMaxLen = 30;

        if (
                !name.isEmpty() && name.length() <= nameMaxLen &&
                        email.length() >= 4 && email.length() <= emailMaxLen &&
                        password.length() >= 4 && password.length() <= passwordMaxLen
        ) {
            databaseHelper.updateData(entry, name, email, encryptedPassword);

        } else {
            if (name.isEmpty() || name.length() > nameMaxLen) {
                messageLiveData.setValue("Name should be 1 to "+ nameMaxLen +" characters long!");

            } else if (email.length() < 4 || email.length() > emailMaxLen) {
                messageLiveData.setValue("Email should be 4 to " + emailMaxLen +" characters long!");

            } else {
                messageLiveData.setValue("Password should be 4 to " + passwordMaxLen + " characters long!");
            }
        }
    }

    public void deleteEntry(String entry) {
        databaseHelper.deleteOneRow(entry);
    }
}
