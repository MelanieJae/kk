package com.application.melanieh.kk.checkout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.application.melanieh.kk.CartItemRVAdapter;
import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.CartItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import timber.log.Timber;

/**
 * Created by melanieh on 6/19/17.
 * Though this class routes both Android Pay and non-Android Pay payments,
 * it extends StripeAndroidPayActivity because that class checks whether Android Pay
 * is available upon creation, requiring less code.
 */

public class CheckoutActivity extends AppCompatActivity{

    @BindView(R.id.cart_item_rv)
    RecyclerView cartItemRV;
    @BindView(R.id.rv_emptyview_text)
    TextView emptyViewTV;
    CartItemRVAdapter rvAdapter;
    ArrayList<CartItem> cartItems;
    ReplaySubject<CartItem> cartItemObservable;
    Observable<CartItem> cachedObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        cartItems = new ArrayList<>();
        displayCart();

        cachedObservable = getOrCreateCachedObservable(savedInstanceState);

    }

    // all methods creating/setting or returning the cached observable must deal with an Observable<T>
    // rather than the original ReplaySubject observable type.
    @NonNull
    private Observable<CartItem> getOrCreateCachedObservable(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            // first run, create and set observable
            cachedObservable = createandSetObservable();
        } else {
            // following runs, get observable from retained fragment
            cachedObservable = RetainShoppingCartFragmentHelper.getObjectOrNull(this, getSupportFragmentManager());
            // fragment may be removed during memory clean up, if so, create and set observable again
            if (cachedObservable == null) {
                cachedObservable = createandSetObservable();
            }
        }

        return cachedObservable;
    }

    @NonNull
    private Observable<CartItem> createandSetObservable() {
        cachedObservable = cartItemObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();

        RetainShoppingCartFragmentHelper.setObject(this, getSupportFragmentManager(), cachedObservable);

        return cachedObservable;
    }

    private void displayCart() {

        cartItemObservable = KKApplication.getObservable();
        Timber.d("cartItemObservable: " + cartItemObservable.toString());
        Consumer<CartItem> cartItemConsumer = new Consumer<CartItem>() {
            @Override
            public void accept(CartItem cartItem) throws Exception {
                cartItems.add(cartItem);

            }
        };


        RetainShoppingCartFragmentHelper.setObject(this, getSupportFragmentManager(), cachedObservable);
        cartItemObservable.subscribe(cartItemConsumer);
        rvAdapter = new CartItemRVAdapter(this, cartItems);
        cartItemRV.setLayoutManager(new LinearLayoutManager(this));
        cartItemRV.setAdapter(rvAdapter);

    }


}
