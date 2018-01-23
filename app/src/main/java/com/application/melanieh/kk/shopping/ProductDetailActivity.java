package com.application.melanieh.kk.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.application.melanieh.kk.CartItemListener;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.checkout.CheckoutActivity;
import com.application.melanieh.kk.models_and_modules.CartItem;

import timber.log.Timber;

public class ProductDetailActivity extends AppCompatActivity implements CartItemListener{

    /**
     * event bus for publishing cart updates when the add to cart FAB is pushed
     */


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_cart:
                Intent launchCartView = new Intent(this, CheckoutActivity.class);
                startActivity(launchCartView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void passCartItem(CartItem cartItem) {
        Timber.d("ProductDetailActivity: " + cartItem);

        AddToCartBtnFragment updatedCartBtnFrag = AddToCartBtnFragment.newInstance(cartItem);
        Timber.d("ProdDetAct post new instance: " +
                updatedCartBtnFrag.getArguments().getParcelable(Constants.CART_ITEMS_DATA_KEY).toString());
        getFragmentManager().beginTransaction()
                .replace(R.id.addToCartBtnFragment,
                        updatedCartBtnFrag)
                .commit();
    }

}
