package com.application.melanieh.kk.checkout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.application.melanieh.kk.models_and_modules.CartItem;

import io.reactivex.Observable;

/**
 * Created by melanieh on 2/27/18.
 */

public class RetainShoppingCartFragmentHelper {

    private static final String RETAIN_FRAGMENT_TAG = "retained_shopping_cart_fragment";

    /**
     * @return @Nullable ShoppingCartRetainedFragment.
     */
    @Nullable
    private static RetainShoppingCartFragment getInstance(FragmentManager fragmentManager, String tag) {
        //noinspection unchecked
        return (RetainShoppingCartFragment) fragmentManager.findFragmentByTag(tag);
    }

    /**
     * @return get Object or null if fragment has been cleaned up.
     */
    @Nullable
    public static Observable<CartItem> getObjectOrNull(@NonNull Object tag,
                                                          @NonNull FragmentManager fragmentManager) {
        final RetainShoppingCartFragment instance =
                RetainShoppingCartFragmentHelper.getInstance(fragmentManager, getTag(tag));
        if (instance == null) {
            return null;
        } else {
            return instance.getObject();
        }
    }

    public static void setObject(@NonNull Object tag,
                                     @NonNull FragmentManager fragmentManager,
                                     @NonNull Observable<CartItem> object) {

        RetainShoppingCartFragment instance = getInstance(fragmentManager, getTag(tag));
        if (instance == null) {
            instance = RetainShoppingCartFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(instance, getTag(tag))
                    .commit();
        }
        instance.setObject(object);
    }

    @NonNull
    private static String getTag(@NonNull Object object) {
        return RETAIN_FRAGMENT_TAG + object.getClass().getCanonicalName();
    }
}
