package com.gero.newpass.viewmodel;

import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.security.SecureRandom;

public class GeneratePasswordViewModel extends ViewModel {

    private final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private final String NUMBERS = "0123456789";
    private final String SPECIALS = "?#%{}@!$()[]";
    private final MutableLiveData<String> passwordLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> uppercaseStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lowercaseStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> numberStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> specialStateLiveData = new MutableLiveData<>();
    private boolean uppercase = true;
    private boolean lowercase = true;
    private boolean number = true;
    private boolean special = false;
    private int length = 12;

    public GeneratePasswordViewModel() {
        generatePassword();
        uppercaseStateLiveData.setValue(uppercase);
        lowercaseStateLiveData.setValue(lowercase);
        numberStateLiveData.setValue(number);
        specialStateLiveData.setValue(special);
    }

    public LiveData<String> getPasswordLiveData() { return passwordLiveData; }
    public LiveData<Boolean> getUppercaseStateLiveData() {
        return uppercaseStateLiveData;
    }
    public LiveData<Boolean> getLowercaseStateLiveData() {
        return lowercaseStateLiveData;
    }
    public LiveData<Boolean> getNumberStateLiveData() {
        return numberStateLiveData;
    }
    public LiveData<Boolean> getSpecialStateLiveData() {
        return specialStateLiveData;
    }

    public void generatePassword() {
        String password = generateRandomPassword(length, uppercase, lowercase, number, special);
        passwordLiveData.setValue(password);
    }

    public void setPasswordLength(int length) {
        this.length = length;
        generatePassword();
    }

    public int toggleUppercase(int optionsPerPosition) {
        //Log.w("8953467", "entropy in ingresso: " + entropy);
        uppercase = !uppercase;
        uppercaseStateLiveData.setValue(uppercase);
        generatePassword();

        if (uppercase) {
            optionsPerPosition = optionsPerPosition + UPPERCASE.length();
        } else {
            optionsPerPosition = optionsPerPosition - UPPERCASE.length();
        }
        Log.w("8953467", "entropy in uscita: " + optionsPerPosition);
        return optionsPerPosition;
    }

    public int toggleLowercase(int optionsPerPosition) {
        //Log.w("8953467", "entropy in ingresso: " + entropy);
        lowercase = !lowercase;
        lowercaseStateLiveData.setValue(lowercase);
        generatePassword();

        if (lowercase) {
            optionsPerPosition = optionsPerPosition + LOWERCASE.length();
        } else {
            optionsPerPosition = optionsPerPosition - LOWERCASE.length();
        }
        Log.w("8953467", "entropy in uscita: " + optionsPerPosition);
        return optionsPerPosition;
    }

    public int toggleNumber(int optionsPerPosition) {
        //Log.w("8953467", "optionsPerPosition in ingresso: " + optionsPerPosition);
        number = !number;
        numberStateLiveData.setValue(number);
        generatePassword();

        if (number) {
            optionsPerPosition = optionsPerPosition + NUMBERS.length();
        } else {
            optionsPerPosition = optionsPerPosition - NUMBERS.length();
        }
        Log.w("8953467", "optionsPerPosition in uscita: " + optionsPerPosition);
        return optionsPerPosition;
    }

    public int toggleSpecial(int optionsPerPosition) {
        //Log.w("8953467", "optionsPerPosition in ingresso: " + optionsPerPosition);
        special = !special;
        specialStateLiveData.setValue(special);
        generatePassword();

        if (special) {
            optionsPerPosition = optionsPerPosition + SPECIALS.length();
        } else {
            optionsPerPosition = optionsPerPosition - SPECIALS.length();
        }
        Log.w("8953467", "optionsPerPosition in uscita: " + optionsPerPosition);
        return optionsPerPosition;
    }

    private String generateRandomPassword(int length, boolean uppercase,  boolean lowercase, boolean number, boolean special) {

        String charSet1 = (uppercase) ? UPPERCASE : "";
        String charSet2 = (lowercase) ? LOWERCASE : "";
        String charSet3 = (number) ? NUMBERS : "";
        String charSet4 = (special) ? SPECIALS : "";


        String characters = charSet1 + charSet2 + charSet3 + charSet4;

        if (characters.isEmpty()) {
            characters = " ";
        }

        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
}

