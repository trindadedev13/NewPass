package com.gero.newpass.viewmodel;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gero.newpass.model.UserData;
import com.gero.newpass.model.database.DatabaseHelper;
import com.gero.newpass.model.database.DatabaseServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<UserData>> userDataList;

    public LiveData<List<UserData>> getUserDataList() {
        if (userDataList == null) {
            userDataList = new MutableLiveData<>();
            loadUserDataList(); // Carica i dati degli utenti al primo accesso
        }
        return userDataList;
    }

    private void loadUserDataList() {
        // Carica i dati degli utenti dal database o da altre sorgenti
        List<UserData> dataList = new ArrayList<>();
        DatabaseHelper myDB = DatabaseServiceLocator.getDatabaseHelper();
        Cursor cursor = myDB.readAllData();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                UserData userData = new UserData(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                dataList.add(userData);
            }
            cursor.close();
        }

        // Aggiorna il LiveData con i nuovi dati
        userDataList.setValue(dataList);
    }
}
