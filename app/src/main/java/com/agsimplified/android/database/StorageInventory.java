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

public class StorageInventory implements Serializable {
    public StorageInventory(){}

    public static String TABLE_NAME = "storage_inventories";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "storage_capacity REAL",
            "current_volume REAL",
            "initial_volume REAL",
            "storageable_id INTEGER",
            "storageable_type TEXT",
            "product_type TEXT",
            "product_form TEXT",
            "initial_product_id INTEGER"
    };
    
    private int id;
    private double storageCapacity;
    private double currentVolume;
    private double initialVolume;
    private int storageableId;
    private String storageableType;
    private String productType;
    private String productForm;
    private int initialProductId;

    public StorageInventory(JSONObject obj) {
        try {
            id = obj.optInt("id");
            storageCapacity = obj.optDouble("storage_capacity");
            currentVolume = obj.optDouble("current_volume");
            initialVolume = obj.optDouble("initial_volume");
            storageableId = obj.optInt("storageable_id");
            storageableType = obj.getString("storageable_type");
            productType = obj.getString("product_type");
            productForm = obj.getString("product_form");
            initialProductId = obj.optInt("initial_product_id");
        } catch (JSONException e) {
            Log.e("nfs", "StorageInventory(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public StorageInventory(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        storageCapacity = c.getDouble(c.getColumnIndex("storage_capacity"));
        currentVolume = c.getDouble(c.getColumnIndex("current_volume"));
        initialVolume = c.getDouble(c.getColumnIndex("initial_volume"));
        storageableId = c.getInt(c.getColumnIndex("storageable_id"));
        storageableType = c.getString(c.getColumnIndex("storageable_type"));
        productType = c.getString(c.getColumnIndex("product_type"));
        productForm = c.getString(c.getColumnIndex("product_form"));
        initialProductId = c.getInt(c.getColumnIndex("initial_product_id"));
    }

    public static StorageInventory find(int id) {
        StorageInventory item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            item = new StorageInventory(cursor);
            cursor.close();
        }

        return item;
    }

    public static List<StorageInventory> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<StorageInventory> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new StorageInventory(cursor));
            }

            cursor.close();
        }

        return list;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("storage_capacity", storageCapacity);
        cv.put("current_volume", currentVolume);
        cv.put("initial_volume", initialVolume);
        cv.put("storageable_id", storageableId);
        cv.put("storageable_type", storageableType);
        cv.put("product_type", productType);
        cv.put("product_form", productForm);
        cv.put("initial_product_id", initialProductId);
        return cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(double storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public double getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(double currentVolume) {
        this.currentVolume = currentVolume;
    }

    public double getInitialVolume() {
        return initialVolume;
    }

    public void setInitialVolume(double initialVolume) {
        this.initialVolume = initialVolume;
    }

    public int getStorageableId() {
        return storageableId;
    }

    public void setStorageableId(int storageableId) {
        this.storageableId = storageableId;
    }

    public String getStorageableType() {
        return storageableType;
    }

    public void setStorageableType(String storageableType) {
        this.storageableType = storageableType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductForm() {
        return productForm;
    }

    public void setProductForm(String productForm) {
        this.productForm = productForm;
    }

    public int getInitialProductId() {
        return initialProductId;
    }

    public void setInitialProductId(int initialProductId) {
        this.initialProductId = initialProductId;
    }

    @Override
    public String toString() {
        return "StorageInventory{" +
                "id=" + id +
                ", storageCapacity=" + storageCapacity +
                ", currentVolume=" + currentVolume +
                ", initialVolume=" + initialVolume +
                ", storageableId=" + storageableId +
                ", storageableType='" + storageableType + '\'' +
                ", productType='" + productType + '\'' +
                ", productForm='" + productForm + '\'' +
                ", initialProductId=" + initialProductId +
                '}';
    }

    public static StorageInventory[] jsonToArray(JSONArray jsonArray) {
        List<StorageInventory> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new StorageInventory(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "StorageInventory.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        StorageInventory[] array = new StorageInventory[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private SQLiteDatabase mDb;

        PopulateAsync(SQLiteDatabase db) {
            super();
            Log.d("nfs", "StorageInventory.PopulateAsync()");
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "StorageInventory.PopulateAsync.doInBackground()");

            StorageInventory[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " STORAGE INVENTORIES");
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            for (StorageInventory item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
            }

            Log.d("nfs", "StorageInventory.PopulateAsync() DONE");

            return null;
        }
    }
}