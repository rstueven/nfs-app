package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Field implements Serializable {
    public Field() {
    }

    public static String TABLE_NAME = "fields";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "farm_id INTEGER",
            "name TEXT NOT NULL",
            "field_clu_number TEXT",
            "soil_type TEXT",
            "fsa_acres REAL",
            "gps_acres REAL",
            "legal_1 TEXT",
            "legal_2 TEXT",
            "legal_sec TEXT",
            "legal_tier TEXT",
            "legal_range TEXT",
            "legal_state TEXT",
            "township TEXT",
            "county TEXT",
            "tenantship TEXT",
            "agreement_terms TEXT",
            "spreadable_acres REAL",
            "hel INTEGER",
            "geo_json TEXT",
            "rusle_2 TEXT",
            "p_index TEXT",
            "guid TEXT",
            "status TEXT"
    };

    private int id;
    private int farmId;
    private String name;
    private String fieldCLUNumber;
    private String soilType;
    private double fsaAcres;
    private double gpsAcres;
    private String legal1;
    private String legal2;
    private String legalSec;
    private String legalTier;
    private String legalRange;
    private String legalState;
    private String township;
    private String county;
    private String tenantship;
    private String agreementTerms;
    private double spreadableAcres;
    private boolean hel;
    private String geoJson;
    private String rusle2;
    private String pIndex;
    private String guid;
    private String status;

    public Field(JSONObject obj) {
        try {
            id = obj.optInt("id");
            farmId = obj.optInt("farm_id");
            name = obj.getString("name");
            fieldCLUNumber = obj.getString("field_clu_number");
            soilType = obj.getString("soil_type");
            fsaAcres = obj.optDouble("fsa_acres");
            gpsAcres = obj.optDouble("gps_acres");
            legal1 = obj.getString("legal_1");
            legal2 = obj.getString("legal_2");
            legalSec = obj.getString("legal_sec");
            legalTier = obj.getString("legal_tier");
            legalRange = obj.getString("legal_range");
            legalState = obj.getString("legal_state");
            township = obj.getString("township");
            county = obj.getString("county");
            tenantship = obj.getString("tenantship");
            agreementTerms = obj.getString("agreement_terms");
            spreadableAcres = obj.optDouble("spreadable_acres");
            hel = obj.optBoolean("hel");
            geoJson = obj.getString("geo_json");
            rusle2 = obj.getString("rusle_2");
            pIndex = obj.getString("p_index");
            guid = obj.getString("guid");
            status = obj.getString("status");
        } catch (JSONException e) {
            Log.e("nfs", "Field(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }
    
    public Field(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        farmId = c.getInt(c.getColumnIndex("farm_id"));
        name = c.getString(c.getColumnIndex("name"));
        fieldCLUNumber = c.getString(c.getColumnIndex("field_clu_number"));
        soilType = c.getString(c.getColumnIndex("soil_type"));
        fsaAcres = c.getDouble(c.getColumnIndex("fsa_acres"));
        gpsAcres = c.getDouble(c.getColumnIndex("gps_acres"));
        legal1 = c.getString(c.getColumnIndex("legal_1"));
        legal2 = c.getString(c.getColumnIndex("legal_2"));
        legalSec = c.getString(c.getColumnIndex("legal_sec"));
        legalRange = c.getString(c.getColumnIndex("legal_range"));
        legalState = c.getString(c.getColumnIndex("legal_state"));
        township = c.getString(c.getColumnIndex("township"));
        county = c.getString(c.getColumnIndex("county"));
        tenantship = c.getString(c.getColumnIndex("tenantship"));
        agreementTerms = c.getString(c.getColumnIndex("agreement_terms"));
        spreadableAcres = c.getDouble(c.getColumnIndex("spreadable_acres"));
        hel = c.getInt(c.getColumnIndex("hel")) == 1;
        geoJson = c.getString(c.getColumnIndex("geo_json"));
        rusle2 = c.getString(c.getColumnIndex("rusle_2"));
        pIndex = c.getString(c.getColumnIndex("p_index"));
        guid = c.getString(c.getColumnIndex("guid"));
        status = c.getString(c.getColumnIndex("status"));
    }

    public static Field find(int id) {
        Field item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            item = new Field(cursor);
            cursor.close();
        }

        return item;
    }

    public static List<Field> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Field> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Field(cursor));
            }

            cursor.close();
        }

        return list;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("farm_id", farmId);
        cv.put("name", name);
        cv.put("field_clu_number", fieldCLUNumber);
        cv.put("soil_type", soilType);
        cv.put("fsa_acres", fsaAcres);
        cv.put("gps_acres", gpsAcres);
        cv.put("legal_1", legal1);
        cv.put("legal_2", legal2);
        cv.put("legal_sec", legalSec);
        cv.put("legal_range", legalRange);
        cv.put("legal_state", legalState);
        cv.put("township", township);
        cv.put("county", county);
        cv.put("tenantship", tenantship);
        cv.put("agreement_terms", agreementTerms);
        cv.put("spreadable_acres", spreadableAcres);
        cv.put("hel", hel);
        cv.put("geo_json", geoJson);
        cv.put("rusle_2", rusle2);
        cv.put("p_index", pIndex);
        cv.put("guid", guid);
        cv.put("status", status);
        return cv;
    }

    public String siteFarmField() {
        String s = getName();

        Farm farm = Farm.find(getFarmId());
        if (farm != null) {
            s = farm.getName() + ":" + s;
            Site site = Site.find(farm.getSiteId());
            if (site != null) {
                s = site.getName() + ":" + s;
            }
        }

        return s;
    }

    public LatLng getLatLng() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldCLUNumber() {
        return fieldCLUNumber;
    }

    public void setFieldCLUNumber(String fieldCLUNumber) {
        this.fieldCLUNumber = fieldCLUNumber;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public double getFsaAcres() {
        return fsaAcres;
    }

    public void setFsaAcres(double fsaAcres) {
        this.fsaAcres = fsaAcres;
    }

    public double getGpsAcres() {
        return gpsAcres;
    }

    public void setGpsAcres(double gpsAcres) {
        this.gpsAcres = gpsAcres;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTenantship() {
        return tenantship;
    }

    public void setTenantship(String tenantship) {
        this.tenantship = tenantship;
    }

    public String getAgreementTerms() {
        return agreementTerms;
    }

    public void setAgreementTerms(String agreementTerms) {
        this.agreementTerms = agreementTerms;
    }

    public double getSpreadableAcres() {
        return spreadableAcres;
    }

    public void setSpreadableAcres(double spreadableAcres) {
        this.spreadableAcres = spreadableAcres;
    }

    public boolean isHel() {
        return hel;
    }

    public void setHel(boolean hel) {
        this.hel = hel;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }

    public String getRusle2() {
        return rusle2;
    }

    public void setRusle2(String rusle2) {
        this.rusle2 = rusle2;
    }

    public String getpIndex() {
        return pIndex;
    }

    public void setpIndex(String pIndex) {
        this.pIndex = pIndex;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Farm getFarm() {
        return Farm.find(farmId);
    }

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", farmId=" + farmId +
                ", name='" + name + '\'' +
                ", fieldCLUNumber='" + fieldCLUNumber + '\'' +
                ", soilType='" + soilType + '\'' +
                ", fsaAcres=" + fsaAcres +
                ", gpsAcres=" + gpsAcres +
                ", legal1='" + legal1 + '\'' +
                ", legal2='" + legal2 + '\'' +
                ", legalSec='" + legalSec + '\'' +
                ", legalTier='" + legalTier + '\'' +
                ", legalRange='" + legalRange + '\'' +
                ", legalState='" + legalState + '\'' +
                ", township='" + township + '\'' +
                ", county='" + county + '\'' +
                ", tenantship='" + tenantship + '\'' +
                ", agreementTerms='" + agreementTerms + '\'' +
                ", spreadableAcres=" + spreadableAcres +
                ", hel=" + hel +
                ", geoJson='" + geoJson + '\'' +
                ", rusle2='" + rusle2 + '\'' +
                ", pIndex='" + pIndex + '\'' +
                ", guid='" + guid + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static Field[] jsonToArray(JSONArray jsonArray) {
        List<Field> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Field(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Field.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        Field[] array = new Field[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private SQLiteDatabase mDb;

        PopulateAsync(SQLiteDatabase db) {
            super();
            Log.d("nfs", "Field.PopulateAsync()");
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "Field.PopulateAsync.doInBackground()");

            Field[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " FIELDS");
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            for (Field item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
            }

            Log.d("nfs", "Field.PopulateAsync() DONE");

            return null;
        }
    }
}