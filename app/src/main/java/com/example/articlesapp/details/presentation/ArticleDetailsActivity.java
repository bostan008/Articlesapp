package com.example.articlesapp.details.presentation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.articlesapp.databinding.ActivityArticleDetailsBinding;

import java.util.Objects;

public class ArticleDetailsActivity extends AppCompatActivity {

    public static final String INTENT_URL_KEY = "url";

    private ActivityArticleDetailsBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = Objects.requireNonNull(getIntent().getStringExtra(INTENT_URL_KEY));

        binding = ActivityArticleDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        WebSettings webSettings = binding.articleDetailsWv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        binding.articleDetailsWv.setWebViewClient(new WebViewClient());
        binding.articleDetailsWv.loadUrl(url);
    }


    @Override
    public void onBackPressed() {
        if (binding.articleDetailsWv.canGoBack()) {
            binding.articleDetailsWv.goBack(); // Navigate back in the WebView's history if possible
        } else {
            super.onBackPressed();
        }
    }
}