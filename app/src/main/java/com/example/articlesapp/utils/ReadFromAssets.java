package com.example.articlesapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.data.ArticlesResponse;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ReadFromAssets implements StorageProvider<List<Article>>, Closeable {

    private static final String TAG = ReadFromAssets.class.getSimpleName();
    private final WeakReference<Context> contextWeakReference;
    private final Moshi moshi;

    @Inject
    public ReadFromAssets(Context context, Moshi moshi) {
        this.contextWeakReference = new WeakReference<>(context);
        this.moshi = moshi;
    }

    @Override
    public List<Article> readFromStorage() {
        try {
            String articlesJsonFileName = Constants.ARTICLES_JSON_FILE_NAME;
            String rawResponse = Utils.getInstance().readJsonFromAssets(contextWeakReference.get(), articlesJsonFileName);

            JsonAdapter<ArticlesResponse> jsonAdapter = moshi.adapter(ArticlesResponse.class);
            ArticlesResponse articlesResponse = jsonAdapter.fromJson(rawResponse);


            if (articlesResponse != null) {

                Collections.sort(articlesResponse.articles, (o1, o2) -> {
                    return o1.publishedAt.compareTo(o2.publishedAt);
                });

                return articlesResponse.articles;
            } else {
                Log.e(TAG, "Error in Parsing.");
                return Collections.emptyList();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());

        } finally {
            close();
        }

        return Collections.emptyList();
    }

    @Override
    public void close() {
        contextWeakReference.clear();
    }
}
