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

    public static String TABLE_NAME = "products";
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

    public static Product[] createProducts(JSONArray products) {
        List<Product> productList = new ArrayList<>();

        try {
            for (int i = 0; i < products.length(); i++) {
                productList.add(new Product(products.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Product.createProducts(): " + e.getLocalizedMessage());
            Log.e("nfs", products.toString());
        }

        Product[] productArray = new Product[productList.size()];
        return productList.toArray(productArray);
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

    protected static class PopulateAsync extends AsyncTask<Product, Void, Void> {
        private SQLiteDatabase mDb;

        PopulateAsync(SQLiteDatabase db) {
            super();
            Log.d("nfs", "Product.PopulateAsync()");
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(Product... products) {
            Log.d("nfs", "Product.PopulateAsync.doInBackground()");
            Log.d("nfs", "LOADING " + products.length + " PRODUCTS");
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            for (Product product : products) {
//                    Log.d("nfs", product.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, product.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + product.name + ">");
                }
            }

            Log.d("nfs", "Product.PopulateAsync() DONE");

            return null;
        }
    }
}