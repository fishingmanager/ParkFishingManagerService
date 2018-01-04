package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.provider.BaseColumns;

/**
 * Created by nam.tran on 10/18/2017.
 */

public final class User {
    private User() {}

    /* Inner class that defines the table contents */
    public static class Properties implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String ROLE = "role";
    }
}
