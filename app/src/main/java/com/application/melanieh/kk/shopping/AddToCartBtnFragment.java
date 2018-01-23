package com.application.melanieh.kk.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.melanieh.kk.ApplicationComponent;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.DaggerApplicationComponent;
import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.CartItem;
import com.application.melanieh.kk.models_and_modules.Event;
import com.stripe.wrap.pay.utils.CartManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by melanieh on 6/19/17.
 */

public class AddToCartBtnFragment extends android.app.Fragment {

    CartManager cartManager;
    static CartItem cartItem;
    ApplicationComponent applicationComponent;
    @Inject EventBus bus;

    @BindView(R.id.eventbus_test)
    TextView eventBusTestTV;
    @BindView(R.id.add_to_cart_btn)
    Button addToCartBtn;
//    @OnClick(R.id.add_to_cart_btn)
//    public void onClick(View view) {
//        updateCart();
//    }

    public static AddToCartBtnFragment newInstance(CartItem incomingCartItem) {
        Timber.d("cart item: " + incomingCartItem);
        cartItem = incomingCartItem;
        Bundle args = new Bundle();
        args.putParcelable(Constants.CART_ITEMS_DATA_KEY, incomingCartItem);
        AddToCartBtnFragment fragment = new AddToCartBtnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddToCartBtnFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
//        Bundle args = getArguments();
//        CartItem incomingCartItem =
//                args.getParcelable(Constants.CART_ITEMS_DATA_KEY);
//        Timber.d("incoming cart item: " + incomingCartItem);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.send(new Event.NewCartItemEvent());
        Timber.d("event emitted");

    }

    @Override
    public void onResume() {
        super.onResume();
        bus.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o instanceof Event.NewCartItemEvent) {
                            Timber.d("observer accepts emitted NewCartItemEvent");
                        } else {
                            Timber.wtf("Exception thrown");
                        }
                    }
                });
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
                updateCart();
            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.toObservable().unsubscribeOn(Schedulers.io());

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
        bus.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o instanceof Event.NewCartItemEvent) {
                            Timber.d("observer accepts emitted NewCartItemEvent");
                        } else {
                            Timber.wtf("Exception thrown");
                        }
                    }
                });

    }


}

