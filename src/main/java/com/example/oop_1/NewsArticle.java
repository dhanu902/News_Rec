package com.example.oop_1;

public class NewsArticle {
    private int id;
    private String link;
    private String headline;
    private String category;
    private String short_description;
    private String authors;
    private String date;

    public NewsArticle(String link, String headline, String category, String short_description, String authors, String date) {
        this.link = link;
        this.headline = headline;
        this.category = category;
        this.short_description = short_description;
        this.authors = authors;
        this.date = date;
    }

    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {return link;}
    public void setUrl(String link) {
        this.link = link;
    }

    public String getCategory() {return category;}
    public void setCategory(String category) {
        this.category = category;
    }

    public String getHeadline() {return headline;}
    public void setHeadline(String title) {
        this.headline = title;
    }

    public String getShort_description() {return short_description;}
    public void setShort_description(String description) {
        this.short_description = description;
    }

    public String getAuthors() {return authors;}
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDate() {return date;}
    public void setDate(String publishedAt) {
        this.date = publishedAt;
    }

    @Override
    public String toString() {
        return headline;
    }
}