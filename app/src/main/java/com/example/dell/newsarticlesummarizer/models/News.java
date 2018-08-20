package com.example.dell.newsarticlesummarizer.models;

import java.util.List;

public class News {
    private String newsName;
    private List<Article> articleList;

    public News(String newsName, List<Article> articleList) {
        this.newsName = newsName;
        this.articleList = articleList;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
