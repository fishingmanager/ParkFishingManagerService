package com.fishing.namtran.fishingmanagerservice.dbconnection;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nam Tran on 10/30/2017.
 */

public class UserManager {

    private SQLiteDatabase db;
    private Context context;

    public UserManager(Context context)
    {
        this.context = context;
    }

    public void createUser(String mEmail, String mPassword, String role) {

        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(User.Properties.EMAIL, mEmail);
        values.put(User.Properties.PASSWORD, mPassword);
        values.put(User.Properties.ROLE, role);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(User.Properties.TABLE_NAME, null, values);

        //close connection
        db.close();
    }

    public boolean checkUserExisted(String email)
    {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                User.Properties._ID,
                User.Properties.EMAIL,
                User.Properties.PASSWORD,
                User.Properties.ROLE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = User.Properties.EMAIL + " = ?";
        String[] selectionArgs = { email };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                User.Properties._ID + " DESC";

        Cursor cursor = db.query(
                User.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToNext()) {
            if(email == cursor.getString(cursor.getColumnIndexOrThrow(User.Properties.EMAIL))) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public boolean UserLogin(String email, String password)
    {
        InitializeDatabase mDbHelper = new InitializeDatabase(context);
        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                User.Properties._ID,
                User.Properties.EMAIL,
                User.Properties.PASSWORD,
                User.Properties.ROLE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = User.Properties.EMAIL + " = ? AND " + User.Properties.PASSWORD + " = ?";
        //String selection = User.Properties.EMAIL + " = '" + email + "' AND " + User.Properties.PASSWORD + " = '" + password + "'";
        String[] selectionArgs = { email, password };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                User.Properties._ID + " DESC";

        Cursor cursor = db.query(
                User.Properties.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        while(cursor.moveToNext()) {
            if(email.equals(cursor.getString(cursor.getColumnIndexOrThrow(User.Properties.EMAIL)))
                    && password.equals(cursor.getString(cursor.getColumnIndexOrThrow(User.Properties.PASSWORD)))) {
                //
                SessionManagement session = new SessionManagement(context);
                session.createLoginSession(email);
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }
}
//https://stackoverflow.com/questions/19194576/how-do-i-view-the-sqlite-database-on-an-android-device
//https://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
