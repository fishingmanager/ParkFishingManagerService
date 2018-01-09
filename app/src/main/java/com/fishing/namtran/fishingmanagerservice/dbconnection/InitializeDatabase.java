package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nam.tran on 10/18/2017.
 */

public class InitializeDatabase extends SQLiteOpenHelper {

    private static final String SQL_CREATE_SETTINGS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Settings.Properties.TABLE_NAME + " (" +
                    Settings.Properties._ID + " INTEGER PRIMARY KEY," +
                    Settings.Properties.SERVER_EMAIL + " TEXT," +
                    Settings.Properties.SERVER_PASSWORD + " TEXT," +
                    Settings.Properties.RECEIVE_EMAIL + " TEXT," +
                    Settings.Properties.PACKAGE_FISHING + " INTEGER," +
                    Settings.Properties.PRICE_FISHING + " INTEGER," +
                    Settings.Properties.PRICE_BUY_FISH + " INTEGER); ";

    private static final String SQL_CREATE_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + User.Properties.TABLE_NAME + " (" +
                    User.Properties._ID + " INTEGER PRIMARY KEY," +
                    User.Properties.ROLE + " INTEGER," +
                    User.Properties.EMAIL + " TEXT," +
                    User.Properties.PASSWORD + " TEXT); ";

    private static final String SQL_CREATE_FISHINGS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Fishings.Properties.TABLE_NAME + " (" +
                    Fishings.Properties._ID + " INTEGER PRIMARY KEY," +
                    Fishings.Properties.USER_ID + " INTEGER DEFAULT 1," +
                    Fishings.Properties.FULLNAME + " TEXT," +
                    Fishings.Properties.DATE_IN + " DATETIME," +
                    Fishings.Properties.DATE_OUT + " DATETIME," +
                    Fishings.Properties.BUY_FISH + " REAL DEFAULT 0," +
                    Fishings.Properties.TOTAL_FISH + " INTEGER DEFAULT 0," +
                    Fishings.Properties.TOTAL_MONEY + " REAL DEFAULT 0.0," +
                    Fishings.Properties.NOTE + " TEXT); ";

    /*---------------- Insert sample records -----------------------*/

    private static final String SQL_CREATE_USERS_RECORDS_1 =
            "INSERT INTO " + User.Properties.TABLE_NAME + " (" +
                    User.Properties._ID + "," +
                    User.Properties.ROLE + "," +
                    User.Properties.EMAIL + "," +
                    User.Properties.PASSWORD + ") VALUES ( 1, 0, 'nam@gmail.com', '12341234'); ";

    private static final String SQL_CREATE_USERS_RECORDS_2 =
                    "INSERT INTO " + User.Properties.TABLE_NAME + " (" +
                    User.Properties._ID + "," +
                    User.Properties.ROLE + "," +
                    User.Properties.EMAIL + "," +
                    User.Properties.PASSWORD + ") VALUES ( 2, 1, 'can@gmail.com', '12341234'); ";

    private static final String SQL_CREATE_SETTINGS_RECORDS =
            "INSERT INTO " + Settings.Properties.TABLE_NAME + " (" +
                    Settings.Properties._ID + "," +
                    Settings.Properties.SERVER_EMAIL + "," +
                    Settings.Properties.SERVER_PASSWORD + "," +
                    Settings.Properties.RECEIVE_EMAIL + "," +
                    Settings.Properties.PACKAGE_FISHING + "," +
                    Settings.Properties.PRICE_FISHING + "," +
                    Settings.Properties.PRICE_BUY_FISH + ") VALUES ( 1, 'parkfishingmanagerservice@gmail.com', 't260gOm3g', 'parkfishingmanagerservice@gmail.com', 3, 120000, 12000 ); ";

    private static final String SQL_CREATE_FISHINGS_RECORDS =
            "INSERT INTO " + Fishings.Properties.TABLE_NAME + " (" +
                    Fishings.Properties._ID + "," +
                    Fishings.Properties.USER_ID + "," +
                    Fishings.Properties.FULLNAME + "," +
                    Fishings.Properties.DATE_IN + "," +
                    Fishings.Properties.DATE_OUT + "," +
                    Fishings.Properties.BUY_FISH + "," +
                    Fishings.Properties.TOTAL_FISH + "," +
                    Fishings.Properties.TOTAL_MONEY + "," +
                    Fishings.Properties.NOTE + ") VALUES ( 1, 1, 'Anh Nam', '2017/11/06 08:00:59', '2017/11/06 10:00:59', 15000, 0.0, 120000, 'Khong co' ); ";

    /*---------------- Insert sample records -----------------------*/

    private static final String SQL_DELETE_USERS_TABLE =
            "DROP TABLE IF EXISTS " + User.Properties.TABLE_NAME;

    private static final String SQL_DELETE_SETTINGS_TABLE =
            "DROP TABLE IF EXISTS " + Settings.Properties.TABLE_NAME;

    private static final String SQL_DELETE_FISHINGS_TABLE =
            "DROP TABLE IF EXISTS " + Fishings.Properties.TABLE_NAME;

    public InitializeDatabase(Context context) {
        super(context, DbConfig.DATABASE_NAME, null, DbConfig.DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SETTINGS_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_FISHINGS_TABLE);

        db.execSQL(SQL_CREATE_USERS_RECORDS_1);
        db.execSQL(SQL_CREATE_USERS_RECORDS_2);
        db.execSQL(SQL_CREATE_SETTINGS_RECORDS);
        db.execSQL(SQL_CREATE_FISHINGS_RECORDS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_USERS_TABLE);
        db.execSQL(SQL_DELETE_SETTINGS_TABLE);
        db.execSQL(SQL_DELETE_FISHINGS_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
