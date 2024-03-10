package com.example.articlesapp.articles.data;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

import java.util.Date;

@JsonClass(generateAdapter = false)
public class Article {
    @Json
    public String title;
    @Json
    public String url;
    @Json
    public String urlToImage;
    @Json
    public Date publishedAt;

    public Article(String title, String url, String urlToImage, Date publishedAt) {
        this.title = title;
        this.url = url;
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
    }
}
