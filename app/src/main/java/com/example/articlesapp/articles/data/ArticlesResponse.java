package com.example.articlesapp.articles.data;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

import java.util.List;

@JsonClass(generateAdapter = false)
public class ArticlesResponse {
        @Json
        public List<Article> articles;
}
