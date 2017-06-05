package com.application.melanieh.kk;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import butterknife.BindInt;

/**
 * Created by melanieh on 6/5/17.
 */

public class Product implements Parcelable {

    @BindInt(0) int productData;

    // attributes
    private String name;
    private String cost;
    private ArrayList<String> varieties;
    private String productImageUrlString;

    public Product(int productData, String name, String cost, @Nullable ArrayList<String> varieties, String productImageUrlString) {
        this.productData = productData;
        this.name = name;
        this.cost = cost;
        this.varieties = varieties;
        this.productImageUrlString = productImageUrlString;
    }

    public int getProductData() {
        return productData;
    }

    public void setProductData(int productData) {
        this.productData = productData;
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
        productData = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
