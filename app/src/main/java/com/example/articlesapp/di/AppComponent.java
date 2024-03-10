package com.example.articlesapp.di;


import android.content.Context;

import com.example.articlesapp.articles.di.ArticlesSubComponents;
import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;



@Singleton
@Component ( modules = {AppModule.class, ViewModelModule.class, AppSubComponentsModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance Builder application(Context context);
        AppComponent build();
    }


    ArticlesSubComponents.Builder articlesSubcomponents();


}
