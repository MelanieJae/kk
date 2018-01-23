package com.application.melanieh.kk.models_and_modules;

import java.util.ArrayList;

/**
 * Created by melanieh on 6/26/17.
 */

public class Invoice {

    private ArrayList<CartItem> invoiceLineItems;
    private String invoiceID;
    private String shippingAddress;
    private String billingAddress;
    private String subtotal;
    private String shippingLineItem;
    private String taxesLineItem;
    private String totalLineItem;
    private String customerNotes;

    public Invoice(ArrayList<CartItem> invoiceLineItems,
                   String invoiceID, String shippingAddress, String billingAddress,
                   String subtotal, String shippingLineItem, String taxesLineItem,
                   String totalLineItem, String customerNotes) {
        this.invoiceLineItems = invoiceLineItems;
        this.invoiceID = invoiceID;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.subtotal = subtotal;
        this.shippingLineItem = shippingLineItem;
        this.taxesLineItem = taxesLineItem;
        this.totalLineItem = totalLineItem;
        this.customerNotes = customerNotes;
    }

    public ArrayList<CartItem> getInvoiceLineItems() {
        return invoiceLineItems;
    }

    public void setInvoiceLineItems(ArrayList<CartItem> invoiceLineItems) {
        this.invoiceLineItems = invoiceLineItems;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getShippingLineItem() {
        return shippingLineItem;
    }

    public void setShippingLineItem(String shippingLineItem) {
        this.shippingLineItem = shippingLineItem;
    }

    public String getTaxesLineItem() {
        return taxesLineItem;
    }

    public void setTaxesLineItem(String taxesLineItem) {
        this.taxesLineItem = taxesLineItem;
    }

    public String getTotalLineItem() {
        return totalLineItem;
    }

    public void setTotalLineItem(String totalLineItem) {
        this.totalLineItem = totalLineItem;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }
}
