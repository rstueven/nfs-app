package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Load extends AbstractTable {
    public static final String TABLE_NAME = "loads";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "load_sheet_id INTEGER",
            "time TEXT",
            "gross_wt REAL",
            "notes TEXT",
            "miles REAL",
            "tare_wt REAL",
            "scale_ticket TEXT",
            "from_inventory_id INTEGER",
            "to_inventory_id INTEGER",
            "amount REAL",
            "from_storage_inventory_id INTEGER",
            "trucking_company_id INTEGER",
            "loading_company_id INTEGER",
            "loading_operator_id INTEGER"
    };

    private int id;
    private int loadSheetId;
    private String time;
    private double grossWt;
    private String notes;
    private double miles;
    private double tareWt;
    private String scaleTicket;
    private int fromInventoryId;
    private int toInventoryId;
    private double amount;
    private int fromStorageInventoryId;
    private int truckingCompanyId;
    private int loadingCompanyId;
    private int loadingOperatorId;

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("load_sheet_id", loadSheetId);
        cv.put("time", time);
        cv.put("gross_wt", grossWt);
        cv.put("notes", notes);
        cv.put("miles", miles);
        cv.put("tare_wt", tareWt);
        cv.put("scale_ticket", scaleTicket);
        cv.put("from_inventory_id", fromInventoryId);
        cv.put("to_inventory_id", toInventoryId);
        cv.put("amount", amount);
        cv.put("from_storage_inventory_id", fromStorageInventoryId);
        cv.put("trucking_company_id", truckingCompanyId);
        cv.put("loading_company_id", loadingCompanyId);
        cv.put("loading_operator_id", loadingOperatorId);
        return cv;
    }

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        loadSheetId = cursor.getInt(cursor.getColumnIndex("load_sheet_id"));
        time = cursor.getString(cursor.getColumnIndex("time"));
        grossWt = cursor.getDouble(cursor.getColumnIndex("gross_wt"));
        notes = cursor.getString(cursor.getColumnIndex("notes"));
        miles = cursor.getDouble(cursor.getColumnIndex("miles"));
        tareWt = cursor.getDouble(cursor.getColumnIndex("tare_wt"));
        scaleTicket = cursor.getString(cursor.getColumnIndex("scale_ticket"));
        fromInventoryId = cursor.getInt(cursor.getColumnIndex("from_inventory_id"));
        toInventoryId = cursor.getInt(cursor.getColumnIndex("to_inventory_id"));
        amount = cursor.getDouble(cursor.getColumnIndex("amount"));
        fromStorageInventoryId = cursor.getInt(cursor.getColumnIndex("from_storage_inventory_id"));
        truckingCompanyId = cursor.getInt(cursor.getColumnIndex("trucking_company_id"));
        loadingCompanyId = cursor.getInt(cursor.getColumnIndex("loading_company_id"));
        loadingOperatorId = cursor.getInt(cursor.getColumnIndex("loading_operator_id"));
    }

    public static List<Load> findByLoadSheetId(int id) {
        List<Load> loads = new ArrayList<>();

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "load_sheet_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    loads.add(Load.fromCursor(Load.class, cursor));
                } catch (IllegalAccessException | InstantiationException e) {
                    Log.e("nfs", "Load.findByLoadSheetId(" + id + "): " + e.getLocalizedMessage());
                }
            }
            cursor.close();
        } else {
            Log.w("nfs", "LOAD.findByLoadSheetId(" + id + ") NULL CURSOR");
        }

        return loads;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoadSheetId() {
        return loadSheetId;
    }

    public void setLoadSheetId(int loadSheetId) {
        this.loadSheetId = loadSheetId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(double grossWt) {
        this.grossWt = grossWt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public double getTareWt() {
        return tareWt;
    }

    public void setTareWt(double tareWt) {
        this.tareWt = tareWt;
    }

    public String getScaleTicket() {
        return scaleTicket;
    }

    public void setScaleTicket(String scaleTicket) {
        this.scaleTicket = scaleTicket;
    }

    public int getFromInventoryId() {
        return fromInventoryId;
    }

    public void setFromInventoryId(int fromInventoryId) {
        this.fromInventoryId = fromInventoryId;
    }

    public int getToInventoryId() {
        return toInventoryId;
    }

    public void setToInventoryId(int toInventoryId) {
        this.toInventoryId = toInventoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFromStorageInventoryId() {
        return fromStorageInventoryId;
    }

    public void setFromStorageInventoryId(int fromStorageInventoryId) {
        this.fromStorageInventoryId = fromStorageInventoryId;
    }

    public int getTruckingCompanyId() {
        return truckingCompanyId;
    }

    public void setTruckingCompanyId(int truckingCompanyId) {
        this.truckingCompanyId = truckingCompanyId;
    }

    public int getLoadingCompanyId() {
        return loadingCompanyId;
    }

    public void setLoadingCompanyId(int loadingCompanyId) {
        this.loadingCompanyId = loadingCompanyId;
    }

    public int getLoadingOperatorId() {
        return loadingOperatorId;
    }

    public void setLoadingOperatorId(int loadingOperatorId) {
        this.loadingOperatorId = loadingOperatorId;
    }
}