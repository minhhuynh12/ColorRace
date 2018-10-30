package com.example.metfone.colorracemetfone.ui.base.sharePreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vitinhHienAnh on 28-10-18.
 */

public class BaseSharePreference {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public boolean putSharePreference(Context context , String key , String value){
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(key, value);
        }catch (Exception ex){
            Log.d("error" , "log: " +ex.getMessage());
            return false;
        }
        return true;
    }

    public String getSharePreference(Context context , String key, String v){

        return null;
    }

}
