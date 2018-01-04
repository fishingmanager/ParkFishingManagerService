package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nam Tran on 10/30/2017.
 */

public class SettingsManager {

    private SQLiteDatabase db;
    private Context context;

    public SettingsManager(Context context)
    {
        this.context = context;
    }

    public boolean updateSettings(String mId, String mPackageFishing, String mPriceFishing, String mPriceBuyFish) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Settings.Properties.PACKAGE_FISHING, mPackageFishing);
        values.put(Settings.Properties.PRICE_FISHING, mPriceFishing);
        values.put(Settings.Properties.PRICE_BUY_FISH, mPriceBuyFish);

        // Which row to update, based on the title
        String selection = Settings.Properties._ID + " = ?";
        String[] selectionArgs = { mId };

        int count = db.update(
                Settings.Properties.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        //close connection
        db.close();
        mDbHelper.close();

        return count > 0 ? true : false;
    }

    public Cursor getSettingEntry(String mId) {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Settings.Properties.PACKAGE_FISHING,
                Settings.Properties.PRICE_FISHING,
                Settings.Properties.PRICE_FEED_TYPE,
                Settings.Properties.PRICE_BUY_FISH
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Settings.Properties._ID + " = ?";
        String[] selectionArgs = { mId };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                User.Properties._ID + " DESC";

        Cursor cursor = db.query(
                Settings.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return cursor;
    }

}

