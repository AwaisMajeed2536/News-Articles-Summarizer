package com.example.dell.newsarticlesummarizer.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.adapters.ArticlesAdapter;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.interfaces.OnItemClickListener;
import com.example.dell.newsarticlesummarizer.models.Article;
import com.example.dell.newsarticlesummarizer.services.GoogleApi;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.FileUtils;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;
import com.example.dell.newsarticlesummarizer.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArticlesActivity extends BaseActivity implements OnItemClickListener{
    private RecyclerView rvArcticles;
    private ArticlesAdapter articlesAdapter;
    private List<Article> articleList = new ArrayList<>();
    private List<Article> selectedList = new ArrayList<>();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
//        getActionBar().setTitle("Hello world App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Articles List");
        preferences = AppPreferences.getInstance(this);
        rvArcticles = findViewById(R.id.rvArticles);
        rvArcticles.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUtils.getArticlesList(new Callback<List<Article>>() {
            @Override
            public void call(List<Article> articles) {
                articleList = articles;
                if(articles == null) {
                    showError("Error in connection!");
                } else if(articles.size() == 0) {
                    showError("List of articles is empty");
                } else {
                    articlesAdapter = new ArticlesAdapter(ArticlesActivity.this, articleList, ArticlesActivity.this);
                    rvArcticles.setAdapter(articlesAdapter);
                }
            }
        });

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
    public void onItemClick(int position) {
            Article article = articleList.get(position);
            article.setSelected(!article.isSelected());
            if(article.isSelected()){
                selectedList.add(article);
            } else {
                selectedList.remove(article);
            }
            articlesAdapter.notifyItemChanged(position);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
