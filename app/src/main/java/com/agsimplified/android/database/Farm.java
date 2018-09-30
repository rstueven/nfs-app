package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Farm extends AbstractTable {
    public static final String TABLE_NAME = "farms";
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

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        siteId = cursor.getInt(cursor.getColumnIndex("site_id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
        contactId = cursor.getInt(cursor.getColumnIndex("contact_id"));
        fsaFarmNumber = cursor.getString(cursor.getColumnIndex("fsa_farm_number"));
        fsaTractNumber = cursor.getString(cursor.getColumnIndex("fsa_tract_number"));
        status = cursor.getString(cursor.getColumnIndex("status"));
        cropZone = cursor.getString(cursor.getColumnIndex("crop_zone"));
        guid = cursor.getString(cursor.getColumnIndex("guid"));
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
}