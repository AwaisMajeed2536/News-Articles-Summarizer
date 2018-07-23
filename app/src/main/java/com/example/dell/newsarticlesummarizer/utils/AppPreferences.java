package com.example.dell.newsarticlesummarizer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dell.newsarticlesummarizer.BaseActivity;

import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class AppPreferences {

    private static SharedPreferences preferences;

    private static final String USER_LOGGED_IN = "USER_LOGGED_IN";

    public AppPreferences(BaseActivity activity){
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void setLoggedIn(boolean loggedIn) {
        preferences.edit().putBoolean(USER_LOGGED_IN, loggedIn).apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(USER_LOGGED_IN, false);
    }
}
