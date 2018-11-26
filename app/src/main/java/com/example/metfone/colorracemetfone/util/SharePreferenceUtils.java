package com.example.metfone.colorracemetfone.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharePreferenceUtils {
    private final String SHAREDPREFERENCE_NAME = "SHARE_PREFERENCE";
    private final String SHAREDPREFERENCE_LANGUAGE = "COLOR_RACE_SETTING_LANGUAGE";
    private final String SHAREDPREFERENCE_PIN_CODE = "SHARE_PRE";
    private final String SYNC_DATA = "LAST_SYNC_DATA_IN_DATE";
    private final String LANGUAGE = "CR_LANGUAGE";
    private final String PIN_CODE = "PIN_CODE";
    private final String OTP = "OTP";
    private final String ISDN = "ISDN";
    private final String ISDN_LOGIN = "ISDN_LOGIN";
    private final String STATUS = "STATUS";
    private final String QR_CODE = "QR_CODE";
    private final String TYPE_TICKET = "TYPE_TICKET";
    private final String LAT = "LAT";
    private final String LONG = "LONG";
    private final String TOTAL_ISDN = "TOTAL_ISDN";
    private final String TIME_NIGHT_RACE = "TIME_NIGHT_RACE";
    private final String PERMISSION = "PERMISSION";
    private final String STATUS_CUSTOMER = "STATUS_CUSTOMER";
    private final String DEVICE_ID = "DEVICE_ID";
    private final String LIST_GIFT = "LIST_GIFT";
    private final String ROLE_NAME = "ROLE_NAME";
    private final String SYSTEM_DATE = "SYSTEM_DATE";
    private final String FLAG_QR_CODE = "FLAG_QR_CODE";
    private final String ROLE_CODE = "Role_code";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences mSharedPreferencesLanguage;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editorLanguage;
    private Context context;
    private Gson gson;

    public SharePreferenceUtils(Context context) {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        mSharedPreferencesLanguage = context.getSharedPreferences(SHAREDPREFERENCE_LANGUAGE, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        editorLanguage = mSharedPreferencesLanguage.edit();
    }

    public SharePreferenceUtils(Context context , Gson gson) {
        this.context = context;
        this.gson = gson;
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

    public void putPinCode(String value) {
        editor.putString(PIN_CODE, value);
        editor.commit();
    }

    public String getPinCode() {
        return mSharedPreferences.getString(PIN_CODE, "");
    }

    public void putOTP(String value) {
        editor.putString(OTP, value);
        editor.commit();
    }

    public String getOTP() {
        return mSharedPreferences.getString(OTP, "");
    }

    public void putISDN(String value) {
        editor.putString(ISDN, value);
        editor.commit();
    }

    public String getISDN() {
        return mSharedPreferences.getString(ISDN, "");
    }

    public void putStatus(String value) {
        editor.putString(STATUS, value);
        editor.commit();
    }

    public String getStatus() {
        return mSharedPreferences.getString(STATUS, "");
    }

    public void putQrCode(String value) {
        editor.putString(QR_CODE, value);
        editor.commit();
    }

    public String getQrCode() {
        return mSharedPreferences.getString(QR_CODE, "");
    }

    public void putTypeTicket(String value) {
        editor.putString(TYPE_TICKET, value);
        editor.commit();
    }

    public String getTypeTicket() {
        return mSharedPreferences.getString(TYPE_TICKET, "");
    }

    public void putLat(String value) {
        editor.putString(LAT, value);
        editor.commit();
    }

    public String getLat() {
        return mSharedPreferences.getString(LAT, "");
    }

    public void putLong(String value) {
        editor.putString(LONG, value);
        editor.commit();
    }

    public String getLong() {
        return mSharedPreferences.getString(LONG, "");
    }

    public void putTimeNightRace(String value) {
        editor.putString(TIME_NIGHT_RACE, value);
        editor.commit();
    }

    public String getTimeNightRace() {
        return mSharedPreferences.getString(TIME_NIGHT_RACE, "");
    }
    public void putPermission(String value) {
        editor.putString(PERMISSION, value);
        editor.commit();
    }

    public String getPermission() {
        return mSharedPreferences.getString(PERMISSION, "");
    }

    public void putTotalIsdn(int value) {
        editor.putInt(TOTAL_ISDN, value);
        editor.commit();
    }

    public int getTotalIsdn() {
        return mSharedPreferences.getInt(TOTAL_ISDN, 0);
    }

    public void putStatusCustomer(String value) {
        editor.putString(STATUS_CUSTOMER, value);
        editor.commit();
    }

    public String getStatusCustomer() {
        return mSharedPreferences.getString(STATUS_CUSTOMER, "");
    }


    public void putISDN_LOGIN(String value) {
        editor.putString(ISDN_LOGIN, value);
        editor.commit();
    }

    public String getISDN_LOGIN() {
        return mSharedPreferences.getString(ISDN_LOGIN, "");
    }


    public void putDeviceID(String value) {
        editor.putString(DEVICE_ID, value);
        editor.commit();
    }

    public String getDeviceID() {
        return mSharedPreferences.getString(DEVICE_ID, "");
    }

    public <T> void putList(List<T> list) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(LIST_GIFT, gson.toJson(list));
        editor.apply();
    }

    public <T> List<T> getList(Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(mSharedPreferences.getString(LIST_GIFT, null), typeOfT);
    }

   public void putRoleName(String value) {
       editor.putString(ROLE_NAME, value);
       editor.commit();
   }

    public String getRoleName() {
        return mSharedPreferences.getString(ROLE_NAME, "");
    }


    public void putSysDate(String value) {
        editor.putString(SYSTEM_DATE, value);
        editor.commit();
    }

    public String getSystemDate() {
        return mSharedPreferences.getString(SYSTEM_DATE, "");
    }

    //DASSDAD
    public void putFlagQrCode(String value) {
        editor.putString(FLAG_QR_CODE, value);
        editor.commit();
    }

    public String getFlagQrCode() {
        return mSharedPreferences.getString(FLAG_QR_CODE, "");
    }

    //DASSDAD
    public void putRoleCode(String value) {
        editor.putString(ROLE_CODE, value);
        editor.commit();
    }

    public String getRoleCode() {
        return mSharedPreferences.getString(ROLE_CODE, "");
    }

}
