package com.application.melanieh.kk.shopping;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.R;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;

import timber.log.Timber;

public class ProductDetailActivity extends StripeAndroidPayActivity {

    SupportWalletFragment androidPayBtnFragment;
    /** event bus for cart updates */
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_universal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** StripeAndroidPayActivity wrapper methods */
    @Override
    protected void onAndroidPayAvailable() {
        Timber.d("Android Pay Available");
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

           // for Android pay Button fragment
        androidPayBtnFragment = SupportWalletFragment.newInstance(walletFragmentOptions);
        addBuyButtonWalletFragment(androidPayBtnFragment);
    }

    @Override
    protected void onAndroidPayNotAvailable() {
        Timber.d("Android Pay Not Available");
        SetUpAndroidPayBtnFragment setUpAndroidPayBtnFragment =
                SetUpAndroidPayBtnFragment.newInstance();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.setUpAndroidPayBtnFragment, setUpAndroidPayBtnFragment).commit();
    }

    @Override
    protected void addBuyButtonWalletFragment(@NonNull SupportWalletFragment walletFragment) {
        FragmentTransaction buttonTransaction = getSupportFragmentManager().beginTransaction();
        buttonTransaction.replace(R.id.androidPayButtonFragment, androidPayBtnFragment).commit();
    }

    /** RxAndroid event bus for cart update pub-sub pattern; GP Cart is the pub and the pay with stripe button
     * and Android Pay button fragments are the subs. The eventbus singleton is used by the Pay with Stripe and Android Pay btn
     * fragments.
     */

    // TODO: implement with Dagger
    public EventBus getEventBusSingleton() {
        if (eventBus == null) {
            eventBus = new EventBus();
        }

        return eventBus;
    }

}
