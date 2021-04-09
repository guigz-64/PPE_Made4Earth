package com.example.ppe_code;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "Appdata11.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("create Table Appdetails1(code INTEGER primary key,marque TEXT, price INTEGER, site TEXT,category TEXT, material TEXT,environment TEXT, human TEXT, health TEXT, animal TEXT, size TEXT,color TEXT,note INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Appdetails1");

    }

    public boolean insertUserDate(long code, String marque, int price,String category, String site, String material,String size,String color, String environment,String human,String health,String animal,int note) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("marque", marque);
        contentValues.put("price", price);
        contentValues.put("site", site);
        contentValues.put("material", material);
        contentValues.put("size",size);
        contentValues.put("category",category);
        contentValues.put("environment",environment);
        contentValues.put("human",human);
        contentValues.put("health",health);
        contentValues.put("animal",animal);
        contentValues.put("color",color);
        contentValues.put("note", note);

        long result = DB.insert("Appdetails1", null, contentValues);

        if (result == -1) {
            return false;
        }
        return true;

    }

    public boolean delete(long code) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Appdetails1 where code = ?", new String[]{String.valueOf(code)});

        if (cursor.getCount() > 0) {
            long result = DB.delete("Appdetails1", null, new String[]{String.valueOf(code)});

            if (result == -1) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }




    public Cursor getData(long code) {

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Appdetails1 where code = ?", new String[]{String.valueOf(code)});
        return cursor;

    }


}
