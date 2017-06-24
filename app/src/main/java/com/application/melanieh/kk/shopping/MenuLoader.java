package com.application.melanieh.kk.shopping;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by melanieh on 5/30/17.
 */

public class MenuLoader extends AsyncTaskLoader {

    private String queryString;
    private Context context;

    public MenuLoader(Context context, String queryString) {
        super(context);
        this.context = context;
        this.queryString = queryString;
    }

    @Override
    public String loadInBackground() {
        return null;
    }
}
