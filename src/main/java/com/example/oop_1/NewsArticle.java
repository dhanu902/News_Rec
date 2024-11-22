package com.example.oop_1;

public class NewsArticle {
    private String title;
    private String category;
    private String description;
    private String publishedAt;
    private String author;
    private String url;

    public NewsArticle(String title, String category, String description, String publishedAt, String author, String url) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.publishedAt = publishedAt;
        this.author = author;
        this.url = url;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {return description;}
    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {return url;}
    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedAt() {return publishedAt;}
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getCategory() {return category;}
    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {return author;}
    public void setAuthor(String author) {
        this.author = author;
    }
}