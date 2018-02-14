package com.application.melanieh.kk;

import android.app.Application;

import com.application.melanieh.kk.models_and_modules.BusModule;
import com.application.melanieh.kk.models_and_modules.CartItem;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.stripe.wrap.pay.AndroidPayConfiguration;

import java.util.concurrent.Executors;

import io.reactivex.subjects.ReplaySubject;

/**
 * Created by melanieh on 5/22/17.
 */

public class KKApplication extends Application {

    private Tracker tracker;
    private static ApplicationComponent applicationComponent;
    private static Picasso picassoInstance;
    private static ReplaySubject<CartItem> observable;
    private static AndroidPayConfiguration androidPayConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().busModule(new BusModule(this)).build();
        picassoInstance = new Picasso.Builder(getApplicationContext()).executor(Executors.newSingleThreadExecutor())
                .memoryCache(Cache.NONE).indicatorsEnabled(false).build();
        observable = ReplaySubject.create();
        androidPayConfiguration = AndroidPayConfiguration.init(BuildConfig.ANDROID_PAY_CONFIG_KEY,
                Constants.CURRENCY_CODE_USD);


    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    // Google Analytics tracking
    // needs application context, so it cannot be placed in the singletons module

    public void provideGATrackerInstance() {

        if (tracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(this);

            // Get the config data for the tracker
            tracker = ga.newTracker(R.xml.analytics_tracker);

            // enable auto-tracking
            ga.enableAutoActivityReports(this);

            // enable auto-logging
            ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

    public static Picasso getPicassoInstance() {
        return picassoInstance;
    }

    public static ReplaySubject<CartItem> getObservable() {
        return observable;
    }

    public static AndroidPayConfiguration initAndroidPayConfig() {
        return androidPayConfiguration;
    }

}






