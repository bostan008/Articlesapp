package com.example.articlesapp.articles.fakes;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.utils.StorageProvider;

import java.util.Collections;
import java.util.List;

public class FakeStorageProvider implements StorageProvider<List<Article>> {

    public boolean isReadSuccessFull = false;

    @Override
    public List<Article> readFromStorage() {

        if (isReadSuccessFull) {
            return FakeConstants.getInstance().dummyArticles();
        } else {
            return Collections.emptyList();
        }
    }
}
