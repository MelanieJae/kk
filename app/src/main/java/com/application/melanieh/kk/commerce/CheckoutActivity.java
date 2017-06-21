package com.application.melanieh.kk.commerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.application.melanieh.kk.CartItem;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.commerce.AndroidPaywStripeActivity;
import com.application.melanieh.kk.commerce.PaywStripeOnlyActivity;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.stripe.wrap.pay.AndroidPayConfiguration;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;
import com.application.melanieh.kk.R;

import java.util.ArrayList;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by melanieh on 6/19/17.
 * Though this class routes both Android Pay and non-Android Pay payments,
 * it extends StripeAndroidPayActivity because that class checks whether Android Pay
 * is available upon creation, requiring less code.
 */

public class CheckoutActivity extends StripeAndroidPayActivity {

    SupportWalletFragment androidPayBtnFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }

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

    }
}
