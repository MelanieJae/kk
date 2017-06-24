package com.application.melanieh.kk.shopping;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

/**
 * Created by melanieh on 5/22/17.
 */

public class ImageHandler {

    private static Picasso instance;

    // tells Volley to cache images in memory when using the image loader
    private final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(20);
    private ImageLoader mImageLoader;

    public static Picasso getSharedInstance(Context context)
    {
        if(instance == null)
        {
            instance =
                    new Picasso.Builder(context).executor(Executors.newSingleThreadExecutor())
                            .memoryCache(Cache.NONE).indicatorsEnabled(false).build();
        }
        return instance;
    }


    public ImageHandler(Context applicationContext) {
        RequestQueue queue = Volley.newRequestQueue(applicationContext);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                mImageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return mImageCache.get(key);
            }
        };
        mImageLoader = new ImageLoader(queue, imageCache);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}