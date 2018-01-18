package com.application.melanieh.kk.checkout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.application.melanieh.kk.BuildConfig;
import com.application.melanieh.kk.R;

import com.application.melanieh.kk.PaymentProcessor;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

/**
 * Created by melanieh on 6/28/17.
 */

public class StripeCardInputDialogFragment extends DialogFragment implements PaymentProcessor {

    Card card;

    public StripeCardInputDialogFragment() {
        //
    }

    public static StripeCardInputDialogFragment newInstance() {
        StripeCardInputDialogFragment fragment = new StripeCardInputDialogFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dialog_stripe_card_input, container, false);
        CardInputWidget cardInputWidget = (CardInputWidget)rootView.findViewById(R.id.card_input_widget);
        card = cardInputWidget.getCard();
        // extract card info from the widget fields and create the card object to process
//            card = cardInputWidget.getCard();
        if (card == null) {
            Toast.makeText(getActivity(), "Invalid card data", Toast.LENGTH_LONG).show();
        } else {
            charge();
        }

        return rootView;

    }

    @Override
    public void charge() {
        // Stripe instance and token to process payment via the card object
        Stripe stripe = new Stripe(getActivity(), BuildConfig.STRIPE_TEST_PUBLISHABLE_KEY);
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server where the charge to the customer's card is made
                        // via the insertCharge method
                        Toast.makeText(getActivity(), "token= " + token.toString(), Toast.LENGTH_LONG).show();
                        ChargeAsyncTask chargeAsyncTask = new ChargeAsyncTask(getActivity());
                        chargeAsyncTask.execute(token);
                    }

                    public void onError(Exception error) {
                        // Show localized error message on UI
                        showPaymentFailedDialog();
                    }
                });
    }

    private void showPaymentFailedDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(getString(R.string.payment_failed_dialog_msg));

        DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    // Ok button mostly for clarity and user interaction
                    // ; any click away from the window dismisses it by fault
                    return;
                }
            }
        };

        String okString = getString(R.string.dialog_ok_btn);
        dialogBuilder.setNegativeButton(okString, okButtonListener);
        dialogBuilder.create();
        dialogBuilder.show();

    }
}
