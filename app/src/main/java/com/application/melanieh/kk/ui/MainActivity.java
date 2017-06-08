package com.application.melanieh.kk.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.melanieh.kk.R;
import com.application.melanieh.kk.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

//    ArrayList<String> imageList;
    ArrayList<Integer> drawableList;
    String[] categoryLabels;
    MenuImageAdapter menuImageAdapter;
    RecyclerView.LayoutManager rvLayoutManager;

    @BindView(R.id.toolbar_logo)
    ImageView toolBarIV;
    @BindView(R.id.product_item_list)
    RecyclerView productItemRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

//        /** populate sample image grid */
//        imageList = new ArrayList<String>();
//        imageList.add(Constants.SAMPLE_IMAGE_URL);
//        imageList.add(Constants.SAMPLE_IMAGE_URL);
//        imageList.add(Constants.SAMPLE_IMAGE_URL);
//        imageList.add(Constants.SAMPLE_IMAGE_URL);


        /** populate sample image grid */
        drawableList = new ArrayList<>();
        drawableList.add(R.drawable.candle_category_sample);
        drawableList.add(R.drawable.gifts_category_sample);
        drawableList.add(R.drawable.gift_basket_category_sample);

        /** populate category labels **/
        categoryLabels = getResources().getStringArray(R.array.product_categories);

        menuImageAdapter = new MenuImageAdapter();
        rvLayoutManager = getLayoutManager();
        productItemRV.setLayoutManager(rvLayoutManager);
        productItemRV.setAdapter(menuImageAdapter);

    }

    private RecyclerView.LayoutManager getLayoutManager() {
        int spanCount = 0;
        int screenWidth = getResources().getConfiguration().screenWidthDp;

        if (screenWidth < 900) {
            spanCount = 1;
        } else {
            spanCount = 2;
        }

        GridLayoutManager glm = new GridLayoutManager(this, spanCount);
        return glm;
    }

    /** recyclerview adapter **/

    public class MenuImageAdapter extends RecyclerView.Adapter<MenuImageAdapter.ImageViewHolder> {

        @Override
        public MenuImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list_item, parent, false);
            return new ImageViewHolder(view);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(MenuImageAdapter.ImageViewHolder holder, int position) {
            Timber.d("onBindViewHolder");
            final int imageResId = drawableList.get(position);
//            final String imageString = imageList.get(position);
            final String categoryLabel = categoryLabels[position];
//            Timber.d("imageString: " + imageString);
            Timber.d("imageResId: " + imageResId);
            holder.gridItemIV.setImageResource(imageResId);
//            ImageHandler.getSharedInstance(holder.itemView.getContext()).load(imageString).
//                    fit().centerCrop().into(holder.gridItemIV);
            holder.categoryLabel.setText(categoryLabel);

            holder.gridItemIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchProductsList = new Intent(MainActivity.this, ProductCategoryDetailActivity.class);
                    launchProductsList.setAction(Intent.ACTION_VIEW);
                    launchProductsList.putExtra(Constants.CATEGORY_EXTRA_KEY, "candles");
                    startActivity(launchProductsList);
                }
            });

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

            public ImageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
