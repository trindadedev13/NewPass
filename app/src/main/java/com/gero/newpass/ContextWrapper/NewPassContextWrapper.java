package com.gero.newpass.ContextWrapper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class NewPassContextWrapper extends ContextWrapper {

    public NewPassContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context, String language) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }
        if (!language.isEmpty() && !sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            setSystemLocale(config, locale);

        }
        context = context.createConfigurationContext(config);
        return new NewPassContextWrapper(context);
    }

    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }
}
