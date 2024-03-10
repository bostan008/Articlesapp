package com.example.articlesapp.articles.fakes;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.data.ArticlesRepository;

import java.util.Collections;
import java.util.List;

public class FakeArticlesRepository implements ArticlesRepository {

    public boolean onSuccess = false;
    public boolean throwException = false;

    @Override
    public List<Article> getArticles() {
        if (onSuccess) {
            return FakeConstants.getInstance().dummyArticles();
        } else {
            if (!throwException) {
                return Collections.emptyList();
            } else {
                throw new RuntimeException(FakeConstants.getInstance().ERROR_MESSAGE);
            }
        }
    }
}
