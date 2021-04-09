package com.jordan_remi.app.codescan.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by revivier on 26/01/18.
 */

public final class CodeContract {
    private CodeContract() {}

    public static final String CONTENT_AUTHORITY = "fr.iut.pm.spa";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CODES = "codes";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CODES);


    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        /** The content URI to access the dog data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CODES);

        /** The MIME type of the {@link #CONTENT_URI} for a list of codes. */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CODES;
        /** The MIME type of the {@link #CONTENT_URI} for a single code. */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CODES;


        public static final String TABLE_NAME = "CodeScanDB";
        public static final String COLUMN_NAME_TITLE_CODE = "code";
        public static final String COLUMN_NAME_TITLE_DATE = "date";
        public static final String COLUMN_NAME_TITLE_TYPE = "type";
        public static final String COLUMN_NAME_TITLE_INFOS = "informations";
        public static final String COLUMN_NAME_TITLE_STATUS = "status";


    }
}
