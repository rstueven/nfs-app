package com.agsimplified.android.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public abstract class TableDefs {
    final static HashMap<String, String> createStatements = new HashMap<>();
    private final static HashMap<String, String[]> indexStatements = new HashMap<>();

    interface UpdateListener {
        void onDataUpdated();
    }

    TableDefs() {
        initTableDefs();
    }

    public static TableDefs newInstance(int version) {
        TableDefs tableDefs;

        switch (version) {
            case 1:
                tableDefs = new TableDefs_1();
                break;
            default:
                throw new IllegalArgumentException("Invalid version: " + version);
        }

        return tableDefs;
    }

    protected abstract void initTableDefs();

    protected void upgrade(SQLiteDatabase db) {
        Log.i("nfs", "upgrade(): default implementation");
    }

    public void loadData(SQLiteDatabase db) {
        Log.i("nfs", "loadData(): default implementation");
    }

    protected void upgradeOldTables(SQLiteDatabase db) {
        Log.i("nfs", "upgradeOldTables(): default implementation");
    }

    protected void installNewTables(SQLiteDatabase db) {
        Log.i("nfs", "installNewTables(): default implementation");
    }

    protected final HashMap<String, String> getCreateStatements() {
        return createStatements;
    }

    protected final HashMap<String, String[]> getIndexStatements() {
        return indexStatements;
    }

    protected static String createIndex(String table, String column) {
        return createIndex(table, column, new String[] { column });
    }

    protected static String[] getColumns(SQLiteDatabase db, String tableName) {
        String[] ar = null;

        Cursor c = db.rawQuery("select * from " + tableName + " limit 1", null);

        if (c != null) {
            ar = c.getColumnNames();
            c.close();
        }

        return ar;
    }

    private static String createIndex(String table, String name, String[] columns) {
        return "CREATE INDEX " + table + "_" + name + " ON " + table + " (" + TextUtils.join(", ", columns) + ")";
    }
}