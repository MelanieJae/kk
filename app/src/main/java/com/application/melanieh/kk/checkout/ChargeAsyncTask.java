package com.application.melanieh.kk.checkout;

import android.content.Context;
import android.os.AsyncTask;

import com.stripe.android.model.Token;

/**
 * Created by melanieh on 6/23/17.
 */

public class ChargeAsyncTask extends AsyncTask<Token, Void, String> {

    private Context context;
    private String stripeJsonResponse;

    public ChargeAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Token...token) {

        return stripeJsonResponse;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
