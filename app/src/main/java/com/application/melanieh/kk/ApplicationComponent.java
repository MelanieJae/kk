package com.application.melanieh.kk;

import com.application.melanieh.kk.models_and_modules.BusModule;
import com.application.melanieh.kk.shopping.AddToCartBtnFragment;
import com.application.melanieh.kk.shopping.ProductDetailFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by melanieh on 1/22/18.
 */

@Singleton
@Component(modules = {BusModule.class})
public interface ApplicationComponent {

    EventBus getBus();
    void inject(AddToCartBtnFragment addToCartBtnFragment);
    void inject(ProductDetailFragment productDetailFragment);

}
