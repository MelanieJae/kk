package com.application.melanieh.kk.models_and_modules;

import com.application.melanieh.kk.KKApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by melanieh on 1/22/18.
 *
 * Dagger module for cart item singleton injection; part of Dagger/RxJava event bus for cart updates.
 */

@Module
public class BusModule {

    KKApplication application;

    public BusModule(KKApplication application) {
        this.application = application;
    }

    // cart item eventbus singleton
    @Provides
    @Singleton
    PublishSubject<CartItem> provideCartItemObservable() { return PublishSubject.create(); }


}
