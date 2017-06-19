package com.application.melanieh.kk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.melanieh.kk.CartItem;
import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.commerce.AndroidPaywStripeActivity;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by melanieh on 6/5/17.
 */

public class ProductDetailFragment extends Fragment {

    @BindView(R.id.product_iv)
    ImageView productImage;
    @BindView(R.id.name)
    TextView productName;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.product_variety_spinner)
    Spinner variety_spinner;
    @BindView(R.id.add_to_cart_btn)
    Button addToCartBtn;
    @BindView(R.id.qty_value)
    EditText qtyValueET;
    @BindView(R.id.cust_requests_notes)
    EditText customerNotesET;

    String transitionImageUrlString;
    String transitionNameText;
    String transitionCostText;
    int transitionImageResId;
    ArrayList<CartItem> cartItems;

    private static int variety = 0;

    Unbinder unbinder;

    public ProductDetailFragment() {
        //
    }

    public static ProductDetailFragment newInstance(String imageUrlString, String productName, String cost) {
        Timber.d("product detail fragment new instance: ");
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TRANSITION_IMAGE_KEY, imageUrlString);
        args.putString(Constants.TRANSITION_TEXT_KEY_NAME, productName);
        args.putString(Constants.TRANSITION_TEXT_KEY_COST, cost);
        fragment.setArguments(args);
        return fragment;
    }


    public static ProductDetailFragment newInstance(@IntegerRes int imageResId,
                                                    String productName, String cost) {
        Timber.d("product detail fragment new instance: ");
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.TRANSITION_IMAGE_KEY, imageResId);
        args.putString(Constants.TRANSITION_TEXT_KEY_NAME, productName);
        args.putString(Constants.TRANSITION_TEXT_KEY_COST, cost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        transitionImageUrlString = getArguments().getString(Constants.TRANSITION_IMAGE_KEY);
        cartItems = getActivity().getIntent().getParcelableArrayListExtra(Constants.CART_ITEMS_KEY);
        transitionImageResId = R.drawable.candle_category_sample;
        transitionNameText = "Tealights";
        transitionCostText = "$1";
        cartItems = new ArrayList<>();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_cart:

                Intent launchCartView = new Intent(getContext(), AndroidPaywStripeActivity.class);
                launchCartView.putExtra(Constants.CART_ITEMS_KEY, cartItems);
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

        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(getActivity(), rootView);

//        productImage = (ImageView)rootView.findViewById(R.id.product_iv);
//        productName = (TextView)rootView.findViewById(R.id.name);
//        cost = (TextView)rootView.findViewById(R.id.cost);
//        variety_spinner = (Spinner)rootView.findViewById(R.id.product_variety_spinner);
//        addToCartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cartItems.add(new CartItem(transitionNameText, Integer.parseInt(qtyValueET.getText().toString()),
//                        Double.parseDouble(transitionCostText), customerNotesET.getText().toString(), 1.00));
//            }
//        });

//        Timber.d("productImage: " + productImage.toString());
//        Timber.d("productName: " + productName.toString());
//        Timber.d("cost: " + cost.toString());
//        ImageHandler.getSharedInstance(getContext()).load(transitionImageUrlString).
//                fit().centerCrop().into(productImage);


        productImage.setImageResource(R.drawable.candle_category_sample);
        productName.setText(transitionNameText);
        cost.setText(transitionCostText);
        loadSpinner();
        return rootView;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    /**
     * Setup the dropdown spinner containing the available varieties of the product.
     */
    private void loadSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter varietySpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.candle_varieties, android.R.layout.simple_spinner_dropdown_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        varietySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Bind the adapter to the spinner
        variety_spinner.setAdapter(varietySpinnerAdapter);

        // Set the integer mSelected to the constant values
        variety_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {

                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                variety = Constants.VARIETY_UNKNOWN;
            }
        });
    }

}
