package com.example.articlesapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.articlesapp.articles.data.Article;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Observable;

public class Utils {

    private Utils() {
    }

    private static class SingletonUtilsHolder {
        public static final Utils instance = new Utils();
    }

    public static Utils getInstance() {
        return SingletonUtilsHolder.instance;
    }

    public Moshi getMoshi() {
        return new Moshi.Builder().add(new IsoDateAdapter()).build();
    }

    public String readJsonFromAssets(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Log.e(Utils.class.getSimpleName(), "Parsing failed!");
            return "";
        }
    }


    public final SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public Observable<List<Article>> searchArticles(List<Article> articles, String query) {

        return Observable.create(emitter -> {
            List<Article> filterResults = new ArrayList<>();
            for (Article article : articles) {
                if (article.title.toLowerCase().contains(query.toLowerCase())) {
                    filterResults.add(article);
                }
            }
            emitter.onNext(filterResults);
            emitter.onComplete();
        });
    }

}
