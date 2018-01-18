package com.application.melanieh.kk.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.application.melanieh.kk.CartItemListener;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.EventBusSingleton;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.checkout.CheckoutActivity;
import com.application.melanieh.kk.models.CartItem;
import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class ProductDetailActivity extends AppCompatActivity implements CartItemListener{

    /**
     * event bus for publishing cart updates when the add to cart FAB is pushed
     */

    CartItem cartItem;
    Cart cart;
    CartManager cartManager;
    EventBusSingleton _bus;
    CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_universal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_cart:
                Intent launchCartView = new Intent(this, CheckoutActivity.class);
                startActivity(launchCartView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * RxAndroid event bus for cart update pub-sub pattern; GP Cart is the pub and the pay with stripe button
     * and Android Pay button fragments are the subs. The eventbus singleton is used by the Pay with Stripe and Android Pay btn
     * fragments.
     */

    // TODO: implement with Dagger

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

    public void publishUpdatedCart() {

    }

    @Override
    public void passCartItem(CartItem cartItem) {
        AddToCartBtnFragment updatedCartBtnFrag = new AddToCartBtnFragment ();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CART_ITEM_DATA_KEY, cartItem);
        updatedCartBtnFrag.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.addToCartBtnFragment,
                        updatedCartBtnFrag)
                .commit();
    }

    //    @Singleton
//    public EventBus getEventBusSingleton() {
//
//        if (_bus == null) {
//            _bus = new EventBus();
//            Timber.d("bus constructor: EventBus: " + _bus);
//        }
//
//        return _bus;
//    }

}
