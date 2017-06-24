package com.application.melanieh.kk.checkout;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.application.melanieh.kk.BuildConfig;
import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.PaymentProcessor;
import com.application.melanieh.kk.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Customer;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class StripePayActivity extends AppCompatActivity implements PaymentProcessor {

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

        // extract card info from the widget fields and create the card object to process
        card = cardInputWidget.getCard();
        if (card == null) {
            Timber.e("Invalid card data");
        } else {
            charge();
        }

    }

    /** payment processor interface method(s) **/
    @Override
    public void charge() {
        // Stripe instance and token to process payment via the card object
        Stripe stripe = new Stripe(this, BuildConfig.STRIPE_TEST_PUBLISHABLE_KEY);
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        ChargeAsyncTask chargeAsyncTask = new ChargeAsyncTask();
                        chargeAsyncTask.execute(token);
                    }

                    public void onError(Exception error) {
                        // Show localized error message on UI
                        AlertDialog paymentFailedDialog = 
                    }
                });

    }
}
