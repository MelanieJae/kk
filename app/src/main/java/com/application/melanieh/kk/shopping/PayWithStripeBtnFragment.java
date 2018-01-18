package com.application.melanieh.kk.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.melanieh.kk.R;
import com.application.melanieh.kk.checkout.StripeCardInputDialogFragment;

/**
 * Created by melanieh on 6/20/17.
 */

public class PayWithStripeBtnFragment extends Fragment {

//    Unbinder unbinder;
    Button stripePay;
//    @OnClick(R.id.pay_with_stripe_btn)
//    public void onClick(View view) {
//        Intent launchCheckout = new Intent(getActivity(), CheckoutActivity.class);
//        startActivity(launchCheckout);
//    }

    public static PayWithStripeBtnFragment newInstance() {
        PayWithStripeBtnFragment fragment = new PayWithStripeBtnFragment();
        return fragment;
    }

    public PayWithStripeBtnFragment() {
        //
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stripe_only_pay_button, container, false);

//        unbinder = ButterKnife.bind(getActivity(), rootView);
        stripePay = (Button)rootView.findViewById(R.id.pay_with_stripe_btn);
        stripePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StripeCardInputDialogFragment stripeCardWidgetDialog = StripeCardInputDialogFragment.newInstance();

            }
        });

        return rootView;
    }



}
