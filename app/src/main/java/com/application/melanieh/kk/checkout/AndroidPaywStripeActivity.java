package com.application.melanieh.kk.checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;

/**
 * Created by melanieh on 6/19/17.
 */

public class AndroidPaywStripeActivity extends StripeAndroidPayActivity {

    Cart cart;

    public AndroidPaywStripeActivity() {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onAndroidPayAvailable() {

    }

    @Override
    protected void onAndroidPayNotAvailable() {

    }
}
