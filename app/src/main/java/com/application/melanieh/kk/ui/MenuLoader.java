package com.application.melanieh.kk.ui;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
