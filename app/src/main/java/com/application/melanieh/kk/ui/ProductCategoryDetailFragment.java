package com.application.melanieh.kk.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.melanieh.kk.BuildConfig;
import com.application.melanieh.kk.Product;
import com.application.melanieh.kk.R;

import java.util.ArrayList;

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

    @BindView(R.id.product_iv)
    ImageView productCategoryIV;
    @NonNull @BindView(R.id.category_products_rv)
    RecyclerView categoryProductsRV;

    Unbinder unbinder;

    RecyclerView.LayoutManager rvLayoutManager;
    ArrayList categoryProducts;
    static FragmentManager fragmentManager;

    public ProductCategoryDetailFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate:");
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView:");

        View rootView = inflater.inflate(R.layout.fragment_category_detail, container, false);
        categoryProducts = new ArrayList<>();
        categoryProducts.add(new Product(1, "Soy Candles", "$0", null, BuildConfig.SAMPLE_IMAGE_URL));

        unbinder = ButterKnife.bind(getActivity(), rootView);
        ButterKnife.setDebug(true);
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
            spanCount = 2;
        } else if (screenWidth >= 600 && screenWidth < 900){
            spanCount = 3;
        } else {
            spanCount = 4;
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

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_product_list_item, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProductViewHolder holder, final int position) {
            Timber.d("onBindViewHolder:");
            holder.product = productsList.get(position);
            final String imageUrlString = holder.product.getProductImageUrlString();
            final String productName = holder.product.getName();
            final String cost = holder.product.getCost();

            ImageHandler.getSharedInstance(holder.itemView.getContext()).load(imageUrlString).
                    fit().centerCrop().into(holder.productIV);
            holder.productName.setText(productName);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProductDetailFragment productDetails =
                            ProductDetailFragment.newInstance(imageUrlString, productName, cost);

                    // Note that we need the API version check here because the actual transition classes (e.g. Fade)
                    // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
                    // ARE available in the support library (though they don't do anything on API < 21)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        productDetails.setSharedElementEnterTransition(new ProductDetailTransition());
                        productDetails.setEnterTransition(new Fade());
                        productDetails.setExitTransition(new Fade());
                        productDetails.setSharedElementReturnTransition(new ProductDetailTransition());
                    }

                    fragmentManager.beginTransaction()
                            .addSharedElement(holder.productIV, "transition image")
                            .replace(R.id.productDetailFragment, productDetails)
                            // preserves the smooth sharedtransition when the back button is pressed
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            Timber.d("getItemCount:");
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
