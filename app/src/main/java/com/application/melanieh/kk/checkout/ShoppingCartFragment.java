package com.application.melanieh.kk.checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.shopping.SetUpAndroidPayBtnFragment;
import com.google.android.gms.wallet.Cart;
import com.application.melanieh.kk.R;

/**
 * Created by melanieh on 6/21/17.
 */

public class ShoppingCartFragment extends Fragment {


    EventBus eventBus;


    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    public ShoppingCartFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // eventbus for cart updates
        eventBus = EventBus.instanceOf();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        eventBus.unregister(lifecycle);
    }
}
