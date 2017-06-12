package com.application.melanieh.kk.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.melanieh.kk.R;

/**
 * Created by melanieh on 6/12/17.
 */

public class CheckoutFragment extends Fragment {

    public CheckoutFragment newInstance() {
        // TODO: FINISH
        return new CheckoutFragment();
    }

    public CheckoutFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO initialize async task for stripe and/or android pay
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);
        return rootView;
    }
}
