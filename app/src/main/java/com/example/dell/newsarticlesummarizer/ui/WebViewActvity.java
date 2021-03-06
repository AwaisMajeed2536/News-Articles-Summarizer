package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;

public class WebViewActvity extends BaseActivity {
    public final static String WEB_URL = "WEB_URL";
    private WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar().setTitle("News Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.webView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Page");
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.hide();
            }
        }, 3000);
        Intent intent = getIntent();
        if(intent.hasExtra(WEB_URL)) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(intent.getStringExtra(WEB_URL));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
