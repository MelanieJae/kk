package com.application.melanieh.kk;

import com.application.melanieh.kk.models_and_modules.BusModule;
import com.application.melanieh.kk.models_and_modules.CartItem;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Flowable;

/**
 * Created by melanieh on 1/22/18.
 *
 * Dagger application component for Dagger/RxJava event bus for cart updates
 */

@Singleton
@Component(modules = {BusModule.class})
public interface ApplicationComponent {

    Flowable<CartItem> getCartItem();

}
