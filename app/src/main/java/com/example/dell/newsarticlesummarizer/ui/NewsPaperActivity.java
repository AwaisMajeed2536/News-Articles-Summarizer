package com.example.dell.newsarticlesummarizer.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.adapters.NewsAdapter;
import com.example.dell.newsarticlesummarizer.models.News;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;

public class NewsPaperActivity extends BaseActivity{
    private RecyclerView rvArcticles;
    private NewsAdapter articlesAdapter;
    private List<News> articleList = new ArrayList<>();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
//        getActionBar().setTitle("Hello world App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News List");
        preferences = AppPreferences.getInstance(this);
        rvArcticles = findViewById(R.id.rvArticles);
        rvArcticles.setLayoutManager(new LinearLayoutManager(this));
        articleList = getNewsList();
        articlesAdapter = new NewsAdapter(NewsPaperActivity.this, articleList);
        rvArcticles.setAdapter(articlesAdapter);


//        FloatingActionButton doneBtn = findViewById(R.id.doneBtn);

//        doneBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Utils.isEmpty(selectedList)) {
//                    showError(getString(R.string.selected_article_empty));
//                }
//            }
//        });
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

    private List<News> getNewsList() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News("Pakistan Observer","https://pakobserver.net/category/islamabad/page/1"));
        newsList.add(new News("Dawn", "https://www.dawn.com/newspaper/islamabad"));
        newsList.add(new News("The News", "https://www.thenews.com.pk/print/category/islamabad"));
        newsList.add(new News("Tribune", "https://tribune.com.pk/islamabad/"));
        return newsList;
    }
}
