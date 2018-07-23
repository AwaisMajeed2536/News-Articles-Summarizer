package com.example.dell.newsarticlesummarizer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;

public class WebViewActvity extends BaseActivity {
    public final static String WEB_URL = "WEB_URL";
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar().setTitle("News Page");
        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        if(intent.hasExtra(WEB_URL)) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(intent.getStringExtra(WEB_URL));
        }
    }
}
