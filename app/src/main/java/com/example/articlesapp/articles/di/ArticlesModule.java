package com.example.articlesapp.articles.di;

import androidx.lifecycle.ViewModel;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.articles.data.ArticlesDataSource;
import com.example.articlesapp.articles.data.ArticlesLocalDataSource;
import com.example.articlesapp.articles.data.ArticlesRepository;
import com.example.articlesapp.articles.data.DefaultArticlesRepository;
import com.example.articlesapp.articles.presentation.ArticlesViewModel;
import com.example.articlesapp.di.ViewModelKey;
import com.example.articlesapp.utils.ReadFromAssets;
import com.example.articlesapp.utils.StorageProvider;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ArticlesModule {

    @Binds
    public abstract ArticlesDataSource providesArticleDataSource(ArticlesLocalDataSource articlesDataSource);

    @Binds
    public abstract ArticlesRepository providesArticlesRepository(DefaultArticlesRepository articlesDataSource);

    @Binds
    public abstract StorageProvider<List<Article>> provideAssetsReader(ReadFromAssets readFromAssets);



    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel.class)
    public abstract ViewModel bindViewModel(ArticlesViewModel articlesViewModel);

}
