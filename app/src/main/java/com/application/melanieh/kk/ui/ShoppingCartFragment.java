package com.application.melanieh.kk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.melanieh.kk.CartItem;
import com.application.melanieh.kk.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by melanieh on 6/8/17.
 */

public class ShoppingCartFragment extends Fragment {

    @BindView(R.id.cart_item_rv)
    RecyclerView cart_item_rv;

    RecyclerView.LayoutManager rvLayoutManager;
    CartItemRVAdapter cartItemRVAdapter;
    ArrayList<CartItem> cartItems;

    public ShoppingCartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        //TODO: retrieve cart items from sharedprefs or arraylist loaded when add to cart is called
        ButterKnife.bind(this, rootView);
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("itemName", 10, 1.00, 10.00, "set of 10"));
        cartItemRVAdapter = new CartItemRVAdapter(getContext(), cartItems);
        cart_item_rv.setLayoutManager(getLayoutManager());
        cart_item_rv.setAdapter(cartItemRVAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private RecyclerView.LayoutManager getLayoutManager() {
        int spanCount = 0;
        int screenWidth = getResources().getConfiguration().screenWidthDp;

        if (screenWidth < 900) {
            spanCount = 1;
        } else {
            spanCount = 2;
        }

        GridLayoutManager glm = new GridLayoutManager(getActivity(), spanCount);
        return glm;
    }


    /**
     * Created by melanieh on 6/8/17.
     */

    public static class CartItemRVAdapter extends RecyclerView.Adapter<CartItemRVAdapter.CartItemViewHolder> {

        public Context context;
        public ArrayList<CartItem> cartItems;

        public CartItemRVAdapter(Context context, ArrayList<CartItem> cartItems) {
            this.context = context;
            this.cartItems = cartItems;
        }

        @Override
        public CartItemRVAdapter.CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedItemView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
            return new CartItemViewHolder(inflatedItemView);
        }

        @Override
        public void onBindViewHolder(CartItemRVAdapter.CartItemViewHolder holder, int position) {
            holder.cartItem = cartItems.get(position);
            String itemName = holder.cartItem.getItemName();
            double itemQty = holder.cartItem.getItemQty();
            double totalItemPrice = holder.cartItem.getTotalItemPrice();
            String customerNotes = holder.cartItem.getCustomerNotes();

            holder.itemName.setText(itemName);
            holder.itemQtyValue.setText("" + itemQty);
            holder.totalItemPriceValue.setText("$ " + totalItemPrice);
            holder.customerNotes.setText("Customer Notes: \n" + customerNotes);
        }

        @Override
        public int getItemCount() {
            return cartItems.size();
        }

        /**
         * Created by melanieh on 6/8/17.
         */

        public static class CartItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.item_name)
            TextView itemName;
            @BindView(R.id.qty_value)
            TextView itemQtyValue;
            @BindView(R.id.total_item_price_value)
            TextView totalItemPriceValue;
            @BindView(R.id.cust_requests_notes)
            TextView customerNotes;

            private CartItem cartItem;

            public CartItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}
