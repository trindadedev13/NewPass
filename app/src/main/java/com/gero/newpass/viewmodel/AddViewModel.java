package com.gero.newpass.viewmodel;

import com.gero.newpass.database.DatabaseHelper;
import com.gero.newpass.database.DatabaseServiceLocator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {

    private final DatabaseHelper databaseHelper;
    private final MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public AddViewModel() {
        databaseHelper = DatabaseServiceLocator.getDatabaseHelper();
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public void addEntry(String name, String email, String password) {

        int NAME_MAX_LENGTH = 30;
        int EMAIL_MAX_LENGTH = 30;
        int PASSWORD_MAX_LENGTH = 30;

        if (
                !name.isEmpty() && name.length() <= NAME_MAX_LENGTH &&
                        email.length() >= 4 && email.length() <= EMAIL_MAX_LENGTH &&
                        password.length() >= 4 && password.length() <= PASSWORD_MAX_LENGTH
            ) {

                if (databaseHelper.checkIfAccountAlreadyExist(name, email)) {
                    messageLiveData.setValue("This account already exists!");

                } else  {
                    databaseHelper.addEntry(name, email, password);
                    messageLiveData.setValue("Account added successfully");
                }

            } else {
                if (name.isEmpty() || name.length() > NAME_MAX_LENGTH) {
                    messageLiveData.setValue("Name should be 1 to "+ NAME_MAX_LENGTH +" characters long!");

                } else if (email.length() < 4 || email.length() > EMAIL_MAX_LENGTH) {
                    messageLiveData.setValue("Email should be 4 to " + EMAIL_MAX_LENGTH +" characters long!");

                } else {
                    messageLiveData.setValue("Password should be 4 to " + PASSWORD_MAX_LENGTH + " characters long!");
                }
            }
    }
}
