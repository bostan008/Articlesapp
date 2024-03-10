package com.example.articlesapp.articles.di;


import com.example.articlesapp.articles.presentation.ArticlesActivity;
import com.example.articlesapp.articles.presentation.ArticlesViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {ArticlesModule.class})
public interface ArticlesSubComponents {



    @Subcomponent.Builder
    interface Builder{

        ArticlesSubComponents build();
    }


    void inject(ArticlesActivity activity);

    ArticlesViewModel bindViewModel();

}
