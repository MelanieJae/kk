package com.application.melanieh.kk.shopping;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models.CartItem;
import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class ProductDetailActivity extends AppCompatActivity {

//    @OnClick(R.id.add_to_cart_fab)
//    public void onClick() {
//        publishUpdatedCart(updateCart(cartItem));
//    }

    /**
     * event bus for publishing cart updates when the add to cart FAB is pushed
     */
    EventBus eventBus;

    CartItem cartItem;
    Cart cart;
    CartManager cartManager;
    FloatingActionButton addToCartFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        setContentView(R.layout.activity_product_detail);
        addToCartFab = (FloatingActionButton)findViewById(R.id.add_to_cart_fab);
        addToCartFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishUpdatedCart(updateCart(cartItem));
            }
        });

    }


    /**
     * RxAndroid event bus for cart update pub-sub pattern; GP Cart is the pub and the pay with stripe button
     * and Android Pay button fragments are the subs. The eventbus singleton is used by the Pay with Stripe and Android Pay btn
     * fragments.
     */

    // TODO: implement with Dagger
    public EventBus getEventBusSingleton() {
        if (eventBus == null) {
            eventBus = EventBus.instanceOf();
        }

        return eventBus;
    }

    private Cart updateCart(CartItem cartItem) {
        // transfer items from customized cart to a Stripe Cart Manager object
        cartManager = new CartManager();
        // TODO: change to retrieveCartItem() call once that method code is added
        cartManager.addLineItem
                ("Candle", 5, Long.parseLong("20"));

        // Add a shipping line item
        cartManager.addShippingLineItem(Constants.DOMESTIC_SHIP_EST_KEY,
                Long.parseLong("2"));
        // Set the tax line item - there can be only one;
        // TODO: change to getTax() call once taxes are set elsewhere
        cartManager.setTaxLineItem("Tax",
                Long.parseLong("2"));
        Timber.d("CartManager: " + cartManager.toString());


        /** make sure a valid Google Play Services/Android pay cart can be created from these line items
         * */

        try {
            Cart cart = cartManager.buildCart();
            Timber.d("Cart: " + cart.toString());
            // publishes cart for subscribers/observers, i.e. the pay button fragments
//            if (eventBus.hasObservers()) {
//                eventBus.send(new CartUpdateEvent());
//            }
            return cart;
        } catch (CartContentException unexpected) {
            Timber.wtf(unexpected,
                    "Valid cart cannot be created. " +
                            "Bad line items detected or bad total price string for the cart");
            return null;
        }
    }


    public void publishUpdatedCart(Cart currentCart) {}


}
