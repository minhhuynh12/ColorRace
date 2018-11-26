package com.example.metfone.colorracemetfone;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.example.metfone.colorracemetfone.util.LanguageUtils;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;
import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Stetho.initializeWithDefaults(this);
        SharePreferenceUtils sharedPreferences = new SharePreferenceUtils(this);
        sharedPreferences.putLanguage("kh");
        LanguageUtils.setLanguage(
                this, sharedPreferences.getLanguage());

    }
}
