package com.example.dell.newsarticlesummarizer.models;

public class Article {
    private String articleId;
    private String articleDescription;
    private String articleHeading;
    private String articleDate;
    private String articleLink;
    private String imageUrl;
    private boolean selected = false;

    public Article(String articleId, String articleDescription, String articleHeading, String imageUrl, String articleDate, String articleLink) {
        this.articleId = articleId;
        this.articleDescription = articleDescription;
        this.articleHeading = articleHeading;
        this.articleDate = articleDate;
        this.articleLink = articleLink;
        this.imageUrl = imageUrl;
    }

    public Article(String articleHeading, String articleLink) {
        this.articleHeading = articleHeading;
        this.articleLink = articleLink;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getArticleHeading() {
        return articleHeading;
    }

    public void setArticleHeading(String articleHeading) {
        this.articleHeading = articleHeading;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }
}
