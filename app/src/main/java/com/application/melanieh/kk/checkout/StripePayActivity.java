package com.application.melanieh.kk.checkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.application.melanieh.kk.BuildConfig;
import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.PaymentProcessor;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.shopping.CardInputFragment;
import com.stripe.android.Stripe;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import junit.framework.Test;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class PaywStripeOnlyActivity extends AppCompatActivity implements PaymentProcessor {

    Stripe stripe;
    Integer amount;
    String name;
    Card card;
    Token token;

    // This class is a subscriber/observer of a cart update event published by the AddToCartFragment
    private EventBus eventBus;
    CompositeDisposable disposables;

    @BindView(R.id.card_input_widget)
    CardInputWidget cardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_stripe_only);
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        // extract card info fromt he widget fields and create the card object to process
        card = cardInputWidget.getCard();
        if (card == null) {
            Timber.e("Invalid card data");
        }


    }

    @Override
    public void charge() {

    }



}
