package com.application.melanieh.kk.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.Product;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by melanieh on 6/5/17.
 *
 * This screen shows an image representing the product category and a listing of products in that
 * category.
 */

public class ProductCategoryDetailFragment extends Fragment {

    @NonNull @BindView(R.id.category_products_rv)
    RecyclerView categoryProductsRV;
    static String category;
    // necessary for using ButterKnife in recycler view
    Unbinder unbinder;

    RecyclerView.LayoutManager rvLayoutManager;
    String[] categoryProductLabels;
    int[] tempProductDrawables;
    ArrayList<Product> categoryProducts;
    static FragmentManager fragmentManager;

    public static ProductCategoryDetailFragment newInstance() {
        ProductCategoryDetailFragment fragment = new ProductCategoryDetailFragment();
        return fragment;
    }

    public ProductCategoryDetailFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate:");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        categoryProducts = new ArrayList<>();

        /** initialize shopping cart array list here
         * so it is not re-created in the product detail screen each time a new product
         * is added to the cart;
         * this also makes transferring the items here (i.e. the CartItemArrayList) into a CartManager
         * object simpler
         *
         */

        /** populate category products list **/
        category = getActivity().getIntent().getStringExtra(Constants.CATEGORY_EXTRA_KEY);

        boolean isCandleCategory = Pattern.compile(Pattern.quote("candle"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();
        boolean isGiftsCategory = Pattern.compile(Pattern.quote("gifts"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();

        if (isCandleCategory) {
            /** populate category labels **/
            categoryProductLabels = getResources().getStringArray(R.array.candle_categories);
            tempProductDrawables = getResources().getIntArray(R.array.temp_category_product_drawables);
        } else if (isGiftsCategory) {
            categoryProductLabels = getResources().getStringArray(R.array.gift_categories);
            Timber.d("categoryProductLabels" + categoryProductLabels);
        } else {
            categoryProductLabels = getResources().getStringArray(R.array.gift_basket_categories);
        }

        Timber.d("categoryProductLabels" + categoryProductLabels);
        fragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView:");

        View rootView = inflater.inflate(R.layout.fragment_category_detail, container, false);
        unbinder = ButterKnife.bind(getActivity(), rootView);
        ButterKnife.setDebug(true);
        for (int i = 0; i < categoryProductLabels.length; i++) {
            categoryProducts.add(new Product("tealights", "$1", null,
                    R.drawable.candle_category_sample));
        }
        Timber.d("categoryProductLabels:" + categoryProductLabels);
        Timber.d("tempProductDrawables:" + tempProductDrawables);
        Timber.d("CategoryProducts:" + categoryProducts);
        CategoryProductRVAdapter rvAdapter = new CategoryProductRVAdapter(getActivity(), categoryProducts);
        rvLayoutManager = getLayoutManager();
        categoryProductsRV = (RecyclerView)rootView.findViewById(R.id.category_products_rv);
        Timber.d("rv: " + categoryProductsRV);
        categoryProductsRV.setLayoutManager(rvLayoutManager);
        categoryProductsRV.setAdapter(rvAdapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        Timber.d("onDestroyView:");
        super.onDestroyView();
        unbinder.unbind();
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        Timber.d("getLayoutManager:");

        int spanCount = 0;
        int screenWidth = getResources().getConfiguration().screenWidthDp;

        if (screenWidth < 600) {
            spanCount = 1;
        } else if (screenWidth >= 600 && screenWidth < 900){
            spanCount = 2;
        } else {
            spanCount = 3;
        }

        GridLayoutManager glm = new GridLayoutManager(getContext(), spanCount);
        return glm;
    }


    /**
     * Created by melanieh on 6/5/17.
     */

    public static class CategoryProductRVAdapter
            extends RecyclerView.Adapter<CategoryProductRVAdapter.ProductViewHolder> {

        private Context context;
        private ArrayList<Product> productsList;

        public CategoryProductRVAdapter(Context context, ArrayList<Product> productsList) {
            this.context = context;
            this.productsList = productsList;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Timber.d("onCreateViewHolder:");

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.product_list_item, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProductViewHolder holder, final int position) {
            Timber.d("onBindViewHolder:");
            holder.product = productsList.get(position);
            final int imageResId = holder.product.getProductImageResId();
            final String imageUrlString = holder.product.getProductImageUrlString();
            final String productName = holder.product.getName();
            final String cost = holder.product.getCost();

//            ImageHandler.getSharedInstance(holder.itemView.getContext()).load(imageUrlString).
//                    fit().centerCrop().into(holder.productIV);
            holder.productIV.setImageResource(imageResId);
            holder.productName.setText(productName);
            holder.cost.setText(cost);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = holder.itemView.getContext();
                    Intent launchproductDetail = new Intent(context, ProductDetailActivity.class);
                    launchproductDetail.putExtra(Constants.CATEGORY_EXTRA_KEY, category);
                    launchproductDetail.putExtra(Constants.TRANSITION_TEXT_KEY_NAME, productName);
                    launchproductDetail.putExtra(Constants.TRANSITION_TEXT_KEY_COST, cost);
                    launchproductDetail.putExtra(Constants.TRANSITION_IMAGE_KEY, imageResId);
                    context.startActivity(launchproductDetail);
                }
            });
        }

        @Override
        public int getItemCount() {
            Timber.d("getItemCount:" + productsList.size());
            return productsList.size();
        }

        /**
         * Created by melanieh on 6/5/17.
         */

        public static class ProductViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.name)
            TextView productName;
            @BindView(R.id.product_iv)
            ImageView productIV;
            @BindView(R.id.cost)
            TextView cost;

            private Product product;

            public ProductViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
