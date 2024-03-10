package com.example.articlesapp.utils;

import androidx.annotation.WorkerThread;

import java.io.IOException;
import java.text.ParseException;

public interface StorageProvider<T> {

    @WorkerThread
    T readFromStorage();
}
