package com.example.articlesapp.articles.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.data.DefaultArticlesRepository;
import com.example.articlesapp.articles.fakes.FakeArticlesDataSource;
import com.example.articlesapp.articles.fakes.FakeConstants;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ArticlesUseCaseTest {

    private FakeArticlesDataSource fakeArticlesDataSource;
    private ArticlesUseCase SUT; // system under test

    @Before
    public void setUp() throws Exception {
        fakeArticlesDataSource = new FakeArticlesDataSource();
        DefaultArticlesRepository defaultArticlesRepository = new DefaultArticlesRepository(fakeArticlesDataSource);

        SUT = new ArticlesUseCase(defaultArticlesRepository);

    }

    @Test
    public void getArticles_onSuccessFullFetch_shouldReturnValidData() throws Exception {
        //arrange
        fakeArticlesDataSource.onSuccess = true;
        List<Article> expectedResults = FakeConstants.getInstance().dummyArticles();

        //act
        List<Article> results = SUT.getArticles();

        //assert
        assertFalse(results.isEmpty());
        assertEquals(expectedResults.get(0).title, results.get(0).title);
    }

    @Test
    public void getArticles_onFailureToFetch_shouldReturnEmptyCollections() {
        //arrange
        fakeArticlesDataSource.onSuccess = false;

        //act
        List<Article> results = SUT.getArticles();

        //assert
        assertTrue(results.isEmpty());

    }
}