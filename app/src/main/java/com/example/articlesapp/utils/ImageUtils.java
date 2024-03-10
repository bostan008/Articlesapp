package com.example.articlesapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.articlesapp.R;

import java.util.Random;

public class ImageUtils implements ImageLoader {

    private static class SingletonGlideImageUtilsHolder {
        public static ImageUtils instance = new ImageUtils();
    }

    public static ImageUtils getInstance() {
        return SingletonGlideImageUtilsHolder.instance;
    }

    private final Random random = new Random();


    private ImageUtils() {
        setupImageLoader();
    }

    private void setupImageLoader() {

    }

    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    @Override
    public void loadImage(Context context, ImageView imageView, String url) {
        Glide
                .with(context)
                .load(url)
                .centerInside()
                .apply(new RequestOptions()
                        .override((int) Utils.getInstance().convertDpToPixel(width, context), (int) Utils.getInstance().convertDpToPixel(300, context))
                )
                .placeholder(R.drawable.ic_news_bg_1)
                .into(imageView);
    }


}
