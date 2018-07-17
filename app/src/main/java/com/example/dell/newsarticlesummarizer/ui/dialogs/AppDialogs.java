package com.example.dell.newsarticlesummarizer.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;


public class AppDialogs {

    public static void showErrorDialog(Context context, String error){
       new AlertDialog.Builder(context)
               .setTitle("Error!").setMessage(error)
               .setPositiveButton("Ok", null)
               .setNegativeButton("Cancel", null)
               .show();
   }


    private static Dialog noInternetDialog;
    public static void showNoInternet(BaseActivity context){
        if(noInternetDialog != null && noInternetDialog.isShowing()){
            return;
        }
        noInternetDialog = new AlertDialog.Builder(context)
               .setTitle(context.getString(R.string.no_internet_heading))
               .setMessage("The app can't connect to internet.\nPlease check your connection.")
               .setCancelable(false)
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       noInternetDialog = null;
                   }
               }).show();
   }

}
