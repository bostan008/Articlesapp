package com.example.articlesapp.articles.presentation;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.domain.ArticlesUseCase;
import com.example.articlesapp.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArticlesViewModel extends ViewModel {

    private static final String TAG = ArticlesViewModel.class.getSimpleName();

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public final CompositeDisposable disposable;
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public final List<Article> cachedResults;
    private final MutableLiveData<ArticleUiState> uiState = new MutableLiveData<>(
            new ArticleUiState(true, Collections.emptyList(), ArticleStates.INITIAL_STATE, new ArticleErrorState("")));
    private final ArticlesUseCase articlesUseCase;

    @Inject
    public ArticlesViewModel(ArticlesUseCase articlesUseCase) {
        this.articlesUseCase = articlesUseCase;
        cachedResults = new ArrayList<>();
        disposable = new CompositeDisposable();
    }

    public LiveData<ArticleUiState> getUiState() {
        return uiState;
    }

    void getArticles() {
        disposable.add(Observable.just("")
                .map(
                        s -> articlesUseCase.getArticles()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articles -> {
                            setCachedResults(articles);

                            emit(new ArticleUiState(
                                    false,
                                    articles,
                                    ArticleStates.CONTENT_LOADED_STATE,
                                    new ArticleErrorState(
                                            ""
                                    )
                            ));
                        },
                        throwable -> {
                            emit(new ArticleUiState(
                                    false,
                                    Collections.emptyList(),
                                    ArticleStates.GENERAL_ERROR_STATE,
                                    new ArticleErrorState(throwable.getMessage())
                            ));
                        },
                        () -> {
                            //on complete
                        }
                )
        );

    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    void setCachedResults(List<Article> results) {
        cachedResults.clear();
        cachedResults.addAll(results);
    }

    private void emit(ArticleUiState articleUiState) {
        uiState.postValue(articleUiState);
    }


    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }

    public void filter(String query) {
        filterResults(cachedResults, query);
    }


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public void filterResults(List<Article> results, String query) {
        disposable.add(
                Utils.getInstance().searchArticles(results, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.single())
                        .subscribe(
                                articles -> {
                                    if (articles.isEmpty() && query.isEmpty()) {
                                        emit(
                                                new ArticleUiState(
                                                        false,
                                                        cachedResults,
                                                        ArticleStates.SEARCH_EMPTY_QUERY_STATE, //when querry is empty load cached results
                                                        new ArticleErrorState("")
                                                ));
                                    } else if (articles.isEmpty() && !query.isEmpty()) {
                                        emit(
                                                new ArticleUiState(
                                                        false,
                                                        Collections.emptyList(),
                                                        ArticleStates.SEARCH_NO_RESULTS_FOUND_STATE,
                                                        new ArticleErrorState("No Results found")
                                                ));
                                    } else {
                                        emit(
                                                new ArticleUiState(
                                                        false,
                                                        articles,
                                                        ArticleStates.SEARCH_CONTENT_LOADED_STATE,
                                                        new ArticleErrorState("")
                                                ));
                                    }

                                },
                                error -> {
                                    emit(
                                            new ArticleUiState(
                                                    false,
                                                    Collections.emptyList(),
                                                    ArticleStates.SEARCH_ERROR_STATE,
                                                    new ArticleErrorState(error.getMessage())
                                            ));
                                },
                                () -> {
                                    //search complete
                                }
                        )
        );
    }
}
