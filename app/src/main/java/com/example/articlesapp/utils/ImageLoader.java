package com.example.articlesapp.utils;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLoader {

    void loadImage(Context context, ImageView imageView, String url);
}
