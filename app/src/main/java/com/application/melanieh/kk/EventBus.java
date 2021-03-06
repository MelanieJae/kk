package com.application.melanieh.kk;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by melanieh on 1/16/18.
 */

@Singleton
public class EventBus {

    @Inject
    public EventBus() {}

    private final PublishSubject<Object> _bus = PublishSubject.create();

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }
}




