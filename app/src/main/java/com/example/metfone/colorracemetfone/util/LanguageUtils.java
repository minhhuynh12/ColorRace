package com.example.metfone.colorracemetfone.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageUtils {
    public static void setLanguage(Context context, String language) {
        Locale locale = new Locale(language.toLowerCase());
        locale.setDefault(new Locale("en_US"));
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
