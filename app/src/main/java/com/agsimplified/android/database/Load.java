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

public class Load implements Serializable {
    public Load(){}

    public static String TABLE_NAME = "loads";
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

    public Load(JSONObject obj) {
        try {
            id = obj.optInt("id");
            loadSheetId = obj.optInt("load_sheet_id");
            time = obj.getString("time");
            grossWt = obj.optDouble("gross_wt");
            notes = obj.getString("notes");
            miles = obj.optDouble("miles");
            tareWt = obj.optDouble("tare_wt");
            scaleTicket = obj.getString("scale_ticket");
            fromInventoryId = obj.optInt("from_inventory_id");
            toInventoryId = obj.optInt("to_inventory_id");
            amount = obj.optDouble("amount");
            fromStorageInventoryId = obj.optInt("from_storage_inventory_id");
            truckingCompanyId = obj.optInt("trucking_company_id");
            loadingCompanyId = obj.optInt("loading_company_id");
            loadingOperatorId = obj.optInt("loading_operator_id");
        } catch (JSONException e) {
            Log.e("nfs", "Load(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public Load(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        loadSheetId = c.getInt(c.getColumnIndex("load_sheet_id"));
        time = c.getString(c.getColumnIndex("time"));
        grossWt = c.getDouble(c.getColumnIndex("gross_wt"));
        notes = c.getString(c.getColumnIndex("notes"));
        miles = c.getDouble(c.getColumnIndex("miles"));
        tareWt = c.getDouble(c.getColumnIndex("tare_wt"));
        scaleTicket = c.getString(c.getColumnIndex("scale_ticket"));
        fromInventoryId = c.getInt(c.getColumnIndex("from_inventory_id"));
        toInventoryId = c.getInt(c.getColumnIndex("to_inventory_id"));
        amount = c.getDouble(c.getColumnIndex("amount"));
        fromStorageInventoryId = c.getInt(c.getColumnIndex("from_storage_inventory_id"));
        truckingCompanyId = c.getInt(c.getColumnIndex("trucking_company_id"));
        loadingCompanyId = c.getInt(c.getColumnIndex("loading_company_id"));
        loadingOperatorId = c.getInt(c.getColumnIndex("loading_operator_id"));
    }

    public static Load find(int id) {
        Load item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            item = new Load(cursor);
            cursor.close();
        } else {
            Log.w("nfs", "LOAD(" + id + ") NOT FOUND");
        }

        return item;
    }

    public static List<Load> findByLoadSheetId(int id) {
        List<Load> loads = new ArrayList<>();

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "load_sheet_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                loads.add(new Load(cursor));
            }
            cursor.close();
        } else {
            Log.w("nfs", "LOAD.findByLoadSheetId(" + id + ") NULL CURSOR");
        }

        return loads;
    }

    public static List<Load> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Load> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Load(cursor));
            }

            cursor.close();
        }

        return list;
    }

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

    @Override
    public String toString() {
        return "Load{" +
                "id=" + id +
                ", loadSheetId=" + loadSheetId +
                ", time='" + time + '\'' +
                ", grossWt=" + grossWt +
                ", notes='" + notes + '\'' +
                ", miles=" + miles +
                ", tareWt=" + tareWt +
                ", scaleTicket='" + scaleTicket + '\'' +
                ", fromInventoryId=" + fromInventoryId +
                ", toInventoryId=" + toInventoryId +
                ", amount=" + amount +
                ", fromStorageInventoryId=" + fromStorageInventoryId +
                ", truckingCompanyId=" + truckingCompanyId +
                ", loadingCompanyId=" + loadingCompanyId +
                ", loadingOperatorId=" + loadingOperatorId +
                '}';
    }

    public static Load[] jsonToArray(JSONArray jsonArray) {
        List<Load> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Load(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Load.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        Load[] array = new Load[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private DbOpenHelper dbHelper;
        private SQLiteDatabase mDb;

        PopulateAsync(DbOpenHelper dbHelper, SQLiteDatabase db) {
            super();
            Log.d("nfs", "Load.PopulateAsync()");
            this.dbHelper = dbHelper;
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "Load.PopulateAsync.doInBackground()");

            Load[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " LOADS");
            dbHelper.onTableLoadStart(TABLE_NAME, array.length);
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            int n = 0;
            for (Load item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
                dbHelper.onTableLoadProgress(TABLE_NAME, ++n);
            }

            dbHelper.onTableLoadEnd(TABLE_NAME);
            Log.d("nfs", "Load.PopulateAsync() DONE");

            return null;
        }
    }
}