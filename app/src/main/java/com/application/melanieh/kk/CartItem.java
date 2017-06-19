package com.application.melanieh.kk;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.gms.wallet.Cart;

/**
 * Created by melanieh on 6/8/17.
 */

public class CartItem implements Parcelable {

    // variables/attributes
    private String itemName;
    private int itemQty;
    private double itemUnitPrice;
    private String customerNotes;
    private double shippingEstimate;
    String[] cartItemParamStrings = {itemName, "" + itemQty, "" + itemUnitPrice, "" + shippingEstimate, customerNotes};

    public CartItem(String itemName, int itemQty, double itemUnitPrice, String customerNotes, double shippingEstimate) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemUnitPrice = itemUnitPrice;
        this.customerNotes = customerNotes;
        this.shippingEstimate = shippingEstimate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public double getShippingEstimate() {
        return shippingEstimate;
    }

    public void setShippingEstimate(double shippingEstimate) {
        this.shippingEstimate = shippingEstimate;
    }

    // Parcelable interface methods

    //constructor
    public static final Parcelable.Creator<CartItem> CREATOR
            = new Parcelable.Creator<CartItem>() {
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    private CartItem(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        in.readStringArray(cartItemParamStrings);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

}
