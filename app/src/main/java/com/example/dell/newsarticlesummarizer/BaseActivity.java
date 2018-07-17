package com.example.dell.newsarticlesummarizer;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseActivity extends AppCompatActivity {

    public void showError(String error) {
        showError(error, Snackbar.LENGTH_SHORT);
    }

    public void showError(String error, int length) {
        Snackbar.make(findViewById(android.R.id.content), error, length).show();
    }

    public int getColorRes(int color) {
        return ContextCompat.getColor(this, color);
    }

    public void hideKeyboard() {
        hideKeyboard(this.getCurrentFocus());
    }

    public void hideKeyboard(View currentFocusView) {
        try {
            if (currentFocusView != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
