package com.application.melanieh.kk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.application.melanieh.kk.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by melanieh on 6/20/17.
 */

public class PayWithStripeBtnFragment extends Fragment {

    Unbinder unbinder;
    Button stripePay;
    @OnClick(R.id.pay_with_stripe_btn)
    public void onClick(View view) {
        // TODO: FINISH CODE
        Toast.makeText(getActivity(), "Pay with Stripe onClick works", Toast.LENGTH_SHORT).show();

    }


    public PayWithStripeBtnFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stripe_only_pay_button, container, false);
        unbinder = ButterKnife.bind(getActivity(), rootView);
        stripePay = (Button)rootView.findViewById(R.id.pay_with_stripe_btn);
        stripePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Pay with Stripe onClick works", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

}
