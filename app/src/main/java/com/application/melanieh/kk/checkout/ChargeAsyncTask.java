package com.application.melanieh.kk.checkout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.application.melanieh.kk.models.Customer;
import com.application.melanieh.kk.models.Invoice;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;
import com.stripe.model.Charge;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import timber.log.Timber;

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
