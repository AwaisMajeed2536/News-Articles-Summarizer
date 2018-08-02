package com.example.dell.newsarticlesummarizer.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;


public class SplashScreenActivity extends AppCompatActivity {
    Handler handler = new Handler();
    SharedPreferences preferences;
    private final String TAG = SplashScreenActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeSplash);
        preferences = AppPreferences.getInstance(SplashScreenActivity.this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_splash_screen);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AppPreferences.isLoggedIn(preferences)){
                    startActivity(new Intent(SplashScreenActivity.this , MainActivity.class));
                }else {
                    startActivity(new Intent(SplashScreenActivity.this , LoginActivity.class));
                }
                SplashScreenActivity.this.finish();
            }
        },2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
