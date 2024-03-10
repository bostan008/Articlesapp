package com.example.articlesapp.articles.presentation;

public enum ArticleStates {

    INITIAL_STATE,

    CONTENT_LOADED_STATE,
    SEARCH_CONTENT_LOADED_STATE,
    SEARCH_EMPTY_QUERY_STATE,
    SEARCH_NO_RESULTS_FOUND_STATE,
    GENERAL_ERROR_STATE,
    SEARCH_ERROR_STATE,
    UNKNOWN_STATE
}
