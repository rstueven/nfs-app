package com.agsimplified.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

public class TableDefs_1 extends TableDefs {
    TableDefs_1() {
        super();
    }

    @Override
    protected void initTableDefs() {
        Log.d("nfs", "TableDefs_1.initTableDefs()");
        createStatements.put(Client.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Client.TABLE_NAME + " ("
                        + TextUtils.join(",", Client.COLUMNS)
                        + ")");
        createStatements.put(JobPlan.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + JobPlan.TABLE_NAME + " ("
                        + TextUtils.join(",", JobPlan.COLUMNS)
                        + ")");
        createStatements.put(DistributionSale.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + DistributionSale.TABLE_NAME + " ("
                        + TextUtils.join(",", DistributionSale.COLUMNS)
                        + ")");
        createStatements.put(Site.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Site.TABLE_NAME + " ("
                        + TextUtils.join(",", Site.COLUMNS)
                        + ")");
        createStatements.put(Product.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Product.TABLE_NAME + " ("
                        + TextUtils.join(",", Product.COLUMNS)
                        + ")");
    }

    @Override
    public void loadData(SQLiteDatabase db) {
        Log.d("nfs", "TableDefs_1.loadData()");
        new Client.LoadAsync(db).execute();
        new JobPlan.LoadAsync(db).execute();
        new DistributionSale.LoadAsync(db).execute();
        new Site.LoadAsync(db).execute();
        new Product.LoadAsync(db).execute();
    }
}