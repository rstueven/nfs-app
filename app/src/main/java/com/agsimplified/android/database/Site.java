package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Site extends AbstractTable {
    public static final String TABLE_NAME = "sites";
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

    @Override
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

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
        stateId = cursor.getString(cursor.getColumnIndex("state_id"));
        address1 = cursor.getString(cursor.getColumnIndex("address_1"));
        address2 = cursor.getString(cursor.getColumnIndex("address_2"));
        city = cursor.getString(cursor.getColumnIndex("city"));
        state = cursor.getString(cursor.getColumnIndex("state"));
        zip = cursor.getString(cursor.getColumnIndex("zip"));
        county = cursor.getString(cursor.getColumnIndex("county"));
        legal1 = cursor.getString(cursor.getColumnIndex("legal_1"));
        legal2 = cursor.getString(cursor.getColumnIndex("legal_2"));
        legalSec = cursor.getString(cursor.getColumnIndex("legal_sec"));
        legalTier = cursor.getString(cursor.getColumnIndex("legal_tier"));
        legalRange = cursor.getString(cursor.getColumnIndex("legal_range"));
        legalState = cursor.getString(cursor.getColumnIndex("legal_state"));
        township = cursor.getString(cursor.getColumnIndex("township"));
        siteType = cursor.getString(cursor.getColumnIndex("site_type"));
        npdesPermit = cursor.getString(cursor.getColumnIndex("npdes_permit"));
        status = cursor.getString(cursor.getColumnIndex("status"));
        nmpDueDate = cursor.getString(cursor.getColumnIndex("nmp_due_date"));
        dateConstructed = cursor.getString(cursor.getColumnIndex("date_constructed"));
        otherId = cursor.getString(cursor.getColumnIndex("other_id"));
        weatherStationId = cursor.getInt(cursor.getColumnIndex("weather_station_id"));
        guid = cursor.getString(cursor.getColumnIndex("guid"));
        serviceLevelId = cursor.getInt(cursor.getColumnIndex("service_level_id"));
        license200a = cursor.getString(cursor.getColumnIndex("license_200a"));
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
}