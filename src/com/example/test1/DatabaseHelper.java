package com.example.test1;

import android.provider.BaseColumns;

public final class DatabaseHelper {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
	    FeedEntry._ID + " INTEGER PRIMARY KEY," +
	    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP + 
	    TEXT_TYPE + COMMA_SEP +
	     // Any other options for the CREATE command
	    " )";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
	
	
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatabaseHelper() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_DATA = "data";
    }
}