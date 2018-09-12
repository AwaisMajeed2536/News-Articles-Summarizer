package com.example.dell.newsarticlesummarizer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class AppPreferences {

    private static final String USER_LOGGED_IN = "USER_LOGGED_IN";
    private static final String USER_NAME = "USER_NAME";
    private static final String DP_KEY = "DP_KEY";
    private static final String FAVORITES_KEY = "FAVORITES_KEY";

    public static SharedPreferences getInstance(Context activity) {
        return PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public static void setLoggedIn(String loggedIn, SharedPreferences preferences) {
        preferences.edit().putString(USER_LOGGED_IN, loggedIn).apply();
    }

    public static boolean isLoggedIn(SharedPreferences preferences) {
        String email = preferences.getString(USER_LOGGED_IN, "");
        return Utils.isNotEmpty(email) && !email.equals("");
    }

    public static String getUserEmail(SharedPreferences preferences) {
        return preferences.getString(USER_LOGGED_IN, "");
    }

    public static String getUserEmail(Activity activity) {
        return PreferenceManager.getDefaultSharedPreferences(activity).getString(USER_LOGGED_IN, "");
    }

    public static void setUserName(String userName, SharedPreferences preferences) {
        preferences.edit().putString(USER_NAME, userName).apply();
    }

    public static String getUserName(SharedPreferences preferences) {
        return preferences.getString(USER_NAME, "");
    }

    public static void saveImageAndPath(Bitmap bm, SharedPreferences preferences) {
        if (bm == null) {
            preferences.edit().putString(DP_KEY, "").apply();
            return;
        }
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        Log.i(TAG, "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            preferences.edit().putString(DP_KEY, file.getAbsolutePath()).apply();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getDpImage(SharedPreferences preferences) {
        File file = new File(preferences.getString(DP_KEY, ""));
        if (file.exists()) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(preferences.getString(DP_KEY, ""), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            return bitmap;
        } else {
            return null;
        }
    }

    public static void addToFavorites(Context context, String newsName) {
        if (isAddedToFavorites(context, newsName))
            return;
        String favs = getFavoritesString(context);
        String toStore = favs == null ? newsName : favs + "," + newsName;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(FAVORITES_KEY, toStore).apply();
    }

    public static void removeFromFavorites(Context context, String newsName) {
        if (isAddedToFavorites(context, newsName)) {
            String[] previousNews = getFavorites(context);
            StringBuilder toStore = new StringBuilder();
            if (previousNews == null)
                return;
            for (String previousNew : previousNews) {
                if (!previousNew.equalsIgnoreCase(newsName)) {
                    toStore.append(previousNew).append(",");
                }
            }
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(FAVORITES_KEY, toStore.length() == 0 ? null : toStore.toString()).apply();
        }
    }

    public static boolean isAddedToFavorites(Context context, String newsName) {
        String[] favs = getFavorites(context);
        if (favs != null) {

            for (String fav : favs) {
                if (fav.equalsIgnoreCase(newsName)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static String[] getFavorites(Context context) {
        String favs = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(FAVORITES_KEY, null);
        if (favs != null) {
            return favs.split(",");
        }
        return null;
    }

    private static String getFavoritesString(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(FAVORITES_KEY, null);
    }
}
