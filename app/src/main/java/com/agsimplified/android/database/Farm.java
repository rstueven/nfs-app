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

public class Farm implements Serializable {
    public Farm() {}

    public static String TABLE_NAME = "farms";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "site_id INTEGER",
            "name TEXT NOT NULL",
            "contact_id INTEGER",
            "fsa_farm_number TEXT",
            "fsa_tract_number TEXT",
            "status TEXT",
            "crop_zone TEXT",
            "guid TEXT"
    };

    private int id;
    private int siteId;
    private String name;
    private int contactId;
    private String fsaFarmNumber;
    private String fsaTractNumber;
    private String status;
    private String cropZone;
    private String guid;
    
    public Farm(JSONObject obj) {
        try {
            id = obj.optInt("id");
            siteId = obj.optInt("site_id");
            name = obj.getString("name");
            contactId = obj.optInt("contact_id");
            fsaFarmNumber = obj.getString("fsa_farm_number");
            fsaTractNumber = obj.getString("fsa_tract_number");
            status = obj.getString("status");
            cropZone = obj.getString("crop_zone");
            guid = obj.getString("guid");
        } catch (JSONException e) {
            Log.e("nfs", "Farm(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }
    
    public Farm(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        siteId = c.getInt(c.getColumnIndex("site_id"));
        name = c.getString(c.getColumnIndex("name"));
        contactId = c.getInt(c.getColumnIndex("contact_id"));
        fsaFarmNumber = c.getString(c.getColumnIndex("fsa_farm_number"));
        fsaTractNumber = c.getString(c.getColumnIndex("fsa_tract_number"));
        status = c.getString(c.getColumnIndex("status"));
        cropZone = c.getString(c.getColumnIndex("crop_zone"));
        guid = c.getString(c.getColumnIndex("guid"));
    }

    public static List<Farm> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Farm> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Farm(cursor));
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
        cv.put("contact_id", contactId);
        cv.put("fsa_farm_number", fsaFarmNumber);
        cv.put("fsa_tract_number", fsaTractNumber);
        cv.put("status", status);
        cv.put("crop_zone", cropZone);
        cv.put("guid", guid);
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

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getFsaFarmNumber() {
        return fsaFarmNumber;
    }

    public void setFsaFarmNumber(String fsaFarmNumber) {
        this.fsaFarmNumber = fsaFarmNumber;
    }

    public String getFsaTractNumber() {
        return fsaTractNumber;
    }

    public void setFsaTractNumber(String fsaTractNumber) {
        this.fsaTractNumber = fsaTractNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCropZone() {
        return cropZone;
    }

    public void setCropZone(String cropZone) {
        this.cropZone = cropZone;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "Farm{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", name='" + name + '\'' +
                ", contactId=" + contactId +
                ", fsaFarmNumber='" + fsaFarmNumber + '\'' +
                ", fsaTractNumber='" + fsaTractNumber + '\'' +
                ", status='" + status + '\'' +
                ", cropZone='" + cropZone + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }

    public static Farm[] jsonToArray(JSONArray jsonArray) {
        List<Farm> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Farm(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Farm.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        Farm[] array = new Farm[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private SQLiteDatabase mDb;

        PopulateAsync(SQLiteDatabase db) {
            super();
            Log.d("nfs", "Farm.PopulateAsync()");
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "Farm.PopulateAsync.doInBackground()");

            Farm[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " FARMS");
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            for (Farm item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
            }

            Log.d("nfs", "Farm.PopulateAsync() DONE");

            return null;
        }
    }

}