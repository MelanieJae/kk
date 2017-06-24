package com.application.melanieh.kk.checkout;

import android.os.AsyncTask;
import android.os.Bundle;

import com.application.melanieh.kk.Constants;
import com.stripe.Stripe;
import com.stripe.android.exception.APIConnectionException;
import com.stripe.android.exception.CardException;
import com.stripe.android.exception.InvalidRequestException;
import com.stripe.android.exception.RateLimitException;
import com.stripe.android.exception.StripeException;
import com.stripe.android.model.Customer;
import com.stripe.android.model.Token;
import com.stripe.android.net.RequestOptions;
import com.stripe.exception.AuthenticationException;
import com.stripe.model.Charge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import timber.log.Timber;

/**
 * Created by melanieh on 6/23/17.
 */

public class ChargeAsyncTask extends AsyncTask<Token, Void, Map<String, Bundle>> {

    Map<String, Object> params;

    public ChargeAsyncTask() {

    }

    @Override
    protected Map<String, Bundle> doInBackground(Token...tokens) {
        Timber.d("doInBackground");
        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
//        Stripe.apiKey = BuildConfig.STRIPE_SECRET_KEY;

        Charge charge = makeChargeRequestToStripe(tokens[0]);
        Map<String, Bundle> shipInfoMap = new HashMap<>();
        com.stripe.model.Customer customer = retrieveCustomerInfoFromCharge(charge);

        return shipInfoMap;

    }

    public Charge makeChargeRequestToStripe(Token token) {
        Timber.d("makeChargeRequestToStripe");

        try {
            // Charge the user's card:
            params.put("amount", 1000);
            params.put("currency", "usd");
            params.put("description", "Example charge");
            params.put("source", token);

            Charge charge = Charge.create(params);
            retrieveCustomerInfoFromCharge(charge);
            return charge;
        } catch (AuthenticationException e) {
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
            Timber.wtf(e, "Authentication exception");
            return null;
        } catch (Exception e) {
            // Something else happened, completely unrelated to Stripe
            Timber.wtf(e, "Exception");
            return null;
        }

    }

    /** this is used *solely* to display the billing address as a shipping address option */
    private com.stripe.model.Customer retrieveCustomerInfoFromCharge(Charge charge) {
        Timber.d("retrieveCustomerInfoFromCharge");

        try {
            // Use Stripe's library to make requests...
//            Stripe.apiKey = BuildConfig.STRIPE_SECRET_KEY;

            List<String> expandList = new LinkedList<String>();
            // retrieving the customer object by expanding the response from the Stripe API
            // allows retrieval of the billing address to be presented as a shipping address
            expandList.add("customer");
            // retrieving the invoice by expanding the response for the invoice to be sent to the business
            // for order fulfillment
            expandList.add("invoice");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("expandList", expandList);
            Charge.retrieve(charge.getId(), params, null).getCustomerObject();
            return Charge.retrieve(charge.getId(), params, null).getCustomerObject();

        } catch (AuthenticationException e) {
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
            Timber.wtf(e, "Authentication exception");
            return null;
        } catch (Exception e) {
            // Something else happened, completely unrelated to Stripe
            Timber.wtf(e, "Exception: cause external to Stripe");
            return null;
        }

    }

    private String generateIdempotencyKey() {
        String generatedKey = "" + UUID.randomUUID();
        return generatedKey;
    }

}
