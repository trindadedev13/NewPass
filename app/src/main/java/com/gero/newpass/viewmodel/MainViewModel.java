package com.gero.newpass.viewmodel;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gero.newpass.model.UserData;
import com.gero.newpass.database.DatabaseHelper;
import com.gero.newpass.database.DatabaseServiceLocator;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserData>> userDataList, searchedDataList;
    private final DatabaseHelper myDB;

    public MainViewModel() {
        myDB = DatabaseServiceLocator.getDatabaseHelper();
    }

    public void storeDataInArrays() {
        userDataList = new MutableLiveData<>();
        ArrayList<UserData> localList = new ArrayList<>();
        Cursor cursor = myDB.readAllData();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                UserData userData = new UserData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                localList.add(userData);
            }
        }
        userDataList.postValue(localList);
    }

    public void storeSearchedDataInArrays(String searchedData) {
        searchedDataList = new MutableLiveData<>();
        ArrayList<UserData> localList = new ArrayList<>();
        Cursor cursor = myDB.searchItem(searchedData);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                UserData userData = new UserData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );

                localList.add(userData);
            }
        }
        searchedDataList.postValue(localList);
    }

    public LiveData<ArrayList<UserData>> getSearchedDataList() {
        return searchedDataList;
    }

    public LiveData<ArrayList<UserData>> getUserDataList() {
        return userDataList;
    }
}