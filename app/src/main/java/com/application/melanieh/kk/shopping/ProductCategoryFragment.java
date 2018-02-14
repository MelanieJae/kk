package com.application.melanieh.kk.shopping;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by melanieh on 1/31/18.
 */

public class ProductCategoryFragment extends Fragment {

    ArrayList<String> imageList;
    Picasso picassoInstance;
    ArrayList<Integer> drawableList;
    String[] categoryLabels;
    RecyclerView categoriesRV;
    RecyclerView.LayoutManager rvLayoutManager;
    CategoriesMenuAdapter rvAdapter;
    String categoryLabel;
    Unbinder unbinder;

    public static ProductCategoryFragment newInstance() {
        ProductCategoryFragment fragment = new ProductCategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        picassoInstance = KKApplication.getPicassoInstance();
        drawableList = new ArrayList<>();
        drawableList.add(R.drawable.romantic_candles_ideas);
        drawableList.add(R.drawable.gift_basket_category_sample);
        drawableList.add(R.drawable.gifts_category_sample);
//        imageList = new ArrayList<>();
//        imageList.add(Constants.CANDLES_CATEGORY_IMAGE_URL);
//        imageList.add(Constants.GIFTS_CATEGORY_IMAGE_URL);
//        imageList.add(Constants.GIFT_BASKETS_CATEGORY_IMAGE_URL);
    }

    @Nullable
    @Override
    public View onCreateView
            (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_product_category, container, false);
        rvLayoutManager = getLayoutManager();
        unbinder = ButterKnife.bind(getActivity(), rootView);
        ButterKnife.setDebug(true);
        categoryLabels = getResources().getStringArray(R.array.product_categories);
        categoriesRV = (RecyclerView) rootView.findViewById(R.id.product_categories_rv);
        Timber.d("PicassoInstance:" + picassoInstance);

        rvAdapter = new CategoriesMenuAdapter(getContext());
        categoriesRV.setLayoutManager(rvLayoutManager);
        categoriesRV.setAdapter(rvAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        int spanCount = 0;
        int screenWidth = getResources().getConfiguration().screenWidthDp;

        if (screenWidth < 900) {
            spanCount = 1;
        } else {
            spanCount = 2;
        }

        GridLayoutManager glm = new GridLayoutManager(getContext(), spanCount);
        return glm;
    }
    /** recyclerview adapter **/

    public class CategoriesMenuAdapter extends RecyclerView.Adapter<CategoriesMenuAdapter.ImageViewHolder> {

        Context context;

        public CategoriesMenuAdapter(Context context) {
            this.context = context;
        }

        @Override
        public CategoriesMenuAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list_item, parent, false);

            return new ImageViewHolder(view);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(CategoriesMenuAdapter.ImageViewHolder holder, int position) {

            Timber.d("onBindViewHolder");
            Timber.d("imageList" + imageList);
            final int imageResId = drawableList.get(position);
//            final String imageString = imageList.get(position);
            categoryLabel = categoryLabels[position];
//            Timber.d("imageString: " + imageString);
//            Timber.d("imageResId: " + imageResId);
            holder.gridItemIV.setImageResource(imageResId);

//            // load image into adapter position via Picasso and adjust text color to match
//            // list item palette
//            picassoInstance.with(holder.itemView.getContext()).load(imageString).fit()
//                    .into(holder.gridItemIV,
//                    PicassoPalette.with(imageString, holder.gridItemIV).use(PicassoPalette.Profile.VIBRANT)
//                            .intoTextColor(holder.categoryLabel, PicassoPalette.Swatch.BODY_TEXT_COLOR));

            holder.categoryLabel.setText(categoryLabel);

//            holder.gridItemIV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    launchProductsList();
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return drawableList.size();
        }


        public class ImageViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.product_list_item_iv)
            ImageView gridItemIV;
            @BindView(R.id.product_category_label)
            TextView categoryLabel;
            @OnClick(R.id.product_list_item_iv)
            void onClick(View view) {
                launchProductsList();
            }

            public ImageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    private void launchProductsList() {
        Intent launchProductsList = new Intent(getContext(),
                ProductCategoryDetailActivity.class);
        launchProductsList.setAction(Intent.ACTION_VIEW);
        launchProductsList.putExtra(Constants.CATEGORY_EXTRA_KEY, categoryLabel);
        startActivity(launchProductsList);
    }

}
