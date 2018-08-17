package com.example.dell.newsarticlesummarizer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    private static final String USER_LOGGED_IN = "USER_LOGGED_IN";
    private static final String USER_NAME = "USER_NAME";

    public static SharedPreferences getInstance(Context activity){
        return PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public static void setLoggedIn(String loggedIn, SharedPreferences preferences) {
        preferences.edit().putString(USER_LOGGED_IN, loggedIn).apply();
    }

    public static boolean isLoggedIn(SharedPreferences preferences) {
        String email = preferences.getString(USER_LOGGED_IN, "");
        return Utils.isNotEmpty(email) && !email.equals("");
    }

    public static String getUserEmail(SharedPreferences preferences){
        return preferences.getString(USER_LOGGED_IN, "");
    }

    public static String getUserEmail(Activity activity){
        return PreferenceManager.getDefaultSharedPreferences(activity).getString(USER_LOGGED_IN, "");
    }

    public static void setUserName(String userName, SharedPreferences preferences) {
        preferences.edit().putString(USER_NAME, userName).apply();
    }

    public static String getUserName(SharedPreferences preferences) {
        return preferences.getString(USER_NAME, "");
    }
}
