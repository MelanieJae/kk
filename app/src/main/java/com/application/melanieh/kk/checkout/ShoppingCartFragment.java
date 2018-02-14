package com.application.melanieh.kk.checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.melanieh.kk.CartItemRVAdapter;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.CartItem;
import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.ReplaySubject;
import timber.log.Timber;

/**
 * Created by melanieh on 6/21/17.
 */

public class ShoppingCartFragment extends Fragment {

    ArrayList<CartItem> cartItems;
    @BindView(R.id.cart_item_rv)
    RecyclerView cartItemRV;
    @BindView(R.id.rv_emptyview_text)
    TextView emptyViewTV;
    CartManager cartManager;

    CartItemRVAdapter rvAdapter;

    // this class is an observer/subscriber to cart update events (observable)
    // published by the bus in the AddToCartFragment

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
        cartItems = new ArrayList<>();

    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, rootView);
        displayCart();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();




    }

    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * RxAndroid event bus for cart update pub-sub pattern; GP Cart is the pub and the pay with stripe button
     * and Android Pay button fragments are the subs. The eventbus singleton is used by the Pay with Stripe and Android Pay btn
     * fragments.
     */

    // TODO: implement with Dagger

    private Cart assembleStripeCart(ArrayList cartList) {

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


        /** TODO: make sure a valid Google Play Services/Android pay cart can be created from these line items
         * */

        try {
            Cart stripeCartObject = cartManager.buildCart();
            Timber.d("Cart: " + stripeCartObject.toString());
            return stripeCartObject;
        } catch (CartContentException unexpected) {
            Timber.wtf(unexpected,
                    "Valid cart cannot be created. " +
                            "Bad line items detected or bad total price string for the cart");
            return null;
        }
    }

    private void displayCart() {

        ReplaySubject<CartItem> cartItemObservable = KKApplication.getObservable();
        Timber.d("cartItemObservable: " + cartItemObservable.toString());
        Consumer<CartItem> cartItemConsumer = new Consumer<CartItem>() {
            @Override
            public void accept(CartItem cartItem) throws Exception {
                cartItems.add(cartItem);

            }
        };

        cartItemObservable.subscribe(cartItemConsumer);
        rvAdapter = new CartItemRVAdapter(getContext(), cartItems);
        cartItemRV.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemRV.setAdapter(rvAdapter);

    }

}
