package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class AbstractTable implements Serializable {
    private static Gson gson = new Gson();

    abstract ContentValues getContentValues();

    abstract void objectFromCursor(final Cursor cursor);

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public static String getTableName(Class clazz) {
        try {
            return (String) clazz.getField("TABLENAME").get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Log.e("nfs", "getTableName(" + clazz.getSimpleName() + ": " + e.getLocalizedMessage());
            return null;
        }
    }

    static <T> T fromJson(final Class<T> clazz, final String json) {
        return gson.fromJson(json, clazz);
    }

    // https://stackoverflow.com/a/28805158
    public static <T> List<T> listFromJson(final Class<T[]> clazz, final JSONArray arr) {
        return listFromJson(clazz, arr.toString());
    }

    private static <T> List<T> listFromJson(final Class<T[]> clazz, final String json) {
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(gson.fromJson(json, clazz)));
        }
    }

    public static <T extends AbstractTable> T fromCursor(final Class<T> clazz, final Cursor cursor) throws IllegalAccessException, InstantiationException {
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

    public static <T> String listToJson(List<T> list) {
        return gson.toJson(list);
    }

    public static <T extends AbstractTable> List<T> all(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM " + getTableName(clazz) + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<T> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(fromCursor(clazz, cursor));
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
            String tableName = getTableName(clazz);
            Log.d("nfs", "PopulateAsync.doInBackground(" + tableName + ")");

            JSONArray jsonArray = jsonArrays[0];
            String json;
            T item;

            try {
                int count = jsonArray.length();
                Log.d("nfs", "LOADING " + tableName + ": " + count + " RECORDS");
                dbHelper.onTableLoadStart(tableName, count);

                mDb.execSQL("DELETE FROM " + tableName);

                for (int i = 0; i < count; i++) {
                    json = jsonArray.getJSONObject(i).toString();
                    item = gson.fromJson(json, clazz);

                    if (mDb.insertOrThrow(tableName, null, item.getContentValues()) == -1) {
                        Log.e("nfs", "FAILED TO INSERT " + tableName + " <" + item.toString() + ">");
                    }

                    dbHelper.onTableLoadProgress(tableName, i+1);
                }
            } catch (JSONException e) {
                Log.e("nfs", "JSON ERROR " + tableName + ": " + e.getLocalizedMessage());
            }

            dbHelper.onTableLoadEnd(tableName);
            Log.d("nfs", "PopulateAsync(" + tableName + ") DONE");

            return null;
        }
    }
}