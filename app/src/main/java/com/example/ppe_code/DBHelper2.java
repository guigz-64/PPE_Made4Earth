package com.example.ppe_code;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper2 extends SQLiteOpenHelper {

    public DBHelper2(@Nullable Context context) {
        super(context, "Appdata2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Appdetails2(code INTEGER primary key,brand TEXT,category TEXT, size TEXT, color TEXT, price INTEGER, " +
                "site TEXT, material TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Appdetails2");

    }

    public boolean insertUserDate(long code, String brand,String category,String color,String size, int price, String site, String material) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("brand", brand);
        contentValues.put("category",category );
        contentValues.put("size",size );
        contentValues.put("color", color);
        contentValues.put("price", price);
        contentValues.put("site", site);
        contentValues.put("material", material);

        long result = DB.insert("Appdetails2", null, contentValues);

        if (result == -1) {
            return false;
        }
        return true;

    }

    public boolean delete(long code) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Appdetails2 where code = ?", new String[]{String.valueOf(code)});

        if (cursor.getCount() > 0) {
            long result = DB.delete("Appdetails2", "code=?", new String[]{String.valueOf(code)});

            if (result == -1) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

    public Boolean updateuserdata(long code, String brand,String category,String color,String size, int price, String site, String material){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("brand", brand);
        contentValues.put("category",category );
        contentValues.put("color", color);
        contentValues.put("size",size );
        contentValues.put("price", price);
        contentValues.put("site", site);
        contentValues.put("material", material);

        Cursor cursor = DB.rawQuery("Select * from Appdetails2 where code = ?", new String[]{String.valueOf(code)});

        if(cursor.getCount()>0) {
            long result = DB.update("AppDetails2", contentValues, "code=?", new String[]{String.valueOf(code)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;

        }
    }

    public Cursor getData() {

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Appdetails2", null);
        return cursor;

    }
}
