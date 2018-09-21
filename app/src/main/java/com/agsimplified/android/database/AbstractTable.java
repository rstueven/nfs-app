package com.agsimplified.android.database;

import com.google.gson.Gson;

import java.io.Serializable;

class AbstractTable implements Serializable {
    static Gson gson = new Gson();

    static String getTableName() {
        throw new UnsupportedOperationException("Subclass must override getTableName()");
    }

    @Override
    public String toString() {
        return this.getClass().getName().toUpperCase() + ": " + gson.toJson(this);
    }
}