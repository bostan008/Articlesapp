package com.example.articlesapp.articles.data;

import static org.junit.Assert.*;

import com.example.articlesapp.articles.fakes.FakeArticlesDataSource;
import com.example.articlesapp.articles.fakes.FakeConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class DefaultArticlesRepositoryTest {

    private DefaultArticlesRepository SUT; //system under test
    private FakeArticlesDataSource fakeArticlesDataSource;

    @Before
    public void setUp() throws Exception {
        fakeArticlesDataSource = new FakeArticlesDataSource();

        SUT = new DefaultArticlesRepository(fakeArticlesDataSource);
    }

    @Test
    public void getArticles_OnSuccess_shouldReturnValidData() {
        //arrange
        List<Article> articles = FakeConstants.getInstance().dummyArticles();
        fakeArticlesDataSource.onSuccess = true;

        //act
        List<Article> resultList = SUT.getArticles();


        //assert
        Assert.assertNotNull(resultList);
        Assert.assertEquals(articles.get(0).title, resultList.get(0).title);
    }


    @Test
    public void getArticles_OnFailure_shouldReturnEmptyList() {
        //arrange
        fakeArticlesDataSource.onSuccess = false;

        //act
        List<Article> resultList = SUT.getArticles();


        //assert
        Assert.assertTrue(resultList.isEmpty());
    }
}