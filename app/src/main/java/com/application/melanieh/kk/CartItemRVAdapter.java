package com.application.melanieh.kk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.melanieh.kk.models_and_modules.CartItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by melanieh on 1/30/18.
 */

/**
 * Created by melanieh on 6/28/17.
 */

public class CartItemRVAdapter
        extends RecyclerView.Adapter<CartItemRVAdapter.CartItemViewHolder> {

    private Context context;
    private ArrayList<CartItem> cartItems;

    public CartItemRVAdapter(Context context, ArrayList<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    public void setCartItem(CartItem newItem) {
        cartItems.clear();
        cartItems.add(newItem);
        notifyDataSetChanged();
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       Timber.d("onCreateViewHolder:");
        View view = LayoutInflater.from(context)
              .inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        Timber.d("onBindViewHolder:");
        holder.cartItem = cartItems.get(position);

//        ImageHandler.getSharedInstance(holder.itemView.getContext()).load(imageUrlString).
//                fit().centerCrop().into(holder.productIV);
        holder.itemNameValue.setText(holder.cartItem.getItemName());
        holder.itemVarietyValue.setText(holder.cartItem.getItemVariety());
        holder.itemQtyValue.setText("" + holder.cartItem.getItemQty());
        holder.itemTotalPriceValue.setText
                ("$" + holder.cartItem.getItemUnitPrice() * holder.cartItem.getItemQty());
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

        @BindView(R.id.qtyLabel)
        TextView qtyLabel;
        @BindView(R.id.itemTotalPriceLabel)
        TextView itemTotalPriceLabel;
        @BindView(R.id.itemNameValue)
        TextView itemNameValue;
        @BindView(R.id.itemVarietyValue)
        TextView itemVarietyValue;
        @BindView(R.id.qtyValue)
        TextView itemQtyValue;
        @BindView(R.id.itemTotalPriceValue)
        TextView itemTotalPriceValue;

        private CartItem cartItem;

        public CartItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
