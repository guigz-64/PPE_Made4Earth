package com.jordan_remi.app.codescan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jordan_remi.app.codescan.Code;

import java.util.ArrayList;

import static com.jordan_remi.app.codescan.Ressource.format;
import static com.jordan_remi.app.codescan.database.CodeContract.FeedEntry.COLUMN_NAME_TITLE_CODE;
import static com.jordan_remi.app.codescan.database.CodeContract.FeedEntry.COLUMN_NAME_TITLE_DATE;
import static com.jordan_remi.app.codescan.database.CodeContract.FeedEntry.COLUMN_NAME_TITLE_STATUS;
import static com.jordan_remi.app.codescan.database.CodeContract.FeedEntry.COLUMN_NAME_TITLE_TYPE;
import static com.jordan_remi.app.codescan.database.CodeContract.FeedEntry.TABLE_NAME;
import static com.jordan_remi.app.codescan.database.cursorToArrayList.cursorToArrayListObject;

/**
 * Created by revivier on 26/01/18.
 */



public class CodeDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CodeBase.db";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    CodeContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE_CODE + " TEXT," +
                    COLUMN_NAME_TITLE_TYPE + " TEXT," +
                    CodeContract.FeedEntry.COLUMN_NAME_TITLE_DATE + " TEXT," +
                    CodeContract.FeedEntry.COLUMN_NAME_TITLE_INFOS + " TEXT," +
                    COLUMN_NAME_TITLE_STATUS + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public CodeDbHelper(Context context) {
        super(context, DATABASE_NAME, /*factory*/ null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Ajouter un code
    public void addCode(Code code) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE_CODE, code.getCode());
        values.put(COLUMN_NAME_TITLE_TYPE, code.getType());
        values.put(COLUMN_NAME_TITLE_STATUS, code.getStatus());
        values.put(COLUMN_NAME_TITLE_DATE, format.format(code.getDate()));

        // Inserting Row

        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public void updateCode(Code code) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE_CODE, code.getCode());
        values.put(COLUMN_NAME_TITLE_TYPE, code.getType());
        values.put(COLUMN_NAME_TITLE_STATUS, code.getStatus());
        values.put(COLUMN_NAME_TITLE_DATE, format.format(code.getDate()));

        db.update(TABLE_NAME, values, String.format("%s = %s", CodeContract.FeedEntry._ID, code.getId()), null);
        db.close(); // Closing database connection
    }


    //Recup tous codes
    public ArrayList<Code> getAllCodes() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                + TABLE_NAME, null);
        ArrayList<Code> result = cursorToArrayListObject(cursor,
                Code.class);
        sqLiteDatabase.close();
        return result;
    }

}

