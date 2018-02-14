package com.application.melanieh.kk.models_and_modules;

import com.application.melanieh.kk.BuildConfig;
import com.stripe.wrap.pay.AndroidPayConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by melanieh on 1/31/18.
 *
 * Dagger module for all application singletons *except* the event bus singleton
 */
@Module
public class SingletonsModule {

    // Android Pay Configuration singleton
    @Provides
    @Singleton
    public AndroidPayConfiguration provideAndroidPayConfigInstance() {
        return AndroidPayConfiguration.init(BuildConfig.STRIPE_TEST_PUBLISHABLE_KEY, "USD");
    }


}
