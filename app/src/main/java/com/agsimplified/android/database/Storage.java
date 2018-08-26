package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.agsimplified.android.model.Destinationable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Storage implements Serializable, Destinationable {
    public Storage() {}

    public static String TABLE_NAME = "storages";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "site_id INTEGER",
            "name TEXT NOT NULL",
            "date_constructed TEXT",
            "description TEXT",
            "gps TEXT",
            "status TEXT",
            "geo_json TEXT",
            "storage_type_id INTEGER"
    };
    
    private int id;
    private int siteId;
    private String name;
    private String dateConstructed;
    private String description;
    private String gps;
    private String status;
    private String geoJson;
    private int storageTypeId;

    public Storage(JSONObject obj) {
        try {
            id = obj.optInt("id");
            siteId = obj.optInt("site_id");
            name = obj.getString("name");
            dateConstructed = obj.getString("date_constructed");
            description = obj.getString("description");
            gps = obj.getString("gps");
            status = obj.getString("status");
            geoJson = obj.getString("geo_json");
            storageTypeId = obj.optInt("storage_type_id");
        } catch (JSONException e) {
            Log.e("nfs", "Storage(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public Storage(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        siteId = c.getInt(c.getColumnIndex("site_id"));
        name = c.getString(c.getColumnIndex("name"));
        dateConstructed = c.getString(c.getColumnIndex("date_constructed"));
        description = c.getString(c.getColumnIndex("description"));
        gps = c.getString(c.getColumnIndex("gps"));
        status = c.getString(c.getColumnIndex("status"));
        geoJson = c.getString(c.getColumnIndex("geo_json"));
        storageTypeId = c.getInt(c.getColumnIndex("storage_type_id"));
    }

    public static Storage find(int id) {
        Storage item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            item = new Storage(cursor);
            cursor.close();
        } else {
            Log.w("nfs", "STORAGE(" + id + ") NOT FOUND");
        }

        return item;
    }

    public static List<Storage> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Storage> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Storage(cursor));
            }

            cursor.close();
        }

        return list;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("site_id", siteId);
        cv.put("name", name);
        cv.put("date_constructed", dateConstructed);
        cv.put("description", description);
        cv.put("gps", gps);
        cv.put("status", status);
        cv.put("geo_json", geoJson);
        cv.put("storage_type_id", storageTypeId);
        return cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateConstructed() {
        return dateConstructed;
    }

    public void setDateConstructed(String dateConstructed) {
        this.dateConstructed = dateConstructed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }

    public int getStorageTypeId() {
        return storageTypeId;
    }

    public void setStorageTypeId(int storageTypeId) {
        this.storageTypeId = storageTypeId;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", name='" + name + '\'' +
                ", dateConstructed='" + dateConstructed + '\'' +
                ", description='" + description + '\'' +
                ", gps='" + gps + '\'' +
                ", status='" + status + '\'' +
                ", geoJson='" + geoJson + '\'' +
                ", storageTypeId=" + storageTypeId +
                '}';
    }

    public static Storage[] jsonToArray(JSONArray jsonArray) {
        List<Storage> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Storage(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Storage.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        Storage[] array = new Storage[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private SQLiteDatabase mDb;

        PopulateAsync(SQLiteDatabase db) {
            super();
            Log.d("nfs", "Storage.PopulateAsync()");
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "Storage.PopulateAsync.doInBackground()");

            Storage[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " STORAGES");
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            for (Storage item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
            }

            Log.d("nfs", "Storage.PopulateAsync() DONE");

            return null;
        }
    }
}