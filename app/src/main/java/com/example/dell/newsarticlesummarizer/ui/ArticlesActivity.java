package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.adapters.ArticlesAdapter;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.interfaces.OnItemClickListener;
import com.example.dell.newsarticlesummarizer.models.Article;
import com.example.dell.newsarticlesummarizer.models.News;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticlesActivity extends BaseActivity{
    public static final String MODEL_NAME = "MODEL_NAME";

    private RecyclerView rvArcticles;
    private ArticlesAdapter articlesAdapter;
    private List<Article> articleList = new ArrayList<>();
    private String newsName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
//        getActionBar().setTitle("Hello world App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Articles List");
        rvArcticles = findViewById(R.id.rvArticles);
        rvArcticles.setLayoutManager(new LinearLayoutManager(this));
        newsName = getIntent().getStringExtra(MODEL_NAME);

        final ProgressDialog progressDialog = showProgressDialog("Loading Articles", "Please wait...");
        FirebaseUtils.getArticlesList(newsName, new Callback<List<Article>>() {
            @Override
            public void call(List<Article> articles) {
                progressDialog.dismiss();
                articleList = articles;
                articlesAdapter = new ArticlesAdapter(ArticlesActivity.this, articleList);
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
