package com.application.melanieh.kk.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.checkout.CheckoutActivity;
import com.application.melanieh.kk.com.application.kk.customviews.ZoomOutPullDownAnimation;
import com.application.melanieh.kk.models_and_modules.CartItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import timber.log.Timber;

/**
 * Created by melanieh on 6/5/17.
 */

public class ProductDetailFragment extends Fragment {

    @Inject
    CartItem cartItem;
    String variety;
    ReplaySubject<CartItem> cartItemObservable;
    Consumer<CartItem> cartItemConsumer;

    @BindView(R.id.product_iv)
//    ZoomOutPullDownAnimation zopdImage;
    ImageView productImage;
    @BindView(R.id.name)
    TextView productName;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.variety_picker)
    NumberPicker varietyPicker;
    @BindView(R.id.qty_picker)
    NumberPicker qtyPicker;
    @BindView(R.id.observable_test)
    TextView observableTestTV;
    @OnClick(R.id.add_to_cart_btn)
    public void onClick(View view) {
        addToCart();
    }

    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        return fragment;
    }
    public ProductDetailFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        cartItemObservable = KKApplication.getObservable();
    }

    @Override
    public void onPause() {
        super.onPause();
        cartItemObservable.unsubscribeOn(Schedulers.io());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_cart:
                Intent launchCartView = new Intent(getContext(), CheckoutActivity.class);
                startActivity(launchCartView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Timber.d("onCreateView:");

        View rootView = inflater.inflate(R.layout.fragment_product_detail, container,
                false);
        ButterKnife.bind(this, rootView);
        // zoom out-pull down animation for image in toolbar
        ZoomOutPullDownAnimation zopdImage = new ZoomOutPullDownAnimation(getActivity());

        productImage.setImageResource(R.drawable.candle_category_sample);
        zopdImage.setZoomRatio(ZoomOutPullDownAnimation.ZOOM_X2);
        zopdImage.setParallaxImageView(productImage);

        productName.setText("Tealights");
        cost.setText("$1");

        loadVarietyPicker();
        loadQtyPicker();
        // TODO this should be an and/or; keeping as "or" and catching qty = 0 in a null check/dialog
//        }

        return rootView;
    }

    private void loadQtyPicker() {

        // number picker main attributes
        String[] pickerRange = new String[2];
        pickerRange[0] = "Select Quantity";
        pickerRange[1] = "1";
        qtyPicker.setDisplayedValues(pickerRange);
        qtyPicker.setMinValue(R.integer.qty_picker_min_value);
//        qtyPicker.setMaxValue(R.integer.qty_picker_max_value);
        qtyPicker.setWrapSelectorWheel(true);

        // Set the integer mSelected to the constant values
        varietyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                cartItem.setItemQty(i1);
                Timber.d("loadQtyPicker: quantity " + i1);
            }
        });

    }

    private void loadVarietyPicker() {
        String[] items = getResources().getStringArray(R.array.candle_varieties);
        // number picker main attributes
        varietyPicker.setDisplayedValues(items);
        varietyPicker.setWrapSelectorWheel(true);

        // Set the integer mSelected to the constant values
        varietyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                variety = items[i1];
                Timber.d("loadVarietyPicker: variety " + variety);
                cartItem.setItemVariety("" + variety);
            }
        });

    }

    private void addToCart() {
        Timber.d("addToCart()");
        // gather product info
//        cartItem = new CartItem(productName.getText().toString(), variety, qtyPicker.getValue(),
//                Integer.parseInt(cost.getText().toString()), 0.0);

        cartItem = new CartItem("tealight", "cinnamon", 1,
                1.0, 0.0);
        cartItemObservable = KKApplication.getObservable();
        Timber.d("cartItemObservable: " + cartItemObservable.toString());

        Timber.d("cartItem: " + cartItem.toString());

//        cartItemConsumer = new Consumer<CartItem>() {
//            @Override
//            public void accept(CartItem cartItem) throws Exception {
//            }
//        };

//        cartItemObservable.subscribe(cartItemConsumer);
        cartItemObservable.onNext(cartItem);


    }
}
