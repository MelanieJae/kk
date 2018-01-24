package com.application.melanieh.kk.models_and_modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by melanieh on 1/22/18.
 *
 * Dagger module for cart item singleton injection; part of Dagger/RxJava event bus for cart updates.
 */

@Module
public class BusModule {

    @Provides
    @Singleton
    static Flowable<CartItem> provideCartItem() {
        return Flowable.create((new FlowableOnSubscribe<CartItem>() {
            @Override
            public void subscribe(FlowableEmitter<CartItem> e) throws Exception {

            }
        }), BackpressureStrategy.LATEST);
    }
}
