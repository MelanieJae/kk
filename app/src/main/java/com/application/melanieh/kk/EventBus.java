package com.application.melanieh.kk;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by melanieh on 1/16/18.
 */


public class EventBus {

    public EventBus() {}

    private final PublishSubject<Object> _bus = PublishSubject.create();

    public void send(Object o) {
        _bus.onNext(o);
    }

//    public Flowable<Object> asFlowable() {
//        return _bus.toFlowable(BackpressureStrategy.LATEST);
//    }

    public Observable<Object> toObservable() {
        return _bus;
    }


}




