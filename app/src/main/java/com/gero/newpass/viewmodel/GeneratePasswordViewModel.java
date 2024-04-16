package com.gero.newpass.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.security.SecureRandom;

public class GeneratePasswordViewModel extends ViewModel {

    private final MutableLiveData<String> passwordLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> uppercaseStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> numberStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> specialStateLiveData = new MutableLiveData<>();
    private boolean uppercase = true;
    private boolean number = true;
    private boolean special = false;
    private int length = 14;

    public GeneratePasswordViewModel() {
        generatePassword();
        uppercaseStateLiveData.setValue(uppercase);
        numberStateLiveData.setValue(number);
        specialStateLiveData.setValue(special);
    }

    public LiveData<String> getPasswordLiveData() { return passwordLiveData; }
    public LiveData<Boolean> getUppercaseStateLiveData() {
        return uppercaseStateLiveData;
    }

    public LiveData<Boolean> getNumberStateLiveData() {
        return numberStateLiveData;
    }

    public LiveData<Boolean> getSpecialStateLiveData() {
        return specialStateLiveData;
    }

    public void generatePassword() {
        String password = generateRandomPassword(length, uppercase, number, special);
        passwordLiveData.setValue(password);
    }

    public void setPasswordLength(int length) {
        this.length = length;
        generatePassword();
    }

    public void toggleUppercase() {
        uppercase = !uppercase;
        uppercaseStateLiveData.setValue(uppercase);
        generatePassword();
    }

    public void toggleNumber() {
        number = !number;
        numberStateLiveData.setValue(number);
        generatePassword();
    }

    public void toggleSpecial() {
        special = !special;
        specialStateLiveData.setValue(special);
        generatePassword();
    }

    private String generateRandomPassword(int length, boolean uppercase, boolean number, boolean special) {

        String charSet1 = (uppercase) ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "";
        String charSet2 = "abcdefghijklmnopqrstuvwxyz";
        String charSet3 = (number) ? "0123456789" : "";
        String charSet4 = (special) ? "?#%{}@!$()[]" : "";


        String characters = charSet1 + charSet2 + charSet3 + charSet4;

        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
}

