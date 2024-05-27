package com.gero.newpass.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gero.newpass.R;
import com.gero.newpass.database.DatabaseHelper;
import com.gero.newpass.database.DatabaseServiceLocator;
import com.gero.newpass.encryption.EncryptionHelper;
import com.gero.newpass.repository.ResourceRepository;

public class UpdateViewModel extends ViewModel {

    private final DatabaseHelper databaseHelper;
    private final MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successUpdateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successDeleteLiveData = new MutableLiveData<>();
    private final ResourceRepository resourceRepository;

    public UpdateViewModel(ResourceRepository resourceRepository) {
        this.databaseHelper = DatabaseServiceLocator.getDatabaseHelper();
        this.resourceRepository = resourceRepository;
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }
    public LiveData<Boolean> getSuccessUpdateLiveData() {
        return successUpdateLiveData;
    }

    public void updateEntry(String entry, String name, String email, String password) {

        String encryptedPassword = EncryptionHelper.encrypt(password);

        if (!name.isEmpty() && !email.isEmpty() && password.length() >= 4) {
            databaseHelper.updateData(entry, name, email, encryptedPassword);
            messageLiveData.setValue(resourceRepository.getString(R.string.dbhelper_updated_successfully));
            successUpdateLiveData.setValue(true);

        } else {
            successUpdateLiveData.setValue(false);

            if (name.isEmpty()) {
                messageLiveData.setValue(resourceRepository.getString(R.string.name_should_not_be_empty));

            } else if (email.isEmpty()) {
                messageLiveData.setValue(resourceRepository.getString(R.string.email_should_not_be_empty));

            } else {
                messageLiveData.setValue(resourceRepository.getString(R.string.password_must_be_at_least_4_characters_long));
            }
        }
    }

    public void deleteEntry(String entry) {
        databaseHelper.deleteOneRow(entry);
        messageLiveData.setValue(resourceRepository.getString(R.string.dbhelper_successfully_deleted));
        successDeleteLiveData.setValue(true);
    }
}
