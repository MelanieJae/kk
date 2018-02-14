package com.application.melanieh.kk;

import com.google.android.gms.wallet.WalletConstants;

/**
 * Created by melanieh on 5/30/17.
 */

public class Constants {

    public Constants () {
        //
    }

    /** fragment argument and intent extra keys **/
    public static final String CART_ITEM_DATA_KEY = "Cart Item data";
    public static final String DOMESTIC_SHIP_EST_KEY = "Domestic shipping estimate";
    public static final String CATEGORY_EXTRA_KEY = "transition image URL string";
    public static final String TRANSITION_IMAGE_KEY = "transition image URL string";
    public static final String TRANSITION_TEXT_KEY_NAME = "transition text-product name";
    public static final String TRANSITION_TEXT_KEY_COST = "transition text-product cost";
    public static final String CART_ITEMS_DATA_KEY = "Cart Item data";

    /** image URLs */
    public static final String CANDLES_CATEGORY_IMAGE_URL =
            "https://pixabay.com/get/eb3cb80c21f7073ecd0b4200ee4a419ee66ae3d018b6154392f5c871_1920.jpg";
    public static final String GIFTS_CATEGORY_IMAGE_URL =
            "https://pixabay.com/get/eb3cb8072dfd023ecd0b4200ee4a419ee66ae3d018b6154392f8c67d_1920.jpg";
    public static final String GIFT_BASKETS_CATEGORY_IMAGE_URL =
            "https://pixabay.com/get/eb36b9062ff3083ecd0b4200ee4a419ee66ae3d018b6154392f9c47d_1920.jpg";

    public static final String JAR_CANDLES_URL =
            "https://i.pinimg.com/736x/d3/97/d4/d397d47ef453e8dad28e5255394578a7--jar-candles-candle-making.jpg";
    public static final String TEALIGHTS_URL =
            "https://pixabay.com/get/e83db8062ff7083ecd0b4200ee4a419ee66ae3d018b6154594f0c171_1920.jpg";
//    public static final String TART_BURNERS_URL =;
//    public static final String ELECTRIC_TART_WARMERS_URL =;
//    public static final String REED_DIFFUSERS_URL =;
//    public static final String WOOD_WICK_CANDLES_URL =;

    public static final String LOTIONS_URL =
            "https://pixabay.com/get/e831b70b2afd043ecd0b4200ee4a419ee66ae3d018b6154594f3c07c_1920.png";

    /** query String for downloading photos from business' Facebook page **/
    public static final String FB_GRAPH_API_QUERY_STRING
            = "http://graph.facebook.com/a.713162965384213.1073741827.194374300596418/1552525248114643";

    /** product variety constants **/

    // global "unknown" option
    public static final int VARIETY_UNKNOWN = -1;

    // candle varieties
    public static final int CANDLE_CINNAMON = 101;
    public static final int CANDLE_LAVENDER = 102;
    public static final int CANDLE_LINEN = 103;
    public static final int CANDLE_LEMON = 104;
    public static final int CANDLE_PINE = 105;

    // gift varieties
    // lotions
    public static final int LOTION_CINNAMON = 401;
    public static final int LOTION_LAVENDER = 402;
    public static final int LOTION_LINEN = 403;
    public static final int LOTION_LEMON = 404;
    public static final int lotion_PINE = 405;

    // primitive items

    // gift basket varieties

    public static final int BABY = 201;
    public static final int GRADUATION = 202;
    public static final int HOUSEWARMING = 203;
    public static final int GET_WELL = 204;
    public static final int THANK_YOU = 205;
    public static final int TEACHER = 206;

    // holidays
    public static final int NEW_YEAR = 301;
    public static final int VALENTINES_DAY = 302;
    public static final int ST_PATRICKS_DAY = 303;
    public static final int EASTER = 304;
    public static final int PASSOVER = 305;
    public static final int MOTHERS_DAY = 306;
    public static final int MEMORIAL_DAY = 307;
    public static final int FATHERS_DAY = 308;
    public static final int FOURTH_JULY = 309;
    public static final int LABOR_DAY = 310;
    public static final int ROSH_HOSHANAH = 311;
    public static final int YOM_KIPPUR = 312;
    public static final int HALLOWEEN = 313;
    public static final int THANKSGIVING= 314;
    public static final int CHANUKAH = 315;
    public static final int CHRISTMAS = 316;

    /** Shopify SDK/API **/
    public static final String MERCHANT_NAME="Kountry Klutter";

    /** Stripe/Android Pay integration **/
    public static final String STRIPE_API_VERSION= "1.0";
    public static final String CURRENCY_CODE_USD = "USD";
    public static final int WALLET_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;

    /** eventbus for cart and invoice updates **/
    public static final int SUBJECT_CART_UPDATE= 0;
    public static final int SUBJECT_INVOICE_UPDATE = 1;

    /** activity request codes **/
    public static final int REQUEST_CODE_LOAD_MASKED_WALLET = 100;

}
