package com.jordan_remi.app.codescan.database;


/**
 * Created by revivier on 26/01/18.
 */

public class DatabaseConnect
{

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CodeContract.FeedEntry.TABLE_NAME + " (" +
                    CodeContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    CodeContract.FeedEntry.COLUMN_NAME_TITLE_CODE + " TEXT," +
                    CodeContract.FeedEntry.COLUMN_NAME_TITLE_TYPE + " TEXT," +
                    CodeContract.FeedEntry.COLUMN_NAME_TITLE_DATE + " TEXT," +
                    CodeContract.FeedEntry.COLUMN_NAME_TITLE_INFOS + " TEXT)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CodeContract.FeedEntry.TABLE_NAME;


}
