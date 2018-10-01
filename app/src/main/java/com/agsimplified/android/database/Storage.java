package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.agsimplified.android.model.GeoLocatable;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Storage extends AbstractTable implements GeoLocatable {
    public static final String TABLE_NAME = "storages";
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

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        siteId = cursor.getInt(cursor.getColumnIndex("site_id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
        dateConstructed = cursor.getString(cursor.getColumnIndex("date_constructed"));
        description = cursor.getString(cursor.getColumnIndex("description"));
        gps = cursor.getString(cursor.getColumnIndex("gps"));
        status = cursor.getString(cursor.getColumnIndex("status"));
        geoJson = cursor.getString(cursor.getColumnIndex("geo_json"));
        storageTypeId = cursor.getInt(cursor.getColumnIndex("storage_type_id"));
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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFullName() {
        String s = getName();
        Site site = null;
        try {
            site = Site.find(Site.class, siteId);
            if (site != null) {
                s = site.getName() + " : " + s;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e("nfs", "Storage.getFullName(): " + e.getLocalizedMessage());
            s += " (ERROR)";
        }

        return s;
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

    @Override
    public LatLng getLocation() {
        LatLng location = null;

        try {
            JSONObject jsonObject = new JSONObject(getGeoJson());
            JSONObject firstFeature = jsonObject.getJSONArray("features").getJSONObject(0);
            JSONObject geometry = firstFeature.getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates");
            location = new LatLng(coordinates.getDouble(1), coordinates.getDouble(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return location;
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
}