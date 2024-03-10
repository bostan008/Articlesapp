package com.example.articlesapp.articles.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.articlesapp.ArticlesApplication;
import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.databinding.ActivityArticlesBinding;
import com.example.articlesapp.details.presentation.ArticleDetailsActivity;
import com.example.articlesapp.di.ViewModelFactory;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class ArticlesActivity extends AppCompatActivity {

    private static final String TAG = ArticlesActivity.class.getSimpleName();
    private ActivityArticlesBinding binding;

    @Inject
    public ViewModelFactory viewModelFactory;
    private ArticlesAdapter articlesAdapter;

    private Disposable disposable;
    private ArticlesViewModel viewModel;

    private boolean isSkipFirstEmissionOnLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((ArticlesApplication) getApplication()).getAppComponent().articlesSubcomponents().build().inject(this);

        super.onCreate(savedInstanceState);

        binding = ActivityArticlesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewModel = new ViewModelProvider(this, viewModelFactory).get(ArticlesViewModel.class);
        viewModel.getArticles();

        viewModel.getUiState().observe(this,
                this::renderUI);

        binding.articlesRv.setLayoutManager(new LinearLayoutManager(this));
        articlesAdapter = new ArticlesAdapter(new ArrayList<Article>(), onItemClickListener);

        binding.articlesRv.setAdapter(articlesAdapter);

        disposable = observeSearchChanges();

    }

    private Disposable observeSearchChanges() {
        return RxTextView.textChanges(binding.articlesSearchEt)
                .debounce(300, TimeUnit.MILLISECONDS) // Debounce for 300 milliseconds
                .map(CharSequence::toString) // Convert CharSequence to String
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String query) {
                        if(isSkipFirstEmissionOnLoad){
                            isSkipFirstEmissionOnLoad = false;
                        }else{
                            filterResults(query);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // Handle errors
                    }

                    @Override
                    public void onComplete() {
                        // Handle completion
                    }
                });
    }

    private void filterResults(String query) {
        viewModel.filter(query);
    }

    private final OnItemClickListener onItemClickListener = this::startDetailsActivity;

    private void renderUI(ArticleUiState articleUiState) {

        renderLoading(articleUiState);

        switch(articleUiState.state){
            case INITIAL_STATE:
                hideSearchUi();
                hideListUi();
                hideErrorContainerUi();
                break;
            case CONTENT_LOADED_STATE:
                renderContent(articleUiState);
                break;
            case SEARCH_CONTENT_LOADED_STATE:
                renderContent(articleUiState);
                break;
            case SEARCH_EMPTY_QUERY_STATE:
                renderContent(articleUiState);
                break;
            case SEARCH_NO_RESULTS_FOUND_STATE:
                renderNoSearchResultsFound(articleUiState);
                break;
            case SEARCH_ERROR_STATE:
                renderError(articleUiState);
                break;
            case GENERAL_ERROR_STATE:
                renderError(articleUiState);
                break;
            case UNKNOWN_STATE:
                break;
            default:
        }





    }

    private void renderLoading(ArticleUiState articleUiState){
        if (articleUiState.isLoading) {
            showLoading();
        } else {
            hideLoading();
        }
    }

    private void renderContent(ArticleUiState articleUiState){
        articlesAdapter.publishResults(articleUiState.articles);
        showSearchUi();
        showListUi();
        binding.articlesSearchEt.requestFocus();
        hideErrorContainerUi();
    }

    private void renderNoSearchResultsFound(ArticleUiState articleUiState){
        articlesAdapter.publishResults(articleUiState.articles);
        showSearchErrorUi(articleUiState.errorState.errorMessage);
    }

    private void renderError(ArticleUiState articleUiState){
        switch (articleUiState.state){
            case GENERAL_ERROR_STATE: {
                showGeneralErrorUi(articleUiState.errorState.errorMessage);
                break;
            }
            case SEARCH_ERROR_STATE: {
                showSearchErrorUi(articleUiState.errorState.errorMessage);
                break;
            }
            default: {
                hideErrorContainerUi();
            }
        }
    }

   private void hideSearchUi(){
       binding.articlesSearchEt.setVisibility(View.GONE);
   }

   private void showSearchUi(){
       binding.articlesSearchEt.setVisibility(View.VISIBLE);
   }
    private void showListUi(){
        binding.articlesRv.setVisibility(View.VISIBLE);
    }

    private void hideListUi(){
        binding.articlesRv.setVisibility(View.GONE);
    }

    private void hideErrorContainerUi() {
        binding.articleErrorContainer.setVisibility(View.GONE);
    }
    private void showSearchErrorUi(String text) {
        binding.articlesSearchEt.setVisibility(View.VISIBLE);
        binding.articlesRv.setVisibility(View.GONE);
        binding.articleErrorContainer.setVisibility(View.VISIBLE);
        binding.articlesErrorTv.setText(text);
    }

    private void showGeneralErrorUi(String text) {
        binding.articlesSearchEt.setVisibility(View.GONE);
        binding.articlesRv.setVisibility(View.GONE);
        binding.articleErrorContainer.setVisibility(View.VISIBLE);
        binding.articlesErrorTv.setText(text);
    }

    private void showLoading() {
        binding.articlesCpb.show();
    }

    private void hideLoading() {
        binding.articlesCpb.hide();
    }

    private void startDetailsActivity(String url) {
        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra(ArticleDetailsActivity.INTENT_URL_KEY, url);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}