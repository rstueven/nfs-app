package com.agsimplified.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

public class TableDefs_1 extends TableDefs {
    private static final String[] CLIENT_COLS = {
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

    private static final String[] JOB_PLAN_COLS = {
            "_id INTEGER NOT NULL",
            "client_id INTEGER",
            "description TEXT",
            "status TEXT",
            "job_type TEXT",
            "job_code TEXT",
            "job_status TEXT",
            "notes TEXT",
            "manager_id INTEGER",
            "wizard_complete TEXT",
            "year INTEGER",
            "manager_emailed INTEGER",
            "accounting_manager_id INTEGER",
            "accounting_manager_emailed INTEGER",
            "created_by_id INTEGER",
            "client_job_code INTEGER",
            "PRIMARY KEY (_id)"
    };

    private static final String[] DISTRIBUTION_SALE_COLS = {
            "_id INTEGER NOT NULL",
            "job_plan_id INTEGER",
            "year INTEGER",
            "from_id INTEGER",
            "to_id INTEGER",
            "mileage REAL",
            "amount REAL",
            "notes TEXT",
            "from_type TEXT",
            "from_field_id INTEGER",
            "from_storage_inventory_id INTEGER",
            "to_type TEXT",
            "to_field_id INTEGER",
            "to_storage_inventory_id INTEGER",
            "product_id INTEGER",
            "product_cost REAL",
            "directions TEXT",
            "sale_price REAL",
            "spreading_cost REAL",
            "trucking_cost REAL",
            "loading_cost REAL",
            "previous_crop_id INTEGER",
            "planned_crop_id INTEGER",
            "cropping_rotation TEXT",
            "tillage_practices TEXT",
            "planned_acres REAL",
            "PRIMARY KEY (_id)"
    };

    private static final String[] SITE_COLS = {
            "_id INTEGER NOT NULL",
            "name TEXT",
            "state_id TEXT",
            "address_1 TEXT",
            "address_2 TEXT",
            "city TEXT",
            "state TEXT",
            "zip TEXT",
            "county TEXT",
            "legal_1 TEXT",
            "legal_2 TEXT",
            "legal_sec TEXT",
            "legal_tier TEXT",
            "legal_range TEXT",
            "legal_state TEXT",
            "township TEXT",
            "site_type TEXT",
            "npdex_permit TEXT",
            "status TEXT",
            "nmp_due_date TEXT",
            "date_constructed TEXT",
            "other_id TEXT",
            "weather_station_id INTEGER",
            "guid TEXT",
            "service_level_id INTEGER",
            "license_200a TEXT",
            "PRIMARY KEY (_id)"
    };

    private static final String[] PRODUCT_COLS = {
            "_id INTEGER NOT NULL",
            "name TEXT",
            "product_type TEXT",
            "product_form TEXT",
            "PRIMARY KEY (_id)"
    };

    TableDefs_1() {
        super();
    }

    @Override
    protected void initTableDefs() {
        Log.d("nfs", "TableDefs_1.initTableDefs()");
        createStatements.put(TABLE_CLIENT,
                "CREATE TABLE IF NOT EXISTS "
                        + TABLE_CLIENT + " ("
                        + TextUtils.join(",", CLIENT_COLS)
                        + ")");
        createStatements.put(TABLE_JOB_PLAN,
                "CREATE TABLE IF NOT EXISTS "
                        + TABLE_JOB_PLAN + " ("
                        + TextUtils.join(",", JOB_PLAN_COLS)
                        + ")");
        createStatements.put(TABLE_DISTRIBUTION_SALE,
                "CREATE TABLE IF NOT EXISTS "
                        + TABLE_DISTRIBUTION_SALE + " ("
                        + TextUtils.join(",", DISTRIBUTION_SALE_COLS)
                        + ")");
        createStatements.put(TABLE_SITE,
                "CREATE TABLE IF NOT EXISTS "
                        + TABLE_SITE + " ("
                        + TextUtils.join(",", SITE_COLS)
                        + ")");
        createStatements.put(TABLE_PRODUCT,
                "CREATE TABLE IF NOT EXISTS "
                        + TABLE_PRODUCT + " ("
                        + TextUtils.join(",", PRODUCT_COLS)
                        + ")");
    }

    @Override
    public void loadData(SQLiteDatabase db) {
        Log.d("nfs", "TableDefs_1.loadData()");
        new Client.LoadAsync(db).execute();
    }
}