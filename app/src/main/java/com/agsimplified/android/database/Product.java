package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Product extends AbstractTable {
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

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("product_type", productType);
        cv.put("product_form", productForm);
        return cv;
    }

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
        productType = cursor.getString(cursor.getColumnIndex("product_type"));
        productForm = cursor.getString(cursor.getColumnIndex("product_form"));
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
}