package com.gero.newpass.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.StringRes;

import com.gero.newpass.R;
import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;
import com.gero.newpass.utilities.LocaleHelper;

public class ResourceRepository {
    private final Context context;

    public ResourceRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public String getString(@StringRes int resId) {
        String currentLanguage = getCurrentLanguage();

        return LocaleHelper.getStringByLocale(context, resId, currentLanguage);
    }

    public String getCurrentLanguage() {
        return SharedPreferencesHelper.getCurrentLanguage(context);
    }
}
