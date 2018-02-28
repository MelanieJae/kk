package com.application.melanieh.kk.checkout;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.application.melanieh.kk.models_and_modules.CartItem;

import io.reactivex.Observable;

/**
 * Created by melanieh on 6/21/17. This is a retained fragment to ensure
 * persistence of the shopping cart upon screen rotation by the user.
 */

public class RetainShoppingCartFragment extends Fragment {


    public static RetainShoppingCartFragment newInstance() {
        RetainShoppingCartFragment fragment = new RetainShoppingCartFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public RetainShoppingCartFragment() {
        //
    }


    private Observable<CartItem> object;

    @NonNull
    public Observable<CartItem> getObject() {
        return object;
    }

    public void setObject(@NonNull Observable<CartItem> object) {
        this.object = object;
    }

}
