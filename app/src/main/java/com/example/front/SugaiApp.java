package com.example.front;

import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class SugaiApp  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
