package com.application.melanieh.kk.checkout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.application.melanieh.kk.BuildConfig;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.PaymentProcessor;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.CartItem;
import com.application.melanieh.kk.shopping.SetUpAndroidPayBtnFragment;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentMethodTokenizationType;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.wrap.pay.AndroidPayConfiguration;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by melanieh on 6/19/17.
 * Though this class routes both Android Pay and non-Android Pay payments,
 * it extends StripeAndroidPayActivity because that class checks whether Android Pay
 * is available upon creation, requiring less code.
 */

public class CheckoutActivity extends StripeAndroidPayActivity implements PaymentProcessor {

    Cart cart;
    Card card;
    AndroidPayConfiguration payConfiguration;
    PaymentMethodTokenizationParameters parameters;
    SupportWalletFragment androidPayBtnFragment;
    // This class is a subscriber/observer of a cart update event published by the AddToCartFragment

    CompositeDisposable disposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // get instance of Android Pay Configuration and require shipping address from customer
        AndroidPayConfiguration payConfiguration = KKApplication.initAndroidPayConfig();

        // this class is an observer/subscriber to the AddtoCartFragment's cart content updates

        // shopping cart, continue shopping button and Stripe checkout buttons should appear regardless

        ShoppingCartFragment shoppingCartFragment = ShoppingCartFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.shoppingCartFragment, shoppingCartFragment)
                .commit();

//        ContinueShoppingBtnFragment continueShoppingBtnFragment = ContinueShoppingBtnFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(R.id.continueShoppingBtnFragment,
//                continueShoppingBtnFragment)
//                .commit();
//
//        PayWithStripeBtnFragment stripeBtnFragment = PayWithStripeBtnFragment.newInstance();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.stripeOnlyPayButtonFragment, stripeBtnFragment)
//                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // retrieve the error code, if available
        int errorCode = -1;
        if (data != null) {
            errorCode = data.getIntExtra(WalletConstants.EXTRA_ERROR_CODE, -1);
        }
        switch (requestCode) {
            case REQUEST_CODE_MASKED_WALLET:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (data != null) {
                            MaskedWallet maskedWallet =
                                    data.getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);

//                            // call to get the Google transaction id
//                            googleTransactionId = getGoogleTransactionId();
//                            launchConfirmationPage(maskedWallet);
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        handleError(errorCode);
                        break;
                }
                break;

            case WalletConstants.RESULT_ERROR:
                handleError(errorCode);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onAndroidPayAvailable() {
        // configure buy button UI element
        WalletFragmentStyle walletFragmentStyle = new WalletFragmentStyle()
                .setBuyButtonText(WalletFragmentStyle.BuyButtonText.BUY_WITH)
                .setBuyButtonAppearance(WalletFragmentStyle.BuyButtonAppearance.ANDROID_PAY_DARK)
                .setBuyButtonWidth(WalletFragmentStyle.Dimension.MATCH_PARENT);

        WalletFragmentOptions walletFragmentOptions = WalletFragmentOptions.newBuilder()
                .setEnvironment(Constants.WALLET_ENVIRONMENT)
                .setFragmentStyle(walletFragmentStyle)
                .setTheme(WalletConstants.THEME_LIGHT)
                .setMode(WalletFragmentMode.BUY_BUTTON)
                .build();

        androidPayBtnFragment = SupportWalletFragment.newInstance(walletFragmentOptions);

        // payment gateway token
        PaymentMethodTokenizationParameters parameters =
                PaymentMethodTokenizationParameters.newBuilder()
                        .setPaymentMethodTokenizationType(PaymentMethodTokenizationType.PAYMENT_GATEWAY)
                        .addParameter("gateway", "stripe")
                        .addParameter("stripe:publishableKey", BuildConfig.STRIPE_TEST_PUBLISHABLE_KEY)
                        //TODO: change version once one is issued after first charge attempt
                        .addParameter("stripe:version", Constants.STRIPE_API_VERSION)
                        .build();

        MaskedWalletRequest maskedWalletRequest = MaskedWalletRequest.newBuilder()
                .setMerchantName(Constants.MERCHANT_NAME)
                .setPhoneNumberRequired(true)
                .setShippingAddressRequired(true)
                .setCurrencyCode(Constants.CURRENCY_CODE_USD)
//                .setEstimatedTotalPrice(cartTotal)
                // Create a Cart with the current line items. Provide all the information
                // available up to this point with estimates for shipping and tax included.
                .setCart(Cart.newBuilder()
                        .setCurrencyCode(Constants.CURRENCY_CODE_USD)
//                        .setTotalPrice(cartTotal)
//                        .setLineItems(lineItems)
                        .build())
                .setPaymentMethodTokenizationParameters(parameters)
                .build();

//        PaymentMethodTokenizationParameters parameters =
//                PaymentMethodTokenizationParameters.newBuilder()
//                        .setPaymentMethodTokenizationType(PaymentMethodTokenizationType.NETWORK_TOKEN)
//                        .addParameter("publicKey", publicKey)
//                        .build();
        WalletFragmentInitParams.Builder startParamsBuilder =
                WalletFragmentInitParams.newBuilder()
                        .setMaskedWalletRequest(maskedWalletRequest)
                        .setMaskedWalletRequestCode(REQUEST_CODE_MASKED_WALLET)
                        .setAccountName(Constants.MERCHANT_NAME);
        androidPayBtnFragment.initialize(startParamsBuilder.build());

// add Wallet fragment to the UI
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.androidPayBtnFragment, androidPayBtnFragment)
                .commit();

    }

    @Override
    protected void onAndroidPayNotAvailable() {

        Timber.d("Android Pay Not Available");
        SetUpAndroidPayBtnFragment setUpAndroidPayBtnFragment =
                SetUpAndroidPayBtnFragment.newInstance();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.androidPayBtnFragment, setUpAndroidPayBtnFragment).commit();
    }

    @Override
    public void charge() {
        /** payment processor interface method(s) **/

        // Stripe instance and token to process payment via the card object
        Stripe stripe = new Stripe(this, BuildConfig.STRIPE_TEST_PUBLISHABLE_KEY);
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server where the charge to the customer's card is made
                        // via the insertCharge method
                        Toast.makeText(CheckoutActivity.this, "token= " + token.toString(),
                                Toast.LENGTH_LONG).show();
                        ChargeAsyncTask chargeAsyncTask = new ChargeAsyncTask(CheckoutActivity.this);
                        chargeAsyncTask.execute(token);
                    }

                    public void onError(Exception error) {
                        // Show localized error message on UI
                        showPaymentFailedDialog();
                    }
                });

    }

    private void showPaymentFailedDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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

    private ArrayList<CartItem> getAllCartItems() {
        ArrayList<CartItem> cart = new ArrayList<>();
        // TODO fill in stream parse logic from eventbus
        return cart;
    }
}
