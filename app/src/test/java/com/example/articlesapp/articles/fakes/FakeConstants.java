package com.example.articlesapp.articles.fakes;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeConstants {


    private FakeConstants() {

    }

    private static class SingletonFakesConstantsHolder {
        public static final FakeConstants instance = new FakeConstants();
    }

    public static FakeConstants getInstance() {
        return FakeConstants.SingletonFakesConstantsHolder.instance;
    }

    public List<Article> dummyArticles() {
        List<Article> articles = new ArrayList<>();
        try {
            articles.add(new Article(
                    "title1",
                    "",
                    "",
                    Utils.getInstance().simpleFormat.parse("Dec 1, 2023")
            ));
            articles.add(new Article(
                    "title2",
                    "",
                    "",
                    Utils.getInstance().simpleFormat.parse("Dec 2, 2023")
            ));
            articles.add(new Article(
                    "title3",
                    "",
                    "",
                    Utils.getInstance().simpleFormat.parse("Dec 3, 2023")
            ));
            articles.add(new Article(
                    "title4",
                    "",
                    "",
                    Utils.getInstance().simpleFormat.parse("Dec 4, 2023")
            ));
            articles.add(new Article(
                    "title5",
                    "",
                    "",
                    Utils.getInstance().simpleFormat.parse("Dec 5, 2023")
            ));
            articles.add(new Article(
                    "title6",
                    "",
                    "",
                    Utils.getInstance().simpleFormat.parse("Dec 6, 2023")
            ));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return articles;
    }

    public final String ERROR_MESSAGE = "Oops Something went wrong!";
}
