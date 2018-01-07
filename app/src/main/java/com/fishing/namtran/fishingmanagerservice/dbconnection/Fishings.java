package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.provider.BaseColumns;

/**
 * Created by nam.tran on 10/18/2017.
 */

public final class Fishings {
    private Fishings() {}

    /* Inner class that defines the table contents */
    public static class Properties implements BaseColumns {
        public static final String TABLE_NAME = "fishings";
        public static final String FULLNAME = "fullname";
        public static final String DATE_IN = "dateIn";
        public static final String DATE_OUT = "dateOut";
        public static final String TOTAL_FISH = "total_fish";
        public static final String BUY_FISH = "buy_fish";
        public static final String NOTE = "note";
        public static final String USER_ID = "userId";
        public static final String TOTAL_MONEY = "total_money";
    }
}
