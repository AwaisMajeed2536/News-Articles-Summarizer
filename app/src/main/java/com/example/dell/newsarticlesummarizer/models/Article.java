package com.example.dell.newsarticlesummarizer.models;

public class Article {
    private String linkSummary;
    private String linkTitle;
    private String linkURL;
    private boolean selected = false;

    public Article() {
    }

    public Article(String linkSummary, String linkTitle, String linkURL) {
        this.linkSummary = linkSummary;
        this.linkTitle = linkTitle;
        this.linkURL = linkURL;
    }

    public String getLinkSummary() {
        return linkSummary;
    }

    public void setLinkSummary(String linkSummary) {
        this.linkSummary = linkSummary;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
