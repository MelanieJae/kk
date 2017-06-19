package com.application.melanieh.kk.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;

import butterknife.BindInt;
import butterknife.BindView;
import timber.log.Timber;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

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
