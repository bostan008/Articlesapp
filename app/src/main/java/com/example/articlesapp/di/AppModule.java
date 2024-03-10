package com.example.articlesapp.di;


import com.example.articlesapp.utils.IsoDateAdapter;
import com.squareup.moshi.Moshi;

import java.util.Objects;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public Moshi providesMoshi() {

        Moshi moshi = new Moshi.Builder().add(new IsoDateAdapter()).build();
        return Objects.requireNonNull(moshi);
    }

}
