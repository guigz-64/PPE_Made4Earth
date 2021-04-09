package com.jordan_remi.app.codescan;

import com.jordan_remi.app.codescan.database.CodeDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by jorda on 17/02/2018.
 */

public class Ressource {

    static CodeDbHelper db;
    public static final DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.FRANCE);

}
