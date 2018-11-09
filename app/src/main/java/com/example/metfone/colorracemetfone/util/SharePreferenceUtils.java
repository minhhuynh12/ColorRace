package com.example.metfone.colorracemetfone.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {
    private final String SHAREDPREFERENCE_NAME = "COLOR_RACE_SETTING";
    private final String SHAREDPREFERENCE_LANGUAGE = "COLOR_RACE_SETTING_LANGUAGE";
    private final String SYNC_DATA = "LAST_SYNC_DATA_IN_DATE";
    private final String LANGUAGE = "CR_LANGUAGE";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences mSharedPreferencesLanguage;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editorLanguage;
    private Context context;

    public SharePreferenceUtils(Context context) {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        mSharedPreferencesLanguage = context.getSharedPreferences(SHAREDPREFERENCE_LANGUAGE, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        editorLanguage = mSharedPreferencesLanguage.edit();
    }

    public void putLanguage(String value) {
        editorLanguage.putString(LANGUAGE, value);
        editorLanguage.commit();
    }

    public String getLanguage() {
        return mSharedPreferencesLanguage.getString(LANGUAGE, "kh");
    }

    public void putDate(int value) {
//        editor.clear();
        editor.remove(SYNC_DATA);
        editor.putInt(SYNC_DATA, value);
        editor.commit();
    }

    public int getDate() {
        int anInt = mSharedPreferences.getInt(SYNC_DATA, 0);
        return anInt;
    }

}
