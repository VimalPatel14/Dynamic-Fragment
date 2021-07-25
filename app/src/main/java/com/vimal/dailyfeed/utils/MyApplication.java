package com.vimal.dailyfeed.utils;

import android.app.Application;
import android.content.Context;

import com.vimal.dailyfeed.discretescrollview.DiscreteScrollViewOptions;

public class MyApplication extends Application {

    private static Context sContext;
    public static int bitmap_width = 720,bitmap_height = 1280;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=   getApplicationContext();

        instance = this;
        DiscreteScrollViewOptions.init(this);

    }

    public static Context getContext() {
        return sContext;
    }
}
