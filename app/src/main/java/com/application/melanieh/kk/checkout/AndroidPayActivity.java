package com.application.melanieh.kk.checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.application.melanieh.kk.PaymentProcessor;

/**
 * Created by melanieh on 6/24/17.
 */

public class AndroidPayActivity extends AppCompatActivity implements PaymentProcessor {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** payment processor */
    @Override
    public void charge() {

    }
}
