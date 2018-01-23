package com.application.melanieh.kk.models_and_modules;

import com.application.melanieh.kk.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by melanieh on 1/22/18.
 *
 * Dagger module for eventbus singleton injection
 */

@Module
public class BusModule {

    @Provides
    @Singleton
    EventBus provideBus() {
        return new EventBus();
    }
}
