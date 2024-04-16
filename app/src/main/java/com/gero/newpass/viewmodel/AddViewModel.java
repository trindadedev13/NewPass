package com.gero.newpass.viewmodel;

import android.util.Log;

import com.gero.newpass.R;
import com.gero.newpass.database.DatabaseHelper;
import com.gero.newpass.database.DatabaseServiceLocator;
import com.gero.newpass.repository.ResourceRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {

    private final DatabaseHelper databaseHelper;
    private final MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();
    private final ResourceRepository resourceRepository;

    public AddViewModel(ResourceRepository resourceRepository) {
        this.databaseHelper = DatabaseServiceLocator.getDatabaseHelper();
        this.resourceRepository = resourceRepository;
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public LiveData<Boolean> getSuccessLiveData() {
        return successLiveData;
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
                    messageLiveData.setValue(resourceRepository.getString(R.string.this_account_already_exists));
                    successLiveData.setValue(false);

                } else  {
                    databaseHelper.addEntry(name, email, password);
                    messageLiveData.setValue(resourceRepository.getString(R.string.account_added_successfully));
                    successLiveData.setValue(true);
                }

            } else {
                successLiveData.setValue(false);
                if (name.isEmpty() || name.length() > NAME_MAX_LENGTH) {
                    messageLiveData.setValue(resourceRepository.getString(R.string.name_should_be_1_to)+ NAME_MAX_LENGTH + resourceRepository.getString(R.string.characters_long));

                } else if (email.length() < 4 || email.length() > EMAIL_MAX_LENGTH) {
                    messageLiveData.setValue(resourceRepository.getString(R.string.email_should_be_4_to) + EMAIL_MAX_LENGTH + resourceRepository.getString(R.string.characters_long));

                } else {
                    messageLiveData.setValue(resourceRepository.getString(R.string.password_should_be_4_to) + PASSWORD_MAX_LENGTH + resourceRepository.getString(R.string.characters_long));
                }
            }
    }
}
