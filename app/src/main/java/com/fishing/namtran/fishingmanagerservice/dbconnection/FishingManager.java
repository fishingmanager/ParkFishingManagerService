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

    public long createFishingEntry(long mCustomerId, String mDateIn, String mNote) {

        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getWritableDatabase();
        String datetime = mDateIn.split(" ")[0];
        long fishingId = -1;

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Fishings.Properties.CUSTOMER_ID, mCustomerId);
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

    public String getCustomerByFishingId(String fishingId) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Fishings.Properties.CUSTOMER_ID,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Fishings.Properties._ID + " = ?";
        String[] selectionArgs = { fishingId };

        Cursor cursor = db.query(
                Fishings.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        String cusId = "";

        if(cursor.moveToNext())
        {
            cusId = cursor.getString(cursor.getColumnIndexOrThrow(Fishings.Properties.CUSTOMER_ID));
        }
        cursor.close();
        db.close();
        mDbHelper.close();
        return cusId;
    }

    public Cursor getFishingAllEntries() {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Fishings.Properties.CUSTOMER_ID,
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

    public Cursor getFishingEntry(String mCustomerId) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Fishings.Properties.CUSTOMER_ID,
                Fishings.Properties.DATE_IN,
                Fishings.Properties.DATE_OUT,
                Fishings.Properties.NOTE,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Fishings.Properties.CUSTOMER_ID + " = ?";
        String[] selectionArgs = { mCustomerId };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Fishings.Properties._ID + " ASC";

        Cursor cursor = db.query(
                Fishings.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
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
                Fishings.Properties.CUSTOMER_ID,
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

    public boolean checkFishingEntryExisted(long mCustomerId, String dateIn) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Fishings.Properties.CUSTOMER_ID,
                Fishings.Properties.DATE_IN,
                Fishings.Properties.DATE_OUT,
                Fishings.Properties.NOTE,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Fishings.Properties.CUSTOMER_ID + " = ? AND " + Fishings.Properties.DATE_IN + " LIKE '" + dateIn + "%' AND " + Fishings.Properties.DATE_OUT + " IS NULL";
        String[] selectionArgs = { Long.toString(mCustomerId) };

        Cursor cursor = db.query(
                Fishings.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.moveToNext()) {
            if(mCustomerId == cursor.getLong(cursor.getColumnIndexOrThrow(Fishings.Properties.CUSTOMER_ID))) {
                cursor.close();
                return true;
            }
        }

        cursor.close();
        return false;
    }

    public Cursor getFishingEntries(String currentDate) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        String query = "SELECT fishing." + Fishings.Properties.DATE_IN + ", fishing." + Fishings.Properties.DATE_OUT  + ", fishing." + Fishings.Properties.BUY_FISH + ", fishing." + Fishings.Properties.TOTAL_FISH + ", fishing." + Fishings.Properties.NOTE
                                + ", fishing." + Fishings.Properties._ID + ", fishing." + Fishings.Properties.TOTAL_MONEY + " , customer." + Customers.Properties.FULLNAME + ", customer." + Customers.Properties.MOBILE + ", customer." + Customers.Properties._ID + " AS customerId" +
                        " FROM " +  Fishings.Properties.TABLE_NAME + " fishing, " + Customers.Properties.TABLE_NAME + " customer " +
                        " WHERE " + "customer." + Customers.Properties._ID + " = " + "fishing." + Fishings.Properties.CUSTOMER_ID + " AND fishing." + Fishings.Properties.DATE_IN + " LIKE '" + currentDate + "%'" ;

        return db.rawQuery(query, null);
    }

    public Cursor getFishingEntriesById(String fishingId) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        String query = "SELECT fishing." + Fishings.Properties.DATE_IN + ", fishing." + Fishings.Properties.DATE_OUT + ", fishing." + Fishings.Properties.BUY_FISH + ", fishing." + Fishings.Properties.TOTAL_FISH + ", fishing." + Fishings.Properties.NOTE
                + ", customer." + Customers.Properties._ID + ", customer." + Customers.Properties.FULLNAME + ", customer." + Customers.Properties.MOBILE +
                " FROM " +  Fishings.Properties.TABLE_NAME + " fishing, " + Customers.Properties.TABLE_NAME + " customer " +
                " WHERE " + "customer." + Customers.Properties._ID + " = " + "fishing." + Fishings.Properties.CUSTOMER_ID +
                " AND " +  "fishing." + Fishings.Properties._ID + " = " + fishingId;

        return db.rawQuery(query, null);
    }
}
