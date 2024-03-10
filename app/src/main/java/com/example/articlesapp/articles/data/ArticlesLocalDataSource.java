package com.example.articlesapp.articles.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.WorkerThread;

import com.example.articlesapp.utils.Constants;
import com.example.articlesapp.utils.StorageProvider;
import com.example.articlesapp.utils.Utils;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ArticlesLocalDataSource implements ArticlesDataSource {

    private static final String TAG = ArticlesLocalDataSource.class.getSimpleName();

    private final StorageProvider<List<Article>> assetsReader;

    @Inject
    public ArticlesLocalDataSource(StorageProvider<List<Article>> assetsReader) {
        this.assetsReader = assetsReader;
    }

    @Override
    public List<Article> getArticles() {
        return assetsReader.readFromStorage();
    }
}
