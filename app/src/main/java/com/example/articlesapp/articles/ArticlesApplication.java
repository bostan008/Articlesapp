package com.example.articlesapp.articles;

import android.app.Application;

import com.example.articlesapp.di.AppComponent;
import com.example.articlesapp.di.DaggerAppComponent;

public class ArticlesApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

       mAppComponent =  DaggerAppComponent.builder().application(this).build();
    }


    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}
