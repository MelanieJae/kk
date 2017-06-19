package com.application.melanieh.kk.ui;

import android.support.v4.app.Fragment;

import com.stripe.android.view.CardInputWidget;

/**
 * Created by melanieh on 6/19/17.
 */

public class CardInputFragment extends Fragment implements CardInputWidget.CardInputListener{


    public CardInputFragment() {
        //
    }

    /* Stripe Card Input Listener methods */
    @Override
    public void onFocusChange(String s) {

    }

    @Override
    public void onCardComplete() {

    }

    @Override
    public void onExpirationComplete() {

    }

    @Override
    public void onCvcComplete() {

    }
}
