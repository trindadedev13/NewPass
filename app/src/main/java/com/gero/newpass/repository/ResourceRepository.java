package com.gero.newpass.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.StringRes;

import com.gero.newpass.SharedPreferences.SharedPreferencesHelper;

public class ResourceRepository {
    private final Context context;

    public ResourceRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public String getString(@StringRes int resId) {
        String currentLanguage = getCurrentLanguage();

        Log.i("235234", currentLanguage);

        String resourceFileName = "strings";

        if ("en".equals(currentLanguage)) {
            resourceFileName = "strings-en";
        }
        if ("it".equals(currentLanguage)) {
            resourceFileName = "strings-it";
        }
        if ("es".equals(currentLanguage)) {
            resourceFileName = "strings-es";
        }
        if ("fr".equals(currentLanguage)) {
            resourceFileName = "strings-fr";
        }

        Log.i("235234", resourceFileName);

        String resourceName = context.getResources().getResourceEntryName(resId); // login_done

        Log.i("235234", resourceName);

        int resourceId = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());

        Log.i("235234", context.getResources().getString(resourceId));

        return context.getResources().getString(resourceId);
    }

    public String getCurrentLanguage() {
        return SharedPreferencesHelper.getCurrentLanguage(context);
    }
}
