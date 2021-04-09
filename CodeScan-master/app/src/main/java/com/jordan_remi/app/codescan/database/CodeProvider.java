package com.jordan_remi.app.codescan.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.jordan_remi.app.codescan.R;

/**
 * Created by revivier on 02/02/18.
 */

public class CodeProvider extends ContentProvider {
        private static final String LOG_TAG = CodeProvider.class.getCanonicalName();

        private static final int CODES = 1000;
        private static final int CODES_ID = 1001;

        private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        public static final String EQUALS = "=?";

        static {
            uriMatcher.addURI(CodeContract.CONTENT_AUTHORITY, CodeContract.PATH_CODES, CODES);
            uriMatcher.addURI(CodeContract.CONTENT_AUTHORITY, CodeContract.PATH_CODES + "/#", CODES_ID);
        }

        private CodeDbHelper codeDbHelper;

        @Override
        public boolean onCreate() {
//            codeDbHelper = new CodeDbHelper(getContext());
            return true;
        }

        @Override
        public String getType(Uri uri) {
            switch (uriMatcher.match(uri)) {
                case CODES:
                    return CodeContract.FeedEntry.CONTENT_LIST_TYPE;
                case CODES_ID:
                    return CodeContract.FeedEntry.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalStateException(getContext().getResources().getString(R.string.unknown_uri_error) + uri);
            }
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
       //     SQLiteDatabase dogDatabase = codeDbHelper.getReadableDatabase();

            Cursor cursor;
/*
            switch (uriMatcher.match(uri)) {
                case CODES:
                 //   cursor = dogDatabase.query(CodeContract.FeedEntry.TABLE_NAME,
                            projection, selection, selectionArgs, null, null, sortOrder);
                    break;
                case CODES_ID:
                    selection = CodeContract.FeedEntry._ID + EQUALS;
                    selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                    cursor = dogDatabase.query(CodeContract.FeedEntry.TABLE_NAME,
                            projection, selection, selectionArgs, null, null, sortOrder);
                    break;
                default:
                    throw new IllegalArgumentException(getContext().getResources().getString(R.string.unknown_uri_error) + uri);
            }
            cursor.setNotificationUri(getContext().getContentResolver(), uri);*/
            return null;
        }


        @Override
        public Uri insert(Uri uri, ContentValues values) {
            switch (uriMatcher.match(uri)) {
                case CODES:
                    return checkAndInsertDog(uri, values);
                default:
                    throw new IllegalArgumentException(getContext().getResources().getString(R.string.insertion_uri_error) + uri);
            }
        }

        private Uri checkAndInsertDog(Uri uri, ContentValues values) {
            SQLiteDatabase codeDatabase = codeDbHelper.getWritableDatabase();

            // TODO: check that the ContentValues contains valid data
            long id = codeDatabase.insert(CodeContract.FeedEntry.TABLE_NAME, null, values);
            if (id == -1) {
                Log.e(LOG_TAG, "Failed to insert row for " + uri);
                return null;
            }

            getContext().getContentResolver().notifyChange(uri, null);

            return ContentUris.withAppendedId(uri, id);
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            SQLiteDatabase dogDatabase = codeDbHelper.getWritableDatabase();
            int rowsDeleted;
            switch (uriMatcher.match(uri)) {
                case CODES:
                    rowsDeleted = dogDatabase.delete(CodeContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
                    break;
                case CODES_ID:
                    selection = CodeContract.FeedEntry._ID + EQUALS;
                    selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                    rowsDeleted =  dogDatabase.delete(CodeContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException(getContext().getResources().getString(R.string.delete_uri_error) + uri);
            }
            if (rowsDeleted > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return rowsDeleted;
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            switch (uriMatcher.match(uri)) {
                case CODES:
                    return checkAndUpdateDog(uri, values, selection, selectionArgs);
                case CODES_ID:
                    selection = CodeContract.FeedEntry._ID + EQUALS;
                    selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                    return checkAndUpdateDog(uri, values, selection, selectionArgs);
                default:
                    throw new IllegalArgumentException(getContext().getResources().getString(R.string.update_uri_error) + uri);
            }
        }

        private int checkAndUpdateDog(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            if (values.size() == 0) return 0;

            SQLiteDatabase database = codeDbHelper.getWritableDatabase();

            // TODO: check that the ContentValues contains valid data
            int rowsUpdated = database.update(CodeContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
            if (rowsUpdated > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return rowsUpdated;
        }
    }


