package com.example.dell.newsarticlesummarizer.ui;

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
import com.example.dell.newsarticlesummarizer.interfaces.OnItemClickListener;
import com.example.dell.newsarticlesummarizer.models.Article;
import com.example.dell.newsarticlesummarizer.services.GoogleApi;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.FileUtils;
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
    private AppPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
//        getActionBar().setTitle("Hello world App");
        getSupportActionBar().setTitle("Articles List");
        preferences = new AppPreferences(this);
        articleList = FileUtils.readRawTextFile(this, R.raw.output);
        rvArcticles = findViewById(R.id.rvArticles);
        rvArcticles.setLayoutManager(new LinearLayoutManager(this));
        articlesAdapter = new ArticlesAdapter(this, articleList, this);
        rvArcticles.setAdapter(articlesAdapter);
        FloatingActionButton doneBtn = findViewById(R.id.doneBtn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isEmpty(selectedList)) {
                    showError(getString(R.string.selected_article_empty));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sign_out){
            if(GoogleApi.getAccount(this) != null) {
                signOut();
            } else {
                preferences.setLoggedIn(false);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        GoogleApi.getSignInClient().signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
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

    private List<Article> getArticleList() {
        int i;
        List<Article> articles = new ArrayList<>();
        for(i = 0; i < 10; i++) {
            articles.add(new Article(""+i, "This is description of article no. "+i, "Heading of article "+ i, "", Calendar.getInstance().getTime().toString(), null));
        }
        return articles;
    }
}
