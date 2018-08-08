package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoadSheet implements Serializable {
    public LoadSheet(){}

    public static String TABLE_NAME = "load_sheets";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "contact_id INTEGER",
            "distribution_sale_id INTEGER",
            "date TEXT"
    };
    
    private int id;
    private int contactId;
    private int distributionSaleId;
    private String date;

    public LoadSheet(JSONObject obj) {
        try {
            id = obj.optInt("id");
            contactId = obj.optInt("contact_id");
            distributionSaleId = obj.optInt("distribution_sale_id");
            date = obj.getString("date");
        } catch (JSONException e) {
            Log.e("nfs", "LoadSheet(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public LoadSheet(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        contactId = c.getInt(c.getColumnIndex("contact_id"));
        distributionSaleId = c.getInt(c.getColumnIndex("distribution_sale_id"));
        date = c.getString(c.getColumnIndex("date"));
    }

    public static List<LoadSheet> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<LoadSheet> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new LoadSheet(cursor));
            }

            cursor.close();
        }

        return list;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("contact_id", contactId);
        cv.put("distribution_sale_id", distributionSaleId);
        cv.put("date", date);
        return cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getDistributionSaleId() {
        return distributionSaleId;
    }

    public void setDistributionSaleId(int distributionSaleId) {
        this.distributionSaleId = distributionSaleId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LoadSheet{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", distributionSaleId=" + distributionSaleId +
                ", date='" + date + '\'' +
                '}';
    }

    public static LoadSheet[] jsonToArray(JSONArray jsonArray) {
        List<LoadSheet> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new LoadSheet(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "LoadSheet.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        LoadSheet[] array = new LoadSheet[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private SQLiteDatabase mDb;

        PopulateAsync(SQLiteDatabase db) {
            super();
            Log.d("nfs", "LoadSheet.PopulateAsync()");
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "LoadSheet.PopulateAsync.doInBackground()");

            LoadSheet[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " LoadSheetS");
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            for (LoadSheet item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
            }

            Log.d("nfs", "LoadSheet.PopulateAsync() DONE");

            return null;
        }
    }
}