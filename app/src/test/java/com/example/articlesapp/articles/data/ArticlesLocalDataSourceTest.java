package com.example.articlesapp.articles.data;

import static org.junit.Assert.*;

import com.example.articlesapp.articles.fakes.FakeConstants;
import com.example.articlesapp.articles.fakes.FakeStorageProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.List;

public class ArticlesLocalDataSourceTest {

    private ArticlesLocalDataSource SUT; // system under test
    private FakeStorageProvider fakeStorageProvider;

    @Before
    public void setUp() {
        fakeStorageProvider = new FakeStorageProvider();
        SUT = new ArticlesLocalDataSource(fakeStorageProvider);
    }

    @Test
    public void getArticles_OnSuccessFullReadFromAssets_shouldReturnValidListOfArticles() {
        //arrange
        List<Article> expectedResults = FakeConstants.getInstance().dummyArticles();
        fakeStorageProvider.isReadSuccessFull = true;

        //act
        List<Article> results = SUT.getArticles();

        //assert
        assertFalse(results.isEmpty());

        assertEquals(expectedResults.get(0).title, results.get(0).title);
    }

    @Test
    public void getArticles_OnFailureDueToParsingOrIOException_shouldReturnEmptyCollection() {
        //arrange
        fakeStorageProvider.isReadSuccessFull = false;

        //act
        List<Article> results = SUT.getArticles();

        //assert
        assertTrue(results.isEmpty());
    }
}