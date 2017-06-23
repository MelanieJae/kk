package com.application.melanieh.kk.commerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.application.melanieh.kk.CartItem;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.KKApplication;
import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.AndroidPayConfiguration;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import java.util.ArrayList;

import timber.log.Timber;

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
