package com.gero.newpass.utilities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocaleHelper {

    public static void updateLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context.createConfigurationContext(config);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static String getStringByLocale(Context context, int resId, String language) {
        Locale locale = new Locale(language);
        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);
        Context localizedContext = context.createConfigurationContext(config);
        return localizedContext.getResources().getString(resId);
    }
}
