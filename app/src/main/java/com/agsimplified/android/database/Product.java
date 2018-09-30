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

public class Product implements Serializable {
    public Product() {
    }

    public static final String TABLE_NAME = "products";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "name TEXT",
            "product_type TEXT",
            "product_form TEXT",
            "PRIMARY KEY (_id)"
    };

    private int id;
    private String name;
    private String productType;
    private String productForm;

    public Product(JSONObject obj) {
        try {
            id = obj.optInt("id");
            name = obj.getString("name");
            productType = obj.getString("product_type");
            productForm = obj.getString("product_form");

        } catch (JSONException e) {
            Log.e("nfs", "Product(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public Product(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        name = c.getString(c.getColumnIndex("name"));
        productType = c.getString(c.getColumnIndex("product_type"));
        productForm = c.getString(c.getColumnIndex("product_form"));
    }

    public static Product find(int id) {
        Product item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            item = new Product(cursor);
            cursor.close();
        } else {
            Log.w("nfs", "PRODUCT(" + id + ") NOT FOUND");
        }

        return item;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("product_type", productType);
        cv.put("product_form", productForm);
        return cv;
    }

    public static List<Product> all() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name ASC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Product> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Product(cursor));
            }

            cursor.close();
        }

        return list;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productType='" + productType + '\'' +
                ", productForm='" + productForm + '\'' +
                '}';
    }


    public static Product[] jsonToArray(JSONArray jsonArray) {
        List<Product> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Product(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Product.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        Product[] array = new Product[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private DbOpenHelper dbHelper;
        private SQLiteDatabase mDb;

        PopulateAsync(DbOpenHelper dbHelper, SQLiteDatabase db) {
            super();
            Log.d("nfs", "Product.PopulateAsync()");
            this.dbHelper = dbHelper;
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "Product.PopulateAsync.doInBackground()");

            Product[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " PRODUCTS");
            dbHelper.onTableLoadStart(TABLE_NAME, array.length);
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            int n = 0;
            for (Product item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
                dbHelper.onTableLoadProgress(TABLE_NAME, ++n);
            }

            dbHelper.onTableLoadEnd(TABLE_NAME);
            Log.d("nfs", "Product.PopulateAsync() DONE");

            return null;
        }
    }
}