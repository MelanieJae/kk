package com.application.melanieh.kk;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by melanieh on 1/16/18.
 */

@Singleton
public class EventBusSingleton {

    public static final EventBusSingleton instance = new EventBusSingleton();
    /** Code courtesy of: https://gist.github.com/benjchristensen/04eef9ca0851f3a5d7bf */

    private final Relay<Object> _bus = PublishRelay.create().toSerialized();

    public void send(Object o) {
        _bus.accept(o);
    }

    public Flowable<Object> asFlowable() {
        return _bus.toFlowable(BackpressureStrategy.LATEST);
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }


}




