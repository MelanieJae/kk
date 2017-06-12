package com.application.melanieh.kk;

/**
 * Created by melanieh on 5/30/17.
 */

public class Constants {

    public Constants () {
        //
    }

    /** fragment argument and intent extra keys **/
    public static final String CATEGORY_EXTRA_KEY = "transition image URL string";
    public static final String TRANSITION_IMAGE_KEY = "transition image URL string";
    public static final String TRANSITION_TEXT_KEY_NAME = "transition text-product name";
    public static final String TRANSITION_TEXT_KEY_COST = "transition text-product cost";

    /** sample image */
    public static final String SAMPLE_IMAGE_URL = BuildConfig.SAMPLE_IMAGE_URL;

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

}
