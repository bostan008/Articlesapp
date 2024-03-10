package com.example.articlesapp.articles.presentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.example.articlesapp.RxImmediateSchedulerRule;
import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.domain.ArticlesUseCase;
import com.example.articlesapp.articles.fakes.FakeArticlesRepository;
import com.example.articlesapp.articles.fakes.FakeConstants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ArticlesViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Rule
    public RxImmediateSchedulerRule timeoutRule = new RxImmediateSchedulerRule();

    private FakeArticlesRepository fakeArticlesRepository;

    private ArticlesViewModel SUT; // system under test

    @Before
    public void setUp() {
        fakeArticlesRepository = new FakeArticlesRepository();

        ArticlesUseCase articlesUseCase = new ArticlesUseCase(fakeArticlesRepository);

        SUT = new ArticlesViewModel(articlesUseCase);
    }

    @Test
    public void getUIState_OnViewModelInit_shouldEmitLoadingState() {
        //arrange

        //act
        LiveData<ArticleUiState> state = SUT.getUiState();

        //assert
        assertTrue(Objects.requireNonNull(state.getValue()).isLoading);
    }

    @Test
    public void getArticles_onSuccess_shouldEmitValidState() {
        //arrange
        fakeArticlesRepository.onSuccess = true;
        List<Article> expectedResults = FakeConstants.getInstance().dummyArticles();

        //act
        SUT.getArticles();

        //assert

        assertFalse(Objects.requireNonNull(SUT.getUiState().getValue()).isLoading);
        assertEquals(expectedResults.get(0).title, SUT.getUiState().getValue().articles.get(0).title);
    }

    @Test
    public void getArticles_onSuccess_shouldEmitValidStateAndAssertCachedResults() {
        //arrange
        fakeArticlesRepository.onSuccess = true;
        List<Article> expectedResults = FakeConstants.getInstance().dummyArticles();

        //act
        SUT.getArticles();

        //assert

        assertEquals(Objects.requireNonNull(SUT.getUiState().getValue()).articles, SUT.cachedResults);
        assertFalse(Objects.requireNonNull(SUT.getUiState().getValue()).isLoading);
        assertEquals(expectedResults.get(0).title, SUT.getUiState().getValue().articles.get(0).title);
    }

    @Test
    public void getArticles_onFailure_shouldEmitErrorState() {
        //arrange
        fakeArticlesRepository.onSuccess = false;
        fakeArticlesRepository.throwException = true;

        //act
        SUT.getArticles();

        //assert
        assertFalse(Objects.requireNonNull(SUT.getUiState().getValue()).isLoading);
        assertTrue(SUT.getUiState().getValue().articles.isEmpty());
        assertFalse(SUT.getUiState().getValue().errorState.errorMessage.isEmpty());
    }

    @Test
    public void setCachedResults_whenCalledShouldSaveResultsLocally() {
        //arrange
        fakeArticlesRepository.onSuccess = true;
        List<Article> expectedResults = FakeConstants.getInstance().dummyArticles();

        //act
        SUT.setCachedResults(expectedResults);

        //assert
        assertEquals(expectedResults, SUT.cachedResults);
    }


    @Test
    public void getArticles_onSuccess_shouldSortListByDate() {
        //arrange
        fakeArticlesRepository.onSuccess = true;

        //act
        SUT.getArticles();

        //assert
        assertTrue(isSorted(Objects.requireNonNull(SUT.getUiState().getValue()).articles));
    }

    @Test
    public void onCleared_whenCalledShouldDisposeAllSubscriptions() {
        SUT.onCleared();

        assertEquals(SUT.disposable.size(), 0);
    }


    @Test
    public void filterResults_whenMatchingResultsAreFound_emitSEARCH_CONTENT_LOADED_STATEAndResults() {
        //arrange
        List<Article> dummyArticles = FakeConstants.getInstance().dummyArticles();

        //act
        SUT.filterResults(dummyArticles, "3");

        //assert
        assertEquals(Objects.requireNonNull(SUT.getUiState().getValue()).articles.get(0).title, "title3");
        assertEquals(SUT.getUiState().getValue().state, ArticleStates.SEARCH_CONTENT_LOADED_STATE);

    }

    @Test
    public void filterResults_whenMatchingResultsAreNotFound_emitSEARCH_NO_RESULTS_FOUND_STATEAndResults() {
        //arrange
        List<Article> dummyArticles = FakeConstants.getInstance().dummyArticles();

        //act
        SUT.filterResults(dummyArticles, "10");

        //assert
        assertTrue(Objects.requireNonNull(SUT.getUiState().getValue()).articles.isEmpty());
        assertEquals(SUT.getUiState().getValue().state, ArticleStates.SEARCH_NO_RESULTS_FOUND_STATE);
    }

    @Test
    public void filterResults_whenSearchQueryIsEmptyAndNoResultsFound_emitSEARCH_EMPTY_QUERY_STATEAndCachedResults() {
        //arrange
        List<Article> dummyArticles = Collections.emptyList();
        fakeArticlesRepository.onSuccess = true;

        //act

        SUT.getArticles();
        SUT.filterResults(dummyArticles, "");

        //assert
        assertEquals(SUT.cachedResults.get(0).title, "title1");
        assertEquals(Objects.requireNonNull(SUT.getUiState().getValue()).state, ArticleStates.SEARCH_EMPTY_QUERY_STATE);

    }

    @Test
    public void filterResults_onError_emitValidStateAndResults() {

    }

    private boolean isSorted(List<Article> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            if (items.get(i).publishedAt.compareTo(items.get(i + 1).publishedAt) > 0) {
                return false;
            }
        }
        return true;
    }
}