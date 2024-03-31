package com.gero.newpass.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> versionNameLiveData = new MutableLiveData<>();

    public void getVersion() {
        // Qui potresti inserire la logica per recuperare la versione dell'applicazione
        // Ad esempio, potresti chiamare un metodo della tua classe di utilità per ottenere la versione
        // In questo esempio, assegniamo un valore fisso per dimostrazione
        String versionName = "negro";
        versionNameLiveData.setValue(versionName);
    }

    // Metodo per ottenere la versione dell'applicazione come LiveData
    public LiveData<String> getVersionName() {
        String versionName = "1.0.0";
        versionNameLiveData.setValue(versionName);
        return versionNameLiveData;

    }
}
