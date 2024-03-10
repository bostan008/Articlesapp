package com.example.articlesapp.articles.fakes;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.data.ArticlesDataSource;

import java.util.Collections;
import java.util.List;

public class FakeArticlesDataSource implements ArticlesDataSource {

    public boolean onSuccess;

    @Override
    public List<Article> getArticles() {

        if (onSuccess) {
            return FakeConstants.getInstance().dummyArticles();
        } else {
            return Collections.emptyList();
        }

    }
}
