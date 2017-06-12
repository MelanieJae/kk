package com.application.melanieh.kk;

import android.support.annotation.Nullable;

/**
 * Created by melanieh on 6/8/17.
 */

public class CartItem {

    // variables/attributes
    private String itemName;
    private int itemQty;
    private double itemUnitPrice;
    private double totalItemPrice;
    private String customerNotes;

    public CartItem(String itemName, int itemQty, double itemUnitPrice, double totalItemPrice,
                    @Nullable String customerNotes) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemUnitPrice = itemUnitPrice;
        this.totalItemPrice = totalItemPrice;
        this.customerNotes = customerNotes;
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

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }
}
