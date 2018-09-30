package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;

public class LoadSheet extends AbstractTable {
    public static final String TABLE_NAME = "load_sheets";
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

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("contact_id", contactId);
        cv.put("distribution_sale_id", distributionSaleId);
        cv.put("date", date);
        return cv;
    }

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        contactId = cursor.getInt(cursor.getColumnIndex("contact_id"));
        distributionSaleId = cursor.getInt(cursor.getColumnIndex("distribution_sale_id"));
        date = cursor.getString(cursor.getColumnIndex("date"));
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
}