package com.application.melanieh.kk;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.stripe.wrap.pay.AndroidPayConfiguration;

/**
 * Created by melanieh on 5/22/17.
 */

public class KKApplication extends Application {

    private Tracker tracker;
    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.create();

    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    // Google Analytics tracker
    public void startTracking() {

        /** Google Analytics tracker */
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

    // Android Pay config instance
    public AndroidPayConfiguration initializeAndroidPayConfig() {
        return AndroidPayConfiguration.init(BuildConfig.STRIPE_TEST_PUBLISHABLE_KEY, "USD");
    }


}
