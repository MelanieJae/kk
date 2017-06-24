package com.application.melanieh.kk.shopping;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

/**
 * Created by melanieh on 6/5/17.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ProductDetailTransition extends TransitionSet {

    /**
     * Transition that performs almost exactly like {@link android.transition.AutoTransition}, but has an
     * added {@link ChangeImageTransform} to support proper scaling-up of product image.
     *
     * @author bherbst
     * Source: https://medium.com/@bherbst/fragment-transitions-with-shared-elements-7c7d71d31cbb
     */
        public ProductDetailTransition() {
            init();
        }

        private void init() {
            this.setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
}


