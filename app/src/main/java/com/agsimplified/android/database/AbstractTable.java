package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class AbstractTable implements Serializable {
    static Gson gson = new Gson();

    abstract ContentValues getContentValues();

    abstract void objectFromCursor(final Cursor cursor);

    static String getTableName(Class clazz) {
        String clazzName = clazz.getName();
        String className = clazzName.substring(clazzName.lastIndexOf('.') + 1);

        switch (className) {
            case "Client":
                return "clients";
            default:
                throw new IllegalArgumentException("unknown class <" + clazzName + ">");
        }
    }

    static <T> T fromJson(final Class<T> clazz, final String json) {
        return gson.fromJson(json, clazz);
    }

    static <T> List<T> listFromJson(final Class<T[]> clazz, final String json) {
        T[] arr = gson.fromJson(json, clazz);
        List<T> list = new ArrayList<>(Arrays.asList(arr));
        return list;
    }

    static <T extends AbstractTable> T fromCursor(final Class<T> clazz, final Cursor cursor) throws IllegalAccessException, InstantiationException {
        T obj = clazz.newInstance();
        obj.objectFromCursor(cursor);
        return obj;
    }

    public static <T extends AbstractTable> T find(final Class<T> clazz, final int id) throws InstantiationException, IllegalAccessException {
        T item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(getTableName(clazz), null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            item = fromCursor(clazz, cursor);
            cursor.close();
        } else {
            Log.w("nfs", clazz.getName() + ".find(" + id + "): NOT FOUND");
        }

        return item;
    }

    public static <T extends AbstractTable> List<T> all(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM " + getTableName(clazz) + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<T> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add((T) fromCursor(clazz, cursor));
            }

            cursor.close();
        }

        return list;
    }

    static class PopulateAsync<T extends AbstractTable> extends AsyncTask<JSONArray, Void, Void> {
        private DbOpenHelper dbHelper;
        private SQLiteDatabase mDb;
        private Class<T> clazz;

        PopulateAsync(Class<T> clazz, DbOpenHelper dbHelper, SQLiteDatabase db) {
            super();
            Log.d("nfs", "PopulateAsync()");
            this.dbHelper = dbHelper;
            this.mDb = db;
            this.clazz = clazz;
        }

        @Override
        protected Void doInBackground(JSONArray... jsonArrays) {
            Log.d("nfs", "PopulateAsync.doInBackground()");
            String tableName = getTableName(clazz);
//            Type arrType = new TypeToken<T[]>(){}.getType();
//            List<T> array = listFromJson(arrType, jsonArrays[0].toString());
            Type listType = new TypeToken<List<T>>(){}.getType();
            List<T> list = gson.fromJson(jsonArrays[0].toString(), listType);
            Log.d("nfs", "LOADING " + list.size() + " CLIENTS");
            dbHelper.onTableLoadStart(tableName, list.size());
            mDb.execSQL("DELETE FROM " + tableName);

            int n = 0;
            for (T item : list) {
                Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(tableName, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
                dbHelper.onTableLoadProgress(tableName, ++n);
            }

            dbHelper.onTableLoadEnd(tableName);
            Log.d("nfs", "PopulateAsync() DONE");

            return null;
        }
    }
}