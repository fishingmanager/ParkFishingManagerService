package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nam Tran on 10/30/2017.
 */

public class FishingManager {

    private SQLiteDatabase db;
    private Context context;

    public FishingManager(Context context) {
        this.context = context;
    }

    public long createFishingEntry(String mFullName, String mDateIn, String mNote) {

        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getWritableDatabase();
        long fishingId = -1;

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Fishings.Properties.FULLNAME, mFullName);
        values.put(Fishings.Properties.DATE_IN, mDateIn);
        values.put(Fishings.Properties.NOTE, mNote);

        // Insert the new row, returning the primary key value of the new row
        fishingId = db.insert(Fishings.Properties.TABLE_NAME, null, values);

        //close connection
        db.close();
        return fishingId;
    }

    public boolean updateCloseFishingEntry(String mFishingId, String mDateOut, String mBuyFish, String mTotalFish, String mTotalMoney, String mNote) {

        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Fishings.Properties.DATE_OUT, mDateOut);
        values.put(Fishings.Properties.BUY_FISH, mBuyFish);
        values.put(Fishings.Properties.TOTAL_FISH, mTotalFish);
        values.put(Fishings.Properties.TOTAL_MONEY, mTotalMoney);
        values.put(Fishings.Properties.NOTE, mNote);

        // Which row to update, based on the title
        String selection = Fishings.Properties._ID + " = ?";
        String[] selectionArgs = { mFishingId };

        //Update fishings
        db.update(
                Fishings.Properties.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        //close connection
        db.close();
        return true;
    }

    public boolean changeCloseFishingEntry(String mFishingId, String fullname, String mDateIn, String mDateOut, String mBuyFish, String mTotalFish, String mTotalMoney, String mNote) {

        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Fishings.Properties.FULLNAME, fullname);
        values.put(Fishings.Properties.DATE_IN, mDateIn);
        values.put(Fishings.Properties.DATE_OUT, mDateOut);
        values.put(Fishings.Properties.BUY_FISH, mBuyFish);
        values.put(Fishings.Properties.TOTAL_FISH, mTotalFish);
        values.put(Fishings.Properties.TOTAL_MONEY, mTotalMoney);
        values.put(Fishings.Properties.NOTE, mNote);

        // Which row to update, based on the title
        String selection = Fishings.Properties._ID + " = ?";
        String[] selectionArgs = { mFishingId };

        //Update fishings
        db.update(
                Fishings.Properties.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        //close connection
        db.close();
        return true;
    }

    public Cursor getFishingAllEntries() {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Fishings.Properties.FULLNAME,
                Fishings.Properties.DATE_IN,
                Fishings.Properties.DATE_OUT,
                Fishings.Properties.NOTE,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Fishings.Properties._ID + " ASC";

        Cursor cursor = db.query(
                Fishings.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return cursor;
    }

    public Cursor getFishingEntryByFishingId(String mFishingId) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Fishings.Properties.FULLNAME,
                Fishings.Properties.DATE_IN,
                Fishings.Properties.DATE_OUT,
                Fishings.Properties.NOTE,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Fishings.Properties._ID + " = ?";
        String[] selectionArgs = { mFishingId };

        Cursor cursor = db.query(
                Fishings.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        return cursor;
    }

    public Cursor getFishingEntries(String currentDate) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        String query = "SELECT fishing." + Fishings.Properties.DATE_IN + ", fishing." + Fishings.Properties.DATE_OUT  + ", fishing." + Fishings.Properties.BUY_FISH + ", fishing." + Fishings.Properties.TOTAL_FISH + ", fishing." + Fishings.Properties.NOTE
                                + ", fishing." + Fishings.Properties._ID + ", fishing." + Fishings.Properties.TOTAL_MONEY + ", fishing." + Fishings.Properties.FULLNAME +
                        " FROM " +  Fishings.Properties.TABLE_NAME + " fishing" +
                        " WHERE fishing." + Fishings.Properties.DATE_IN + " LIKE '" + currentDate + "%'" ;

        return db.rawQuery(query, null);
    }

    public Cursor getFishingEntriesById(String fishingId) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        String query = "SELECT fishing." + Fishings.Properties.DATE_IN + ", fishing." + Fishings.Properties.DATE_OUT + ", fishing." + Fishings.Properties.BUY_FISH + ", fishing." + Fishings.Properties.TOTAL_FISH + ", fishing." + Fishings.Properties.NOTE +
                ", fishing." + Fishings.Properties.FULLNAME + ", fishing." + Fishings.Properties.TOTAL_MONEY +
                " FROM " +  Fishings.Properties.TABLE_NAME + " fishing" +
                " WHERE fishing." + Fishings.Properties._ID + " = " + fishingId;

        return db.rawQuery(query, null);
    }
}
