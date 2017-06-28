package com.application.melanieh.kk;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import org.reactivestreams.Subscription;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import hu.akarnokd.rxjava2.util.CompositeSubscription;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Used for subscribing to and publishing to subjects. Allowing you to send data between activities, fragments, etc.
 * <p>
 * Created by Pierce Zaifman on 2017-01-02. The code can be found here:
 * https://dzone.com/articles/how-to-make-an-event-bus-with-rxjava-and-rxandroid
 */

public final class EventBus {
    private static SparseArray<PublishSubject<Object>> sSubjectMap = new SparseArray<>();
    private static Map<Object, CompositeSubscription> sSubscriptionsMap = new HashMap<>();

//    @Retention(SOURCE)
    @IntDef({Constants.SUBJECT_CART_UPDATE, Constants.SUBJECT_INVOICE_UPDATE})
    @interface Subject {
    }

    public static EventBus instanceOf() {
        return new EventBus();
    }
    private EventBus() {
        // hidden constructor
    }
    /**
     * Get the subject or create it if it's not already in memory.
     */
    @NonNull
    private static PublishSubject<Object> getSubject(@Subject int subjectCode) {
        PublishSubject<Object> subject = sSubjectMap.get(subjectCode);
        if (subject == null) {
            subject = PublishSubject.create();
            subject.subscribeOn(AndroidSchedulers.mainThread());
            sSubjectMap.put(subjectCode, subject);
        }
        return subject;
    }
    /**
     * Get the CompositeSubscription or create it if it's not already in memory.
     */
    @NonNull
    private static CompositeSubscription getCompositeSubscription(@NonNull Object object) {
        CompositeSubscription compositeSubscription = sSubscriptionsMap.get(object);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
            sSubscriptionsMap.put(object, compositeSubscription);
        }
        return compositeSubscription;
    }
    /**
     * Subscribe to the specified subject and listen for updates on that subject. Pass in an object to associate
     * your registration with, so that you can unsubscribe later.
     * <br/><br/>
     * <b>Note:</b> Make sure to call {@link EventBus#unregister(Object)} to avoid memory leaks.
     */
    public static void subscribe(@Subject int subject, @NonNull Object lifecycle,
                                 @NonNull ArrayList<Object> action) {
//        Subscription subscription = getSubject(subject).subscribe("");
//        getCompositeSubscription(lifecycle).add(subscription);
    }
    /**
     * Unregisters this object from the bus, removing all subscriptions.
     * This should be called when the object is going to go out of memory.
     */
    public static void unregister(@NonNull Object lifecycle) {
        //We have to remove the composition from the map, because once you unsubscribe it can't be used anymore
        CompositeSubscription compositeSubscription = sSubscriptionsMap.get(lifecycle);
//        if (sSubscriptionsMap.get(lifecycle) != null) {
//            Iterator keysetIterator = sSubscriptionsMap.keySet().iterator();
//            while (keysetIterator.hasNext()) {
//                (keysetIterator.next()).unsubscribe();
//        }
    }
    /**
     * Publish an object to the specified subject for all subscribers of that subject.
     */
    public static void publish(@Subject int subject, @NonNull Object message) {
        getSubject(subject).onNext(message);
    }
}