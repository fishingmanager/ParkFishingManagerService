package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.provider.BaseColumns;

/**
 * Created by nam.tran on 10/18/2017.
 */

public final class Customers {
    private Customers() {}

    /* Inner class that defines the table contents */
    public static class Properties implements BaseColumns {
        public static final String TABLE_NAME = "customers";
        public static final String FULLNAME = "fullname";
        public static final String MOBILE = "mobile";
        public static final String GENDER = "gender";
        public static final String AGE = "age";
        public static final String ADDRESS = "address";
        public static final String EMAIL = "email";
        public static final String NOTE = "note";
    }
}
