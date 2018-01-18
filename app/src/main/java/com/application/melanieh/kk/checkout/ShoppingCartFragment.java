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

import com.application.melanieh.kk.EventBusSingleton;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models.CartItem;
import com.application.melanieh.kk.shopping.AddToCartBtnFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.flowables.ConnectableFlowable;
import timber.log.Timber;

/**
 * Created by melanieh on 6/21/17.
 */

public class ShoppingCartFragment extends Fragment {

    ArrayList<CartItem> cartItems;

    @BindView(R.id.cart_item_rv)
    RecyclerView cartItemRV;
    private CompositeDisposable disposables;
    @BindView(R.id.rv_emptyview_text)
    TextView emptyViewTV;

    // this class is an observer/subscriber to cart update events (observable)
    // published by the bus in the AddToCartFragment

    EventBusSingleton _bus;

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
    public void onStart() {
        super.onStart();
        disposables = new CompositeDisposable();
        _bus = EventBusSingleton.instance;
        Timber.d("ShoppingCartFrag: Event Bus: " + _bus);
        ConnectableFlowable<Object> tapEventEmitter = _bus.asFlowable().publish();
        disposables
                .add(
                        tapEventEmitter.subscribe(
                                event -> {
                                    if (event instanceof AddToCartBtnFragment.TapEvent) {

                                    }}));

        // this publishes a tapeventemitter event which causes only the tap count to display (which is the size of
        // an array called "taps" which contains all of the tap events available to a tapeventemitter subscriber)
        // after the word "tap" is displayed, which is the result of the subscription of the disposable (i.e. the tap button)
        // to the tapeventemitter, which is the publisher of any queued tap events.

        disposables.add(
                tapEventEmitter
                        .publish(stream -> stream.buffer(stream.debounce(1, TimeUnit.SECONDS)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                tap -> {
//                                    ArrayList<CartItem> allCartItems = parseCartFromStream(stream);
                                    displayCart(cartItems);
                                }));

        disposables.add(tapEventEmitter.connect());

//        // tap and count number won't show without this
    }


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(getActivity(), rootView);
        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//      _bus.unregister(lifecycle);
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

    private ArrayList<CartItem> parseCartFromStream (Stream<Object> streamFromBus) {
        ArrayList<CartItem> cart = new ArrayList<>();
        // TODO: add logic
        return cart;
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


}
