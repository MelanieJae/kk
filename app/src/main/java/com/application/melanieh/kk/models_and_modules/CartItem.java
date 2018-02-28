package com.application.melanieh.kk.models_and_modules;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by melanieh on 6/8/17.
 */

public class CartItem implements Parcelable {

    // variables/attributes
    private String itemName;
    private String itemVariety;
    private int itemQty;
    private double itemUnitPrice;
    private double shippingEstimate;
    String[] cartItemParamStrings = {itemName, itemVariety, "" + itemQty,
            "" + itemUnitPrice, "" + shippingEstimate};

    public CartItem(String itemName, String itemVariety,
                    int itemQty, double itemUnitPrice, double shippingEstimate) {
        this.itemName = itemName;
        this.itemVariety = itemVariety;
        this.itemQty = itemQty;
        this.itemUnitPrice = itemUnitPrice;
        this.shippingEstimate = shippingEstimate;
    }

    public String getItemVariety() {
        return itemVariety;
    }

    public void setItemVariety(String itemVariety) {
        this.itemVariety = itemVariety;
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

    public double getItemUnitPrice() { return itemUnitPrice; }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
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

    @Override
    public String toString() {
        return "CartItem{" +
                "itemName='" + itemName + '\'' +
                "itemVariety='" + itemVariety + '\'' +
                ", itemQty=" + itemQty + '\'' +
                ", itemUnitPrice=" + itemUnitPrice +
                ", shippingEstimate=" + shippingEstimate +
                ", cartItemParamStrings=" + Arrays.toString(cartItemParamStrings) +
                '}';
    }
}
