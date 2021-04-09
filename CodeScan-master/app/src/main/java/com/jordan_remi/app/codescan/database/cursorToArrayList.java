package com.jordan_remi.app.codescan.database;

import android.content.Context;
import android.database.Cursor;

import com.jordan_remi.app.codescan.Code;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.jordan_remi.app.codescan.Ressource.format;

/**
 * Created by revivier on 09/02/18.
 */

public class cursorToArrayList {

    private static final String KEY_NAME = "ADAPT";
    private static ArrayList<Code> lesCodes;
    private static Context context;
    private int lastPosition = -1;
    public static <E> ArrayList<E> cursorToArrayListObject(Cursor cursor,
                                                           Class<E> output) {
        ArrayList<E> arrResult = new ArrayList<E>();
        E object = null;
        Field[] arrFields;
        while (cursor.moveToNext()) {
            try {
                Constructor<E> constructor = output.getConstructor();
                object = constructor.newInstance();
                arrFields = output.getDeclaredFields();
                for (Field field : arrFields) {
                    if (Modifier.isFinal(field.getModifiers())) {
                        continue;
                    }

                    if (cursor.getColumnIndex(field.getName()) == -1) {
                        continue;
                    }

                    field.setAccessible(true);
                    Object type = field.getType();
                    if (type.equals(String.class)) {
                        field.set(object, cursor.getString(cursor
                                .getColumnIndex(field.getName())));
                    } else if (type.equals(int.class)) {
                        field.set(object, cursor.getInt(cursor
                                .getColumnIndex(field.getName())));
                    } else if (type.equals(float.class)) {
                        field.set(object, cursor.getFloat(cursor
                                .getColumnIndex(field.getName())));
                    } else if (type.equals(double.class)) {
                        field.set(object, cursor.getDouble(cursor
                                .getColumnIndex(field.getName())));
                    } else if (type.equals(Date.class)) {
                        try {
                            field.set(object, format.parse(cursor.getString(cursor
                                    .getColumnIndex(field.getName()))));
                        } catch (ParseException e) {
                            field.set(object, Calendar.getInstance().getTime());
                        }
                    }

                }
                arrResult.add((E) object);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
        cursor.close();
        return arrResult;
    }

}
