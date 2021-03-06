package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.agsimplified.android.util.NetworkRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Site implements Serializable {
    public Site() {
    }

    public static String TABLE_NAME = "sites";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "name TEXT",
            "state_id TEXT",
            "address_1 TEXT",
            "address_2 TEXT",
            "city TEXT",
            "state TEXT",
            "zip TEXT",
            "county TEXT",
            "legal_1 TEXT",
            "legal_2 TEXT",
            "legal_sec TEXT",
            "legal_tier TEXT",
            "legal_range TEXT",
            "legal_state TEXT",
            "township TEXT",
            "site_type TEXT",
            "npdes_permit TEXT",
            "status TEXT",
            "nmp_due_date TEXT",
            "date_constructed TEXT",
            "other_id TEXT",
            "weather_station_id INTEGER",
            "guid TEXT",
            "service_level_id INTEGER",
            "license_200a TEXT",
            "PRIMARY KEY (_id)"
    };

    private int id;
    private String name;
    private String stateId;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String county;
    private String legal1;
    private String legal2;
    private String legalSec;
    private String legalTier;
    private String legalRange;
    private String legalState;
    private String township;
    private String siteType;
    private String npdesPermit;
    private String status;
    private String nmpDueDate;
    private String dateConstructed;
    private String otherId;
    private int weatherStationId;
    private String guid;
    private int serviceLevelId;
    private String license200a;

    public Site(JSONObject obj) {
        try {
            id = obj.optInt("id");
            name = obj.getString("name");
            stateId = obj.getString("state_id");
            address1 = obj.getString("address_1");
            address2 = obj.getString("address_2");
            city = obj.getString("city");
            state = obj.getString("state");
            zip = obj.getString("zip");
            county = obj.getString("county");
            legal1 = obj.getString("legal_1");
            legal2 = obj.getString("legal_2");
            legalSec = obj.getString("legal_sec");
            legalTier = obj.getString("legal_tier");
            legalRange = obj.getString("legal_range");
            legalState = obj.getString("legal_state");
            township = obj.getString("township");
            siteType = obj.getString("site_type");
            npdesPermit = obj.getString("npdes_permit");
            status = obj.getString("status");
            nmpDueDate = obj.getString("nmp_due_date");
            dateConstructed = obj.getString("date_constructed");
            otherId = obj.getString("other_id");
            weatherStationId = obj.optInt("weather_station_id");
            guid = obj.getString("guid");
            serviceLevelId = obj.optInt("service_level_id");
            license200a = obj.getString("license_200a");
        } catch (JSONException e) {
            Log.e("nfs", "Site(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public Site(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        name = c.getString(c.getColumnIndex("name"));
        stateId = c.getString(c.getColumnIndex("state_id"));
        address1 = c.getString(c.getColumnIndex("address_1"));
        address2 = c.getString(c.getColumnIndex("address_2"));
        city = c.getString(c.getColumnIndex("city"));
        state = c.getString(c.getColumnIndex("state"));
        zip = c.getString(c.getColumnIndex("zip"));
        county = c.getString(c.getColumnIndex("county"));
        legal1 = c.getString(c.getColumnIndex("legal_1"));
        legal2 = c.getString(c.getColumnIndex("legal_2"));
        legalSec = c.getString(c.getColumnIndex("legal_sec"));
        legalTier = c.getString(c.getColumnIndex("legal_tier"));
        legalRange = c.getString(c.getColumnIndex("legal_range"));
        legalState = c.getString(c.getColumnIndex("legal_state"));
        township = c.getString(c.getColumnIndex("township"));
        siteType = c.getString(c.getColumnIndex("site_type"));
        npdesPermit = c.getString(c.getColumnIndex("npdes_permit"));
        status = c.getString(c.getColumnIndex("status"));
        nmpDueDate = c.getString(c.getColumnIndex("nmp_due_date"));
        dateConstructed = c.getString(c.getColumnIndex("date_constructed"));
        otherId = c.getString(c.getColumnIndex("other_id"));
        weatherStationId = c.getInt(c.getColumnIndex("weather_station_id"));
        guid = c.getString(c.getColumnIndex("guid"));
        serviceLevelId = c.getInt(c.getColumnIndex("service_level_id"));
        license200a = c.getString(c.getColumnIndex("license_200a"));
    }

    public static Site find(int id) {
        Site item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            item = new Site(cursor);
            cursor.close();
        }

        return item;
    }

    public static List<Site> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Site> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Site(cursor));
            }

            cursor.close();
        }

        return list;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("state_id", stateId);
        cv.put("address_1", address1);
        cv.put("address_2", address2);
        cv.put("city", city);
        cv.put("state", state);
        cv.put("zip", zip);
        cv.put("county", county);
        cv.put("legal_1", legal1);
        cv.put("legal_2", legal2);
        cv.put("legal_sec", legalSec);
        cv.put("legal_tier", legalTier);
        cv.put("legal_range", legalRange);
        cv.put("legal_state", legalState);
        cv.put("township", township);
        cv.put("site_type", siteType);
        cv.put("npdes_permit", npdesPermit);
        cv.put("status", status);
        cv.put("nmp_due_date", nmpDueDate);
        cv.put("date_constructed", dateConstructed);
        cv.put("other_id", otherId);
        cv.put("weather_station_id", weatherStationId);
        cv.put("guid", guid);
        cv.put("service_level_id", serviceLevelId);
        cv.put("license_200a", license200a);
        return cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLegal1() {
        return legal1;
    }

    public void setLegal1(String legal1) {
        this.legal1 = legal1;
    }

    public String getLegal2() {
        return legal2;
    }

    public void setLegal2(String legal2) {
        this.legal2 = legal2;
    }

    public String getLegalSec() {
        return legalSec;
    }

    public void setLegalSec(String legalSec) {
        this.legalSec = legalSec;
    }

    public String getLegalTier() {
        return legalTier;
    }

    public void setLegalTier(String legalTier) {
        this.legalTier = legalTier;
    }

    public String getLegalRange() {
        return legalRange;
    }

    public void setLegalRange(String legalRange) {
        this.legalRange = legalRange;
    }

    public String getLegalState() {
        return legalState;
    }

    public void setLegalState(String legalState) {
        this.legalState = legalState;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getNpdesPermit() {
        return npdesPermit;
    }

    public void setNpdesPermit(String npdesPermit) {
        this.npdesPermit = npdesPermit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNmpDueDate() {
        return nmpDueDate;
    }

    public void setNmpDueDate(String nmpDueDate) {
        this.nmpDueDate = nmpDueDate;
    }

    public String getDateConstructed() {
        return dateConstructed;
    }

    public void setDateConstructed(String dateConstructed) {
        this.dateConstructed = dateConstructed;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public int getWeatherStationId() {
        return weatherStationId;
    }

    public void setWeatherStationId(int weatherStationId) {
        this.weatherStationId = weatherStationId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getServiceLevelId() {
        return serviceLevelId;
    }

    public void setServiceLevelId(int serviceLevelId) {
        this.serviceLevelId = serviceLevelId;
    }

    public String getLicense200a() {
        return license200a;
    }

    public void setLicense200a(String license200a) {
        this.license200a = license200a;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stateId='" + stateId + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", county='" + county + '\'' +
                ", legal1='" + legal1 + '\'' +
                ", legal2='" + legal2 + '\'' +
                ", legalSec='" + legalSec + '\'' +
                ", legalTier='" + legalTier + '\'' +
                ", legalRange='" + legalRange + '\'' +
                ", legalState='" + legalState + '\'' +
                ", township='" + township + '\'' +
                ", siteType='" + siteType + '\'' +
                ", npdesPermit='" + npdesPermit + '\'' +
                ", status='" + status + '\'' +
                ", nmpDueDate='" + nmpDueDate + '\'' +
                ", dateConstructed='" + dateConstructed + '\'' +
                ", otherId='" + otherId + '\'' +
                ", weatherStationId=" + weatherStationId +
                ", guid='" + guid + '\'' +
                ", serviceLevelId=" + serviceLevelId +
                ", license200a='" + license200a + '\'' +
                '}';
    }

    public static Site[] jsonToArray(JSONArray jsonArray) {
        List<Site> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Site(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Site.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        Site[] array = new Site[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private SQLiteDatabase mDb;

        PopulateAsync(SQLiteDatabase db) {
            super();
            Log.d("nfs", "Site.PopulateAsync()");
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "Site.PopulateAsync.doInBackground()");

            Site[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " SITES");
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            for (Site item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
            }

            Log.d("nfs", "Site.PopulateAsync() DONE");

            return null;
        }
    }
}