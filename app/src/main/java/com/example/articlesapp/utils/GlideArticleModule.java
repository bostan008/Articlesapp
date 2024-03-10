package com.example.articlesapp.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class GlideArticleModule extends AppGlideModule {


    @Override
    public void applyOptions(@NonNull Context context, GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        int bitmapPoolSizeBytes = 1024 * 1024 * 30; // 30mb
        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSizeBytes));

        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));

        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                /*.disallowHardwareBitmaps()*/);
    }
}
