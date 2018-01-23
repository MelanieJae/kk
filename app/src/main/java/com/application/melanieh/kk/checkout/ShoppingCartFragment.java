package com.application.melanieh.kk.checkout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.CartItem;
import com.application.melanieh.kk.models_and_modules.Event;
import com.google.android.gms.wallet.Cart;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by melanieh on 6/21/17.
 */

public class ShoppingCartFragment extends Fragment {

    ArrayList<CartItem> cartItems;
    @Inject
    EventBus bus;

    @BindView(R.id.cart_item_rv)
    RecyclerView cartItemRV;
    private CompositeDisposable disposables;
    @BindView(R.id.rv_emptyview_text)
    TextView emptyViewTV;
    CartManager cartManager;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        bus
                .toObservable()
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(getActivity(), rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.toObservable()
                .unsubscribeOn(Schedulers.io());
    }


    /**
     * Created by melanieh on 6/28/17.
     */

    public static class CartItemRVAdapter
            extends RecyclerView.Adapter<CartItemRVAdapter.CartItemViewHolder> {

        private Context context;
        private ArrayList<CartItem> cartItems;

        public CartItemRVAdapter(Context context, ArrayList<CartItem> cartItems) {
            this.context = context;
            this.cartItems = cartItems;
        }

        @Override
        public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Timber.d("onCreateViewHolder:");

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.cart_item, parent, false);
            return new CartItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder
                (final CartItemViewHolder holder, final int position) {
            Timber.d("onBindViewHolder:");
            Timber.d("holder.itemqtyvalue" + holder.itemQtyValue);
            holder.cartItem = cartItems.get(position);
            Timber.d("holder.cartItem.qty" + holder.cartItem.getItemQty());
            final String productName = holder.cartItem.getItemName();
            final String qty = "" + holder.cartItem.getItemQty();
            final String price = "" + holder.cartItem.getItemUnitPrice() * holder.cartItem.getItemQty();

//            ImageHandler.getSharedInstance(holder.itemView.getContext()).load(imageUrlString).
//                    fit().centerCrop().into(holder.productIV);
            holder.itemNameValue.setText(holder.cartItem.getItemName());
            holder.itemQtyValue.setText(holder.cartItem.getItemQty());
            holder.itemUnitPriceValue.setText("" + holder.cartItem.getItemUnitPrice());
            holder.itemTotalPriceValue.setText("" + holder.cartItem.getItemUnitPrice() *
                    holder.cartItem.getItemQty());
            holder.customerNotesValue.setText(holder.cartItem.getCustomerNotes());
        }

        @Override
        public int getItemCount() {
            Timber.d("getItemCount:" + cartItems.size());
            return cartItems.size();
        }

        /**
         * Created by melanieh on 6/28/17.
         */

        public static class CartItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.itemNameValue)
            TextView itemNameValue;
            @BindView(R.id.qty_value)
            TextView itemQtyValue;
            @BindView(R.id.itemUnitPriceValue)
            TextView itemUnitPriceValue;
            @BindView(R.id.itemTotalPriceValue)
            TextView itemTotalPriceValue;
            @BindView(R.id.customerNotesValue)
            TextView customerNotesValue;

            private CartItem cartItem;

            public CartItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public void displayCart(ArrayList<CartItem> cartItems) {
        //todo: finish code for loading cart item info into recyclerview/arraylist
        if (cartItems == null) {
            emptyViewTV.setText("Your cart is empty.");
        } else {
            // this is temporary until eventbus is working
//            cartItems.add
//                    (new CartItem("tealights", 10, 1, "set of 10", 1.0));
            CartItemRVAdapter rvAdapter = new CartItemRVAdapter(getContext(), cartItems);
            cartItemRV.setLayoutManager(new LinearLayoutManager(getContext()));
            cartItemRV.setAdapter(rvAdapter);
        }
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

}
