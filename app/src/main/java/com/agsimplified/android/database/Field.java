package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.agsimplified.android.model.GeoLocatable;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Field extends AbstractTable implements GeoLocatable {
    public static final String TABLE_NAME = "fields";
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

    @Override
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

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        farmId = cursor.getInt(cursor.getColumnIndex("farm_id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
        fieldCLUNumber = cursor.getString(cursor.getColumnIndex("field_clu_number"));
        soilType = cursor.getString(cursor.getColumnIndex("soil_type"));
        fsaAcres = cursor.getDouble(cursor.getColumnIndex("fsa_acres"));
        gpsAcres = cursor.getDouble(cursor.getColumnIndex("gps_acres"));
        legal1 = cursor.getString(cursor.getColumnIndex("legal_1"));
        legal2 = cursor.getString(cursor.getColumnIndex("legal_2"));
        legalSec = cursor.getString(cursor.getColumnIndex("legal_sec"));
        legalRange = cursor.getString(cursor.getColumnIndex("legal_range"));
        legalState = cursor.getString(cursor.getColumnIndex("legal_state"));
        township = cursor.getString(cursor.getColumnIndex("township"));
        county = cursor.getString(cursor.getColumnIndex("county"));
        tenantship = cursor.getString(cursor.getColumnIndex("tenantship"));
        agreementTerms = cursor.getString(cursor.getColumnIndex("agreement_terms"));
        spreadableAcres = cursor.getDouble(cursor.getColumnIndex("spreadable_acres"));
        hel = cursor.getInt(cursor.getColumnIndex("hel")) == 1;
        geoJson = cursor.getString(cursor.getColumnIndex("geo_json"));
        rusle2 = cursor.getString(cursor.getColumnIndex("rusle_2"));
        pIndex = cursor.getString(cursor.getColumnIndex("p_index"));
        guid = cursor.getString(cursor.getColumnIndex("guid"));
        status = cursor.getString(cursor.getColumnIndex("status"));
    }

    public String siteFarmField() {
        String s = getName();

        Farm farm = null;
        try {
            farm = Farm.find(Farm.class, getFarmId());
            if (farm != null) {
                s = farm.getName() + " : " + s;
                Site site = Site.find(Site.class, farm.getSiteId());
                if (site != null) {
                    s = site.getName() + " : " + s;
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e("nfs", "Field.siteFarmField(" + s +"): " + e.getLocalizedMessage());
            s += " (ERROR)";
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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFullName() {
        return siteFarmField();
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

    @Override
    public String getGeoJson() {
        return geoJson;
    }

    @Override
    public LatLng getLocation() {
        // TODO: Calculate this when you create the object.
        LatLng location = null;

        try {
            JSONObject jsonObject = new JSONObject(getGeoJson());
            JSONObject firstFeature = null;
            firstFeature = jsonObject.getJSONArray("features").getJSONObject(0);
            JSONObject geometry = firstFeature.getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates").getJSONArray(0);

            int n = coordinates.length();
            if (n > 0) {
                JSONArray coord;
                LatLng point;
                double x = 0.0;
                double y = 0.0;
                for (int i = 0; i < n; i++) {
                    coord = coordinates.getJSONArray(i);
                    x += coord.getDouble(1);
                    y += coord.getDouble(0);
                }

                x /= n;
                y /= n;

                location = new LatLng(x, y);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return location;
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
        try {
            return Farm.find(Farm.class, farmId);
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e("nfs", "Field.getFarm(" + farmId +"): " + e.getLocalizedMessage());
            return null;
        }
    }
}