package com.example.dell.newsarticlesummarizer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.adapters.ArticlesAdapter;
import com.example.dell.newsarticlesummarizer.models.Article;
import com.example.dell.newsarticlesummarizer.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArticlesActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private RecyclerView rvArcticles;
    private List<Article> articleList = new ArrayList<>();
    private List<Article> selectedList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        articleList = getArticleList();
        rvArcticles = findViewById(R.id.rvArticles);
        rvArcticles.setLayoutManager(new LinearLayoutManager(this));
        ArticlesAdapter articlesAdapter = new ArticlesAdapter(this,articleList);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Article article = articleList.get(position);
            article.setSelected(!article.isSelected());
            if(article.isSelected()){
                selectedList.add(article);
            } else {
                selectedList.remove(article);
            }
    }

    private List<Article> getArticleList() {
        int i;
        List<Article> articles = new ArrayList<>();
        for(i = 0; i < 10; i++) {
            articles.add(new Article(""+i, "This is description of article no. "+i, "Heading of article "+ i, "", Calendar.getInstance().getTime().toString()));
        }
        return articles;
    }
}
