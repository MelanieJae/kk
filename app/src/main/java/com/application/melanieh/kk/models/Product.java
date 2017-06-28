package com.application.melanieh.kk.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by melanieh on 6/5/17.
 */

public class Product implements Parcelable {

    // attributes
    private String name;
    private String cost;
    private ArrayList<String> varieties;
    private String productImageUrlString;
    private Integer productImageResId;
    String[] productParamStrings = {name, cost, null, productImageUrlString};

    public Product(String name, String cost, @Nullable ArrayList<String> varieties,
                   String productImageUrlString) {
        this.name = name;
        this.cost = cost;
        this.varieties = varieties;
        this.productImageUrlString = productImageUrlString;
    }

    public Product(String name, String cost, @Nullable ArrayList<String> varieties,
                   Integer imageResId) {
        this.name = name;
        this.cost = cost;
        this.varieties = varieties;
        this.productImageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public ArrayList<String> getVarieties() {
        return varieties;
    }

    public void setVarieties(ArrayList<String> varieties) {
        this.varieties = varieties;
    }

    public String getProductImageUrlString() {
        return productImageUrlString;
    }

    public void setProductImageUrlString(String productImageUrlString) {
        this.productImageUrlString = productImageUrlString;
    }

    public Integer getProductImageResId() {
        return productImageResId;
    }

    public void setProductImageResId(Integer productImageResId) {
        this.productImageResId = productImageResId;
    }

    // Parcelable interface methods

    //constructor
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        in.readStringArray(productParamStrings);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
