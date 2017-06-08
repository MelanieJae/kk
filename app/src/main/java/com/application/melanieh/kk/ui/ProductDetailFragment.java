package com.application.melanieh.kk.ui;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;

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

    String transitionImageUrlString;
    String transitionNameText;
    String transitionCostText;
    int transitionImageResId;

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
        transitionImageResId = R.drawable.candle_category_sample;
        transitionNameText = "Tealights";
        transitionCostText = "$1";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView:");

        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
//        unbinder = ButterKnife.bind(getActivity(), rootView);
//        ButterKnife.bind(getActivity());
        productImage = (ImageView)rootView.findViewById(R.id.product_iv);
        productName = (TextView)rootView.findViewById(R.id.name);
        cost = (TextView)rootView.findViewById(R.id.cost);
        variety_spinner = (Spinner)rootView.findViewById(R.id.product_variety_spinner);

//        Timber.d("productImage: " + productImage.toString());
//        Timber.d("productName: " + productName.toString());
//        Timber.d("cost: " + cost.toString());
//        ImageHandler.getSharedInstance(getContext()).load(transitionImageUrlString).
//                fit().centerCrop().into(productImage);


        productImage.setImageResource(transitionImageResId);
        productName.setText(transitionNameText);
        cost.setText(transitionCostText);
        loadSpinner();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

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
//                    if (selection.equals(getString(R.string.gender_male))) {
//                        mGender = PetEntry.GENDER_MALE;
//                    } else if (selection.equals(getString(R.string.gender_female))) {
//                        mGender = PetEntry.GENDER_FEMALE;
//                    } else {
//                        mGender = PetEntry.GENDER_UNKNOWN;
//                    }
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
