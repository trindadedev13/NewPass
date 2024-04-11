package com.gero.newpass.repository;

import android.content.Context;

import androidx.annotation.StringRes;

public class ResourceRepository {
    private final Context context;

    public ResourceRepository(Context context) {
        this.context = context.getApplicationContext(); // Usa il contesto dell'applicazione per evitare memory leaks
    }

    public String getString(@StringRes int resId) {
        return context.getString(resId);
    }
}
