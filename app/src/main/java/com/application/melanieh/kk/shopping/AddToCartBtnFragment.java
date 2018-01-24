package com.application.melanieh.kk.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.CartItem;
import com.stripe.wrap.pay.utils.CartManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by melanieh on 6/19/17.
 */

public class AddToCartBtnFragment extends android.app.Fragment {

    ArrayList<CartItem> cartItems;
    CartManager cartManager;
    CartItem extractedCartItem;
    @BindView(R.id.eventbus_test)
    TextView eventBusTestTV;
    @BindView(R.id.add_to_cart_btn)
    Button addToCartBtn;

//    @OnClick(R.id.add_to_cart_btn)
//    public void onClick(View view) {
//        updateCart();
//    }

    public static AddToCartBtnFragment newInstance() {
        AddToCartBtnFragment fragment = new AddToCartBtnFragment();
        return fragment;
    }

    public AddToCartBtnFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume()");

    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_add_to_cart_btn, container, false);
        ButterKnife.bind(this, rootView);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCart();
            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        KKApplication.getApplicationComponent().getCartItem().unsubscribeOn(Schedulers.io());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getTaxAmt() {

    }

    private void getDomesticShippingEstimate() {}

    private void updateCart() {
        Timber.d("updateCart()");
        cartItems = new ArrayList<CartItem>();

        KKApplication.getApplicationComponent().getCartItem().subscribe();
        Timber.d("cartItems: " + cartItems);
        eventBusTestTV.append("" + cartItems);

    }


}

