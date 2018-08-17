package com.example.dell.newsarticlesummarizer.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.adapters.ArticlesAdapter;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.interfaces.OnItemClickListener;
import com.example.dell.newsarticlesummarizer.models.Article;
import com.example.dell.newsarticlesummarizer.services.GoogleApi;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemClickListener {

    private RecyclerView rvArcticles;
    private ArticlesAdapter articlesAdapter;
    private List<Article> articleList = new ArrayList<>();
    private List<Article> selectedList = new ArrayList<>();
    private SharedPreferences preferences;
    private TextView emailTv;
    private TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NAS");
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
                    articlesAdapter = new ArticlesAdapter(MainActivity.this, articleList, MainActivity.this);
                    rvArcticles.setAdapter(articlesAdapter);
                }
            }
        });
//        FloatingActionButton doneBtn = findViewById(R.id.doneBtn);
//
//        doneBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Utils.isEmpty(selectedList)) {
//                    showError(getString(R.string.selected_article_empty));
//                }
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        emailTv = navigationView.getHeaderView(0).findViewById(R.id.emailTv);
        nameTv = navigationView.getHeaderView(0).findViewById(R.id.nameTv);
        nameTv.setText(AppPreferences.getUserName(preferences));
        emailTv.setText(AppPreferences.getUserEmail(preferences));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.show_articles) {
            Intent intent = new Intent(this, ArticlesActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.show_news) {
            startActivity(new Intent(this, NewsPaperActivity.class));
        } else if (id == R.id.change_name) {
            startActivity(new Intent(this, ChangeNameActivity.class));
        } else if (id == R.id.change_password) {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        } else if (id == R.id.sign_out) {
            if(GoogleApi.getAccount(this) != null) {
                signOut();
            } else {
                AppPreferences.setLoggedIn(null, preferences);
                AppPreferences.setUserName(null, preferences);
                finish();
            }
        }/* else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

//    private List<Article> getArticleList() {
//        int i;
//        List<Article> articles = new ArrayList<>();
//        for(i = 0; i < 10; i++) {
//            articles.add(new Article(""+i, "This is description of article no. "+i, "Heading of article "+ i, "", Calendar.getInstance().getTime().toString(), null));
//        }
//        return articles;
//    }
}
