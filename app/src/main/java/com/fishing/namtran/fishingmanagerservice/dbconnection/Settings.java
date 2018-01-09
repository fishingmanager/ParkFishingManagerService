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
        public static final String SERVER_EMAIL = "server_email";
        public static final String SERVER_PASSWORD = "server_password";
        public static final String RECEIVE_EMAIL = "receive_email";
        public static final String PACKAGE_FISHING = "package_fishing";
        public static final String PRICE_FISHING = "price_fishing";
        public static final String PRICE_BUY_FISH = "price_buy_fish";
    }
}
