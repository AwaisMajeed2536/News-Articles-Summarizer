package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.adapters.NewsAdapter;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.models.News;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsPaperActivity extends BaseActivity{
    private RecyclerView rvArcticles;
    private NewsAdapter articlesAdapter;
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
//        getActionBar().setTitle("Hello world App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News List");
        rvArcticles = findViewById(R.id.rvArticles);
        rvArcticles.setLayoutManager(new LinearLayoutManager(this));
        final ProgressDialog progressDialog = showProgressDialog("Loading News", "Please wait...");
        FirebaseUtils.getNewsList(new Callback<List<News>>() {
            @Override
            public void call(List<News> news) {
                progressDialog.dismiss();
                newsList = news;
                if(newsList == null) {
                    showError("Something went wrong please try again!");
                    return;
                }
                articlesAdapter = new NewsAdapter(NewsPaperActivity.this, newsList);
                rvArcticles.setAdapter(articlesAdapter);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
