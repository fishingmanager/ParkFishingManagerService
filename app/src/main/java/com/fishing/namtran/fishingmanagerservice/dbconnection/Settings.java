package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.provider.BaseColumns;

/**
 * Created by nam.tran on 10/18/2017.
 */

public final class Settings {
    private Settings() {
    }

    /* Inner class that defines the table contents */
    public static class Properties implements BaseColumns {
        public static final String TABLE_NAME = "settings";
        public static final String PACKAGE_FISHING = "package_fishing";
        public static final String PRICE_FISHING = "price_fishing";
        public static final String PRICE_FEED_TYPE = "price_feed_type";
        public static final String PRICE_BUY_FISH = "price_buy_fish";
    }
}
