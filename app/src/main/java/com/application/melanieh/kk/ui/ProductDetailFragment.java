package com.application.melanieh.kk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    String transitionImageUrlString;
    String transitionNameText;
    String transitionCostText;

    public ProductDetailFragment() {
        //
    }

    public static ProductDetailFragment newInstance(String imageUrlString, String productName, String cost) {
        Timber.d("product detail fragment new instance: ");
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TRANSITION_IMAGE_KEY, imageUrlString);
        args.putString(Constants.TRANSITION_TEXT_KEY_NAME, productName);
        args.putString(Constants.TRANSITION_TEXT_KEY_COST, productName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transitionImageUrlString = getArguments().getString(Constants.TRANSITION_IMAGE_KEY);
        transitionNameText = getArguments().getString(Constants.TRANSITION_TEXT_KEY_NAME);
        transitionCostText = getArguments().getString(Constants.TRANSITION_TEXT_KEY_COST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(getActivity());

        ImageHandler.getSharedInstance(getContext()).load(transitionImageUrlString).
                fit().centerCrop().into(productImage);
        productName.setText(transitionNameText);
        cost.setText(transitionCostText);

        return rootView;
    }
}
