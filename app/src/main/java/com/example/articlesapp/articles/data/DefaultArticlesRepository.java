package com.example.articlesapp.articles.data;

import java.util.List;

import javax.inject.Inject;

public class DefaultArticlesRepository implements ArticlesRepository {

    private final ArticlesDataSource articlesLocalDataSource;

    @Inject
    public DefaultArticlesRepository(ArticlesDataSource localDataSource) {
        this.articlesLocalDataSource = localDataSource;
    }

    @Override
    public List<Article> getArticles() {
        return articlesLocalDataSource.getArticles();
    }
}
