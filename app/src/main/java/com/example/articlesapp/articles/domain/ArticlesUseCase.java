package com.example.articlesapp.articles.domain;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.data.ArticlesRepository;

import java.util.List;

import javax.inject.Inject;

public class ArticlesUseCase {

    private final ArticlesRepository articlesRepository;

    @Inject
    public ArticlesUseCase(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }


    public List<Article> getArticles() {
        return articlesRepository.getArticles();
    }

}
