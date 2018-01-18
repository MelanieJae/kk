package com.application.melanieh.kk.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.EventBusSingleton;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models.CartItem;
import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by melanieh on 6/19/17.
 */

public class AddToCartBtnFragment extends android.app.Fragment {

    CartManager cartManager;
    CartItem cartItem;
    Button addToCartButton;
    // cart update event bus

    @BindView(R.id.eventbus_test)
    TextView eventBusTestTV;
    @BindView(R.id.add_to_cart_btn)
    Button addToCartBtn;
//    @OnClick(R.id.add_to_cart_btn)
//    public void onClick(View view) {
//        updateCart();
//    }
    EventBusSingleton _bus;

    public AddToCartBtnFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("AddToCartBtnFrag:OnCreateView");
        View rootView = inflater.inflate(R.layout.fragment_add_to_cart_btn, container, false);
        ButterKnife.bind(this, rootView);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem = retrieveCartItem();
                updateCart(cartItem);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getTaxAmt() {

    }

    private void getDomesticShippingEstimate() {}

    private CartItem retrieveCartItem() {
        Bundle args = getArguments();
        CartItem incomingCartItem =
                args.getParcelable(Constants.CART_ITEM_DATA_KEY);
        return incomingCartItem;
    }

    private void updateCart(CartItem cartItem) {
        Timber.d("updateCart()");
        Timber.d("cartItem: " + cartItem);
        _bus = EventBusSingleton.instance;
        Timber.d("updateCart: EventBus:" + _bus);
        // checks to see if button has observers/subscribers (it does, the shopping cart fragment)
        // and emits a new tap event upon
        // a tap of the button by the user
        if (_bus.hasObservers()) {
            _bus.send(new AddToCartBtnFragment.TapEvent());
            _bus.send(cartItem);
            Timber.d("Tap event emitted");
        } else {
            Timber.d("No observers found, tap event not emitted");
        }
    }

    public Cart addFeesToCart(CartItem cartItem) {
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


        /** make sure a valid Google Play Services/Android pay cart can be created from
         * these line items
         * */

        try {
            Cart cart = cartManager.buildCart();
            Timber.d("Cart: " + cart.toString());

            return cart;
        } catch (CartContentException unexpected) {
            Timber.wtf(unexpected,
                    "Valid cart cannot be created. " +
                            "Bad line items detected or bad total price string for the cart");
            return null;
        }

    }

    public static class TapEvent {}
}

