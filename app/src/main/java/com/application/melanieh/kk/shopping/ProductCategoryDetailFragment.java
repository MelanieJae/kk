package com.application.melanieh.kk.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.Product;
import com.squareup.picasso.Picasso;

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

    static Picasso picassoInstance;

    static String category;
    static String[] categoryProductLabels;
    static String[] imageUrls;
    ArrayList<Integer> drawables;

    boolean candleCategory;
    boolean giftsCategory;
    boolean giftBasketsCategory;

    // necessary for using ButterKnife in recycler view
    Unbinder unbinder;

    RecyclerView.LayoutManager rvLayoutManager;

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
        picassoInstance = KKApplication.getPicassoInstance();

        /** populate category products list **/
        category = getActivity().getIntent().getStringExtra(Constants.CATEGORY_EXTRA_KEY);
        candleCategory = Pattern.compile(Pattern.quote("candle"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();
        giftsCategory = Pattern.compile(Pattern.quote("gifts"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();
        giftBasketsCategory = Pattern.compile(Pattern.quote("gifts"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();

        categoryProductLabels = new String[]{"Jar Candles", "Tealights", "Tarts", "Tart burners", "Reed diffusers",
        "Wood wick candles"};
//        categoryProductLabels = generateLabels();
        Timber.d("categoryProductLabels:" + categoryProductLabels[0]);


//        imageUrls = new String[]{Constants.JAR_CANDLES_URL,
//                Constants.TEALIGHTS_URL,
//                Constants.GIFTS_CATEGORY_IMAGE_URL,
//                Constants.JAR_CANDLES_URL,
//                Constants.TEALIGHTS_URL,
//                Constants.GIFTS_CATEGORY_IMAGE_URL};


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView:");

        View rootView = inflater.inflate(R.layout.fragment_category_detail, container, false);
        rvLayoutManager = getLayoutManager();
        categoryProductsRV = (RecyclerView)rootView.findViewById(R.id.category_products_rv);
        Timber.d("rv: " + categoryProductsRV);
        CategoryProductRVAdapter rvAdapter = new CategoryProductRVAdapter(getActivity());
        categoryProductsRV.setLayoutManager(rvLayoutManager);
        categoryProductsRV.setAdapter(rvAdapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        Timber.d("onDestroyView:");
        super.onDestroyView();
//        unbinder.unbind();
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
        String[] imageURLList;
        ArrayList<Integer> drawables;

        boolean candleCategory = Pattern.compile(Pattern.quote("candle"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();
        boolean giftsCategory = Pattern.compile(Pattern.quote("gifts"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();
        boolean giftBasketsCategory = Pattern.compile(Pattern.quote("gifts"),
                Pattern.CASE_INSENSITIVE).matcher(category).find();

        public CategoryProductRVAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Timber.d("onCreateViewHolder:");

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.product_list_item, parent, false);
            drawables = new ArrayList<>();
            drawables.add(R.drawable.candle_category_sample);
            drawables.add(R.drawable.candle_category_sample);
            drawables.add(R.drawable.candle_category_sample);
            drawables.add(R.drawable.candle_category_sample);
            drawables.add(R.drawable.candle_category_sample);
            drawables.add(R.drawable.candle_category_sample);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProductViewHolder holder, final int position) {
            Timber.d("onBindViewHolder:");
            final int imageResId = drawables.get(position);
            Timber.d("drawable:" + drawables.get(position));

//            final String imageUrlString = imageUrls[position];
            Timber.d("categoryProductLabels:" + categoryProductLabels[0]);
            final String productName = categoryProductLabels[position];
            final String cost = "$1";

//            // load image into adapter position via Picasso and adjust text color to match
//            // list item palette
//            picassoInstance.with(holder.itemView.getContext()).load(imageResId).
//                    fit().centerCrop().into(holder.productIV,
//                    PicassoPalette.with(imageResId, holder.productIV).use(PicassoPalette.Profile.VIBRANT)
//                            .intoTextColor(holder.productName, PicassoPalette.Swatch.BODY_TEXT_COLOR));

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
//                    launchproductDetail.putExtra(Constants.TRANSITION_IMAGE_KEY, imageResId);
                    context.startActivity(launchproductDetail);
                }
            });
        }

        @Override
        public int getItemCount() {
            Timber.d("getItemCount:" + categoryProductLabels.length);
            return categoryProductLabels.length;
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

    private String[] generateLabels() {
        String[] labels;

        if (candleCategory) {
            labels = getResources().getStringArray(R.array.candle_categories);
        } else if (giftsCategory) {
            labels = getResources().getStringArray(R.array.gift_categories);
        } else {
            labels = getResources().getStringArray(R.array.gift_basket_categories);
        }
        return labels;
    }

    private String[] generateImageUrls() {
        String[] urls;

        if (candleCategory) {
            urls = getResources().getStringArray(R.array.candle_categories);
        } else if (giftsCategory) {
            urls = getResources().getStringArray(R.array.gift_categories);
        } else {
            urls = getResources().getStringArray(R.array.gift_basket_categories);
        }
        return urls;
    }



}
