package com.application.melanieh.kk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.application.melanieh.kk.CartItem;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;
import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by melanieh on 6/19/17.
 */

public class AddToCartBtnFragment extends Fragment {

    CartManager cartManager;
    Button addToCartButton;

    Unbinder unbinder;
    @OnClick(R.id.add_to_cart_btn)
    public void onClick(View view) {}

    public AddToCartBtnFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_to_cart_btn, container, false);
//        unbinder = ButterKnife.bind(getActivity(), rootView);
        addToCartButton = (Button)rootView.findViewById(R.id.add_to_cart_btn);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    // send cart either to Stripe-only purchase flow or Android Pay

                } catch (CartContentException unexpected) {
                    Timber.wtf(unexpected,
                            "Valid cart cannot be created. Bad line items detected or bad total price string for the cart");
                }

                    }
                });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    private void getTaxAmt() {

    }

    private void getDomesticShippingEstimate() {}

    private CartItem retrieveCartItem() {
        //TODO: FINISH CODE
        return null;}
}
