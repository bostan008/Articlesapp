package com.example.articlesapp.articles.presentation;

import com.example.articlesapp.articles.data.Article;

import java.util.List;

public class ArticleUiState {
    public boolean isLoading;
    public List<Article> articles;
    public ArticleStates state;

    public ArticleErrorState errorState;

    public ArticleUiState(boolean isLoading, List<Article> articles, ArticleStates state, ArticleErrorState errorState) {
        this.isLoading = isLoading;
        this.articles = articles;
        this.state = state;
        this.errorState = errorState;
    }
}
