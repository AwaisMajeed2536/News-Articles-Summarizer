package com.example.dell.newsarticlesummarizer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.BuildConfig;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.ui.dialogs.AppDialogs;

import java.util.Collection;

public class Utils {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static String getDeviceInfo() {
        return "Android " + Build.VERSION.RELEASE + "; Model: " + Build.MANUFACTURER + " " + Build.MODEL;
    }

    public static boolean isInDebugMode() {
        return BuildConfig.DEBUG;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isNetworkAvailable(Context context){
        return isNetworkAvailable(context, false, false);
    }

    public static boolean isNetworkAvailable(Context context, boolean showMessage, boolean showDialog) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // if no network is available networkInfo will be null, otherwise check if we are connected
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }

            if (showMessage) {
                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).showError(context.getString(R.string.no_network_available));
                } else {
                    Toast.makeText(context, R.string.no_network_available, Toast.LENGTH_SHORT).show();
                }
            }
            if (showDialog && context instanceof BaseActivity) {
                AppDialogs.showNoInternet((BaseActivity) context);
            }
        } catch (Exception e) {
            Log.e("Utils", e.getMessage());
        }
        return false;
    }

    public static boolean isNetworkNotAvailable(Context context) {
        return !isNetworkAvailable(context);
    }

}
