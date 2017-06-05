package com.application.melanieh.kk.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by melanieh on 5/22/17.
 *
 * Custom ImageView class that adjusts images loaded via the ImageHandler class (e.g. via Picasso)
 */

public class DynamicNetworkImageView extends NetworkImageView {

    private float mAspectRatio = 1.5f;

        public DynamicNetworkImageView(Context context) {
            super(context);
        }

        public DynamicNetworkImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public DynamicNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public void setAspectRatio(float aspectRatio) {
            mAspectRatio = aspectRatio;
            requestLayout();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int measuredWidth = getMeasuredWidth();
            setMeasuredDimension(measuredWidth, (int) (measuredWidth / mAspectRatio));
        }
}


