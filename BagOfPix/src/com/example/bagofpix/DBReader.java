package com.example.bagofpix;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBReader extends SQLiteOpenHelper{
	
	private static final String SQL_CREATE_ENTRIES1 =
	    "CREATE TABLE Story (id INTEGER PRIMARY KEY, name TEXT, url TEXT, comment TEXT); ";
	private static final String SQL_CREATE_ENTRIES2 =
	    "CREATE TABLE Photo (id INTEGER PRIMARY KEY, storyId INTEGER, url TEXT, comment TEXT); ";
	
	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS Story" +
	    "DROP TABLE IF EXISTS Photo";
	
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DBReader(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES1);
        db.execSQL(SQL_CREATE_ENTRIES2);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
