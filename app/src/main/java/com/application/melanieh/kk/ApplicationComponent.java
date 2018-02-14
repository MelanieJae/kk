package com.application.melanieh.kk;

import com.application.melanieh.kk.models_and_modules.BusModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by melanieh on 1/22/18.
 *
 * Dagger application component for Dagger/RxJava event bus for cart updates
 */

@Singleton
@Component(modules = {BusModule.class})
public interface ApplicationComponent {

//    void inject(ProductDetailFragment productDetailFragment);
//    void inject(AddToCartBtnFragment addToCartBtnFragment);

}
