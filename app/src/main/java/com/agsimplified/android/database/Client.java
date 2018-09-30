package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Client extends AbstractTable {
    public static final String TABLE_NAME = "clients";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "name TEXT NOT NULL",
            "address1 TEXT",
            "address2 TEXT",
            "city TEXT",
            "state TEXT",
            "zip TEXT",
            "client_status TEXT",
            "office_phone TEXT",
            "office_fax TEXT",
            "mobile_phone TEXT",
            "email TEXT",
            "website TEXT",
            "farm INTEGER",
            "feedlot INTEGER",
            "service_provider INTEGER",
            "contact_id INTEGER",
            "company_id INTEGER",
            "notes TEXT",
            "service_level_id INTEGER",
            "PRIMARY KEY (_id)"
    };

    private int id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String clientStatus;
    private String officePhone;
    private String officeFax;
    private String mobilePhone;
    private String email;
    private String website;
    private boolean farm;
    private boolean feedlot;
    // private Date createdAt;
    // private Date updatedAt;
    private boolean serviceProvider;
    private int contactId;
    private int companyId;
    private String notes;
    private int serviceLevelId;

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("address1", address1);
        cv.put("address2", address2);
        cv.put("city", city);
        cv.put("state", state);
        cv.put("zip", zip);
        cv.put("client_status", clientStatus);
        cv.put("office_phone", officePhone);
        cv.put("office_fax", officeFax);
        cv.put("mobile_phone", mobilePhone);
        cv.put("email", email);
        cv.put("website", website);
        cv.put("farm", farm);
        cv.put("feedlot", feedlot);
        cv.put("service_provider", serviceProvider);
        cv.put("contact_id", contactId);
        cv.put("company_id", companyId);
        cv.put("notes", notes);
        return cv;
    }

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
        address1 = cursor.getString(cursor.getColumnIndex("address1"));
        address2 = cursor.getString(cursor.getColumnIndex("address2"));
        city = cursor.getString(cursor.getColumnIndex("city"));
        state = cursor.getString(cursor.getColumnIndex("state"));
        zip = cursor.getString(cursor.getColumnIndex("zip"));
        clientStatus = cursor.getString(cursor.getColumnIndex("client_status"));
        officePhone = cursor.getString(cursor.getColumnIndex("office_phone"));
        officeFax = cursor.getString(cursor.getColumnIndex("office_fax"));
        mobilePhone = cursor.getString(cursor.getColumnIndex("mobile_phone"));
        email = cursor.getString(cursor.getColumnIndex("email"));
        website = cursor.getString(cursor.getColumnIndex("website"));
        farm = cursor.getInt(cursor.getColumnIndex("farm")) == 1;
        feedlot = cursor.getInt(cursor.getColumnIndex("feedlot")) == 1;
        serviceProvider = cursor.getInt(cursor.getColumnIndex("service_provider")) == 1;
        contactId = cursor.getInt(cursor.getColumnIndex("contact_id"));
        companyId = cursor.getInt(cursor.getColumnIndex("company_id"));
        notes = cursor.getString(cursor.getColumnIndex("notes"));
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

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficeFax() {
        return officeFax;
    }

    public void setOfficeFax(String officeFax) {
        this.officeFax = officeFax;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isFarm() {
        return farm;
    }

    public void setFarm(boolean farm) {
        this.farm = farm;
    }

    public boolean isFeedlot() {
        return feedlot;
    }

    public void setFeedlot(boolean feedlot) {
        this.feedlot = feedlot;
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    public boolean isServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(boolean serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getServiceLevelId() {
        return serviceLevelId;
    }

    public void setServiceLevelId(int serviceLevelId) {
        this.serviceLevelId = serviceLevelId;
    }
}