package com.example.articlesapp.di;


import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    public Map<Class<? extends ViewModel>, Provider<ViewModel>> providesMap(){
        return new HashMap<>();
    }
}
