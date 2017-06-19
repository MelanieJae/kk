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

        // initialize Android Pay configuration; must be done before accessing singleton
        ((KKApplication) getApplication()).initializeAndroidPayConfig();

        // require user to enter shipping address
        AndroidPayConfiguration.getInstance().setShippingAddressRequired(true);

        // transfer items from customized cart to a Stripe Cart Manager object
        ArrayList<CartItem> cartItems = getIntent().getParcelableArrayListExtra(Constants.CART_ITEMS_KEY);
        CartManager cartManager = new CartManager();

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem currentCartItem = cartItems.get(i);
            cartManager.addLineItem
                    (currentCartItem.getItemName(),
                            currentCartItem.getItemQty(),
                            Long.parseLong("" + currentCartItem.getItemUnitPrice()));

            // Add a shipping line item
            cartManager.addShippingLineItem(Constants.DOMESTIC_SHIP_EST_KEY,
                    Long.parseLong("" + currentCartItem.getShippingEstimate()));
            // Set the tax line item - there can be only one;
            // TODO: CHANGE POINTER TO JUST getUnitPrice once taxes are set elsewhere
            cartManager.setTaxLineItem("Tax",
                    Long.parseLong("" + 0.07 * currentCartItem.getItemUnitPrice()));
        }

        /** make sure a valid Google Play Services/Android pay cart can be created from these line items
         * */

        try {
            cart = cartManager.buildCart();
        } catch (CartContentException unexpected) {
            Timber.wtf(unexpected,
                    "Valid cart cannot be created. Bad line items detected or bad total price string for the cart");
        }

    }

    @Override
    protected void onAndroidPayAvailable() {
        Intent intent = new Intent(this, AndroidPaywStripeActivity.class)
                .putExtra(StripeAndroidPayActivity.EXTRA_CART, cart);
        startActivity(intent);
    }

    @Override
    protected void onAndroidPayNotAvailable() {

    }
}
