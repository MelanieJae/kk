package com.application.melanieh.kk.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

import butterknife.BindInt;
import butterknife.BindView;
import timber.log.Timber;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

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

        SupportWalletFragment androidPayWalletFragment = SupportWalletFragment.newInstance(walletFragmentOptions);

        FragmentTransaction buttonTransaction = getSupportFragmentManager().beginTransaction();
        buttonTransaction.replace(R.id.androidPayButtonFragment, androidPayWalletFragment).commit();

//        int imageResId = getIntent().getIntExtra(Constants.TRANSITION_IMAGE_KEY, -1);
//        String productName = getIntent().getStringExtra(Constants.TRANSITION_TEXT_KEY_NAME);
//        String cost = getIntent().getStringExtra(Constants.TRANSITION_TEXT_KEY_COST);

//        Timber.d("imageResId: " + imageResId);
//        Timber.d("productName: " + productName);
//        Timber.d("cost: " + cost);
//
//        Fragment productDetailFragment = ProductDetailFragment.newInstance(imageResId, productName, cost);
//        getSupportFragmentManager().beginTransaction().replace(R.id.productDetailFragment,
//                productDetailFragment)
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_universal, menu);
        return super.onCreateOptionsMenu(menu);
    }



}
