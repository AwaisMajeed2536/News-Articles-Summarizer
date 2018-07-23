package com.example.dell.newsarticlesummarizer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dell.newsarticlesummarizer.BaseActivity;

import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class AppPreferences {

    private static SharedPreferences preferences;

    public static void getInstance(BaseActivity activity){
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void storeThis(String string){
        preferences.edit().putString("key", string).apply();
    }
}
