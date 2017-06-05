package com.application.melanieh.kk.ui;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.application.melanieh.kk.BuildConfig;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.ui.ImageHandler;
import com.application.melanieh.kk.ui.MenuLoader;
import com.application.melanieh.kk.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> imageList;
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

        /** populate sample image grid */
        imageList = new ArrayList<String>();
        imageList.add(Constants.SAMPLE_IMAGE_URL);
        imageList.add(Constants.SAMPLE_IMAGE_URL);
        imageList.add(Constants.SAMPLE_IMAGE_URL);
        imageList.add(Constants.SAMPLE_IMAGE_URL);

        menuImageAdapter = new MenuImageAdapter();
        rvLayoutManager = getLayoutManager();
        productItemRV.setLayoutManager(rvLayoutManager);
        productItemRV.setAdapter(menuImageAdapter);

    }

    private RecyclerView.LayoutManager getLayoutManager() {
        int spanCount = 0;
        int screenWidth = getResources().getConfiguration().screenWidthDp;

        if (screenWidth < 600) {
            spanCount = 2;
        } else if (screenWidth >= 600 && screenWidth < 900){
            spanCount = 3;
        } else {
            spanCount = 4;
        }

        GridLayoutManager glm = new GridLayoutManager(this, spanCount);
        return glm;
    }

    /** recyclerview adapter **/

    public class MenuImageAdapter extends RecyclerView.Adapter<MenuImageAdapter.ImageViewHolder> {

        @Override
        public MenuImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_item_list_content, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MenuImageAdapter.ImageViewHolder holder, int position) {
            Timber.d("onBindViewHolder");
            String imageString = imageList.get(position);
            Timber.d("imageString: " + imageString);

            // destination class for intent; varies according to which button is selected

            // toolbar image - loaded into product_item_list_content layout
            ImageHandler.getSharedInstance(holder.itemView.getContext()).load(imageString).
                    fit().centerCrop().into(holder.gridItemIV);

        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }


        public class ImageViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.product_list_item_iv)
            ImageView gridItemIV;

            public ImageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
