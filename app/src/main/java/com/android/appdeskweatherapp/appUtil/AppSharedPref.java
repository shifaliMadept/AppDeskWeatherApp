package com.android.appdeskweatherapp.appUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPref {

    private Activity activity;
    private static AppSharedPref appSharedPreferences;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppSharedPref(Activity activity) {
        this.activity = activity;

    }

    public static AppSharedPref getReferenceProvider(Activity activity) {
        if (appSharedPreferences == null) {
            appSharedPreferences = new AppSharedPref(activity);
        }
        return appSharedPreferences;
    }


    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences("weatherappprefs", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void putValue(String key, String value){
        editor = getReferenceProvider(activity).getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBooleanValue(String key, Boolean value){
        editor = getReferenceProvider(activity).getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getValue(String key){
        sharedPreferences = getReferenceProvider(activity).getSharedPreferences();
        return  sharedPreferences.getString(key, null);
    }

    public boolean getBooleanValue(String key){
        sharedPreferences = getReferenceProvider(activity).getSharedPreferences();
        return  sharedPreferences.getBoolean(key, true);
    }

    public void clearValue(String key){
        editor = getReferenceProvider(activity).getSharedPreferences().edit();
        editor.putString(key, "");
        editor.apply();
    }
}
